package it.polimi.ingsw.network.server;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Server implements Runnable{
    private final Object clientsLock = new Object();
    private int rmiPort = 51633;
    private int socketPort = 51634;

    private static Server instance = null;
    private RMIServer rmiServer;
    private SocketServer socketServer;

    private ExecutorService executor;

    private ConcurrentHashMap<String, Connection> clientsConnected; //mappa di tutti i client con una connessione attiva, per quelli disconnessi c'è la lista dei disconnessi nelle gameLobby
    private GlobalLobby globalLobby;

    private final static int MAX_PLAYERS = 4;
    private final static int MIN_PLAYERS = 2;

    private Server (){
        synchronized (clientsLock) {
            this.clientsConnected = new ConcurrentHashMap<>();
        }
        // Verifica se il server è già stato avviato
        if (instance != null) {
            return;
        }
        startServers();
        executor = new ThreadPoolExecutor(4, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        globalLobby = new GlobalLobby();
    }

    public static void main(String[] args){
        Server server = new Server();
        server.run();
    }

    private void startServers(){
        if (instance != null) {
            System.out.println("Servers already started");
            return;
        }

        instance = this;
        instance.rmiServer = new RMIServer(this, rmiPort);
        instance.rmiServer.startServer();
        System.out.println("RMI Server started on port: " + rmiPort );

        instance.socketServer = new SocketServer(this, socketPort);
        instance.socketServer.startServer();
        System.out.println("Socket Server started on port: " + socketPort + "\n");
    }

    /**
     * Process that pings all the clients to check if they are still connected
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (clientsLock) {
                for (Connection connection : clientsConnected.values()) {
                    if (connection != null && connection.isConnected() && connection.getClientPinger() == null) {
                        ClientPinger pinger = new ClientPinger(getUsernameByConnection(connection), connection, clientsLock);
                        connection.setClientPinger(pinger);
                        executor.execute(pinger);
                        System.out.println("Sono il server... ho creato un thread clientPinger per il client " + getUsernameByConnection(connection));
                    }
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("Server ping interrupted");
                Thread.currentThread().interrupt();
            }
        }
    }

    public static synchronized Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public synchronized void loginToServer(String nickname, Connection connection) throws Exception {

        //lavora sulla mappa di clientsConnected poi lo farà entrare nella globalLobby e poi nella gameLobby
        try {
            synchronized (clientsLock) {
                System.out.println("\nSono il server... ho ricevuto la richiesta di login da parte di " + nickname);

                if (globalLobby.isPlayerDisconnectedInAnyGameLobby(nickname) || clientsConnected.containsKey(nickname)) {
                    knownPlayerLogin(nickname, connection);
                } else {
                    newPlayerLogin(nickname, connection);
                }
            }
        } catch (IOException e) {
            System.out.println("Something went wrong during login. Connection refused!");

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.DISCONNECTION_FORCED);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
        }
    }

    private synchronized void newPlayerLogin(String nickname, Connection connection) throws Exception {

        if (checkNickname(nickname)) { // nickname legit
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            MessageHeader header = new MessageHeader(MessageType.LOBBY, nickname);
            MessagePayload payload = new MessagePayload(KeyLobbyPayload.GLOBAL_LOBBY_DECISION);
            String content = "Login effettuato con successo!";
            payload.put(Data.CONTENT,content);
            connection.sendMessageToClient(new Message(header,payload));

            System.out.println(nickname + " connected to server!");
            System.out.println("Sono il server.. ora passo alla fase nella globalLobby...");

            this.globalLobby.addPlayerToWaiting(nickname, connection);

        } else { // nickname not legit

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_LENGTH);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with invalid name length! Connection refused!");
        }

    }

    private synchronized void knownPlayerLogin(String nickname, Connection connection) throws Exception {
        //o si era disconnesso o nickname già in uso

        if (globalLobby.isPlayerDisconnectedInAnyGameLobby(nickname)) { // player was disconnected
            System.out.println("Sono il server... " + nickname + " era disconnesso in una gameLobby. Ora lo riconnetto...");

            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.CONNECTION_CREATION);
            String content = "Login effettuato con successo sul server! Ora verrai riconnesso alla tua partita...";
            payload.put(Data.CONTENT, content);
            connection.sendMessageToClient(new Message(header, payload));

            System.out.println(nickname + " connected to server!");
            System.out.println("Sono il server.. ora passo alla fase di riconnessione direttamente nella gameLobby...(skip globalLobby)");

            globalLobby.reconnectPlayerToGameLobby(nickname, connection);

        } else if (clientsConnected.containsKey(nickname)) { // nickname already in use

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_TAKEN);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with a nickname already taken!");

        }

    }

    private boolean checkNickname(String nickname) {
        final int MAX_LENGTH_NICKNAME = 20;
        final int MIN_LENGTH_NICKNAME = 2;
        return nickname.length() <= MAX_LENGTH_NICKNAME && nickname.length() >= MIN_LENGTH_NICKNAME;
    }

    public synchronized void onDisconnect(Connection playerConnection) throws RemoteException {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            synchronized (clientsLock) {
                System.out.println(username + " disconnected from server!");
                this.globalLobby.disconnectPlayerFromGlobalLobby(username);
                System.out.println("Sono il server... ho disconnesso " + username + " dalla globalLobby");
                clientsConnected.remove(username);
                System.out.println("Sono il server... ho disconnesso " + username + " dalla lista di clientsConnected del server");
            }
        }
    }

    private String getUsernameByConnection(Connection connection) {
        Set<String> usernameList;
        synchronized (clientsLock) {
            usernameList = clientsConnected.entrySet()
                    .stream()
                    .filter(entry -> connection.equals(entry.getValue()))
                    .map(HashMap.Entry::getKey)
                    .collect(Collectors.toSet());
        }
        if (usernameList.isEmpty()) {
            return null;
        } else {
            return usernameList.iterator().next();
        }
    }

    public synchronized void receiveMessageFromClient(Message message) throws IOException {
        System.out.println("\nSono il server... ho ricevuto il messaggio: "+ message.toString() + "da un client\n");
        MessageType messageType = message.getHeader().getMessageType();

        switch (messageType) {
            case LOBBY -> handleGlobalLobbyPhase(message);

            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    private synchronized void handleGlobalLobbyPhase(Message message) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di join global lobby da parte di " + message.getHeader().getNickname());

        KeyLobbyPayload keyLobbyPayload = (KeyLobbyPayload) message.getPayload().getKey();
        switch (keyLobbyPayload) {
            case CREATE_GAME_LOBBY -> handleCreateGameLobby(message);
            case JOIN_SPECIFIC_GAME_LOBBY -> handleJoinSpecificGameLobby(message);
            case JOIN_RANDOM_GAME_LOBBY -> handleJoinRandomGameLobby(message);
            default -> throw new IllegalStateException("Unexpected value: " + keyLobbyPayload);
        }
    }

    private synchronized void handleCreateGameLobby(Message message) throws IOException {
        int wantedPlayers = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        System.out.println("Sono il server... ho ricevuto la richiesta di creare una nuova game lobby da parte di " + message.getHeader().getNickname() + " con " + wantedPlayers + " giocatori");

        if(wantedPlayers < MIN_PLAYERS || wantedPlayers > MAX_PLAYERS){
            MessageHeader header = new MessageHeader(MessageType.ERROR, message.getHeader().getNickname());
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NUM_PLAYER_WANTED);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(message.getHeader().getNickname()).sendMessageToClient(messageToClient);

            System.out.println("Attention! " + message.getHeader().getNickname() + " tried to create a game lobby with invalid number of players!");
            return;
        }

        System.out.println("il numero di giocatori è valido! ora devo creare la game lobby...");
        this.globalLobby.playerCreatesGameLobby(wantedPlayers, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
        System.out.println("game lobby creata con successo!");
    }

    private synchronized void handleJoinSpecificGameLobby(Message message) throws IOException {
        int gameId = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        System.out.println("Sono il server... ho ricevuto la richiesta di join specific game lobby da parte di " + message.getHeader().getNickname() + " con id " + gameId);

        this.globalLobby.playerJoinsGameLobbyId(gameId, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    private synchronized void handleJoinRandomGameLobby(Message message) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di join random game lobby da parte di " + message.getHeader().getNickname());
        System.out.println("ora devo aggiungere il player alla game lobby random con un posto libero...");

        this.globalLobby.playerJoinsFirstFreeSpotInRandomGame(message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

}
