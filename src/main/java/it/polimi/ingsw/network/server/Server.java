package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.rmi.RemoteException;
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

    private ConcurrentHashMap<String, Connection> clientsConnected;
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
                if (clientsConnected.containsKey(nickname)) {
                    knownPlayerLogin(nickname, connection);
                } else {
                    newPlayerLogin(nickname, connection);
                }
                System.out.println("Sono il server.. ora passo alla fase nella globalLobby...");
            }
        } catch (IOException e) {
            connection.disconnect();
        }
    }

    private synchronized void newPlayerLogin(String nickname, Connection connection) throws Exception {

        if (checkNickname(nickname)) { // nickname legit
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.CONNECTION_CREATION);
            String content = "Login effettuato con successo!";
            payload.put(Data.CONTENT,content);
            connection.sendMessageToClient(new Message(header,payload));

            System.out.println(nickname + " connected to server!");

            this.globalLobby.addPlayerToWaiting(nickname, connection);

            //TODO: poi da togliere
            int wantedPlayers = 3;
            this.globalLobby.playerCreatesGameLobby(wantedPlayers, nickname, connection);

            System.out.println("game lobby creata con successo!");


        } else { // nickname not legit

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_LENGTH);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with invalid name length!");
        }

    }

    private synchronized void knownPlayerLogin(String nickname, Connection connection) throws Exception {
        //o nickname già in uso o si era disconnesso

        if (clientsConnected.containsKey(nickname)) { // nickname already in use

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_TAKEN);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with already used name!");

        } else { // nickname already used but disconnected
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.CONNECTION_CREATION);
            String content = "Login effettuato con successo!";
            payload.put(Data.CONTENT,content);
            connection.sendMessageToClient(new Message(header,payload));

            System.out.println(nickname + " connected to server!");

            this.globalLobby.reconnectPlayerToGameLobby(nickname, connection);
        }
    }

    private boolean checkNickname(String nickname) {
        int MAX_LENGTH_NICKNAME = 20;
        int MIN_LENGTH_NICKNAME = 2;
        return nickname.length() <= MAX_LENGTH_NICKNAME && nickname.length() >= MIN_LENGTH_NICKNAME;
    }

    public void onDisconnect(Connection playerConnection) throws RemoteException {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            synchronized (clientsLock) {
                System.out.println(username + " disconnected from server!");
                this.globalLobby.disconnectPlayerFromGlobalLobby(username);
                clientsConnected.remove(username);
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

    public void receiveMessageFromClient(Message message) throws IOException {
        System.out.println("\nSono il server... ho ricevuto il messaggio: "+ message.toString() + "da un client\n");
        MessageType messageType = message.getHeader().getMessageType();

        switch (messageType) {
            case LOBBY -> handleGlobalLobbyPhase(message);

            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

        private void handleGlobalLobbyPhase(Message message) throws IOException {
        KeyLobbyPayload keyLobbyPayload = (KeyLobbyPayload) message.getPayload().getKey();
        switch (keyLobbyPayload) {
            case JOIN_GLOBAL_LOBBY -> handleJoinGlobalLobby(message);
            case CREATE_GAME_LOBBY -> handleCreateGameLobby(message);
            case JOIN_SPECIFIC_GAME_LOBBY -> handleJoinSpecificGameLobby(message);
            case JOIN_RANDOM_GAME_LOBBY -> handleJoinRandomGameLobby(message);
            default -> throw new IllegalStateException("Unexpected value: " + keyLobbyPayload);
        }
    }

    private void handleJoinGlobalLobby(Message message) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di join global lobby da parte di " + message.getHeader().getNickname());
        this.globalLobby.addPlayerToWaiting(message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));

        MessageHeader header = new MessageHeader(MessageType.LOBBY, message.getHeader().getNickname());
        MessagePayload payload = new MessagePayload(KeyLobbyPayload.GLOBAL_LOBBY_DECISION);
        Message messageToClient = new Message(header, payload);

        clientsConnected.get(message.getHeader().getNickname()).sendMessageToClient(messageToClient);
    }

    private void handleCreateGameLobby(Message message) throws IOException {
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

    private void handleJoinSpecificGameLobby(Message message) throws IOException {
        int gameId = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        System.out.println("Sono il server... ho ricevuto la richiesta di join specific game lobby da parte di " + message.getHeader().getNickname() + " con id " + gameId);

        this.globalLobby.playerJoinsGameLobbyId(gameId, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    private void handleJoinRandomGameLobby(Message message) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di join random game lobby da parte di " + message.getHeader().getNickname());
        System.out.println("ora devo aggiungere il player alla game lobby random con un posto libero...");

        this.globalLobby.playerJoinsFirstFreeSpotInRandomGame(message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

}
