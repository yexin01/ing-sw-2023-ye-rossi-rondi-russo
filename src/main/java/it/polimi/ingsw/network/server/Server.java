package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.*;

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
                System.out.println("Sono il server... ho ricevuto la richiesta di login da parte di " + nickname);
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

    //TODO: da aggiungere che tipo ti risposta se è tutto ok o se è errore
    private synchronized void newPlayerLogin(String nickname, Connection connection) throws Exception {

        if (checkNickname(nickname)) { // nickname legit
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            ServerMessageHeader header = new ServerMessageHeader(EventType.CONNECTION_RESPONSE, getUsernameByConnection(connection));
            MessagePayload payload = new MessagePayload("Login effettuato con successo!");
            MessageFromServer message = new MessageFromServer(header, payload);
            connection.sendMessageToClient(message);

            System.out.println(nickname + " connected to server!");

            this.globalLobby.addPlayerToWaiting(nickname, connection);

        } else { // nickname not legit

            ServerMessageHeader header = new ServerMessageHeader(EventType.ERR_NICKNAME_LENGTH, getUsernameByConnection(connection));
            MessagePayload payload = new MessagePayload("ERROR: Invalid Username");
            MessageFromServer message = new MessageFromServer(header, payload);
            connection.sendMessageToClient(message);

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with invalid name length!");
        }

    }

    private synchronized void knownPlayerLogin(String nickname, Connection connection) throws Exception {
        //o nickname già in uso o si era disconnesso

        if (clientsConnected.containsKey(nickname)) { // nickname already in use
            ServerMessageHeader header = new ServerMessageHeader(EventType.ERR_NICKNAME_TAKEN, getUsernameByConnection(connection));
            MessagePayload payload = new MessagePayload("ERROR: Username already in use");
            MessageFromServer message = new MessageFromServer(header, payload);
            connection.sendMessageToClient(message);

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with already used name!");

        } else { // nickname already used but disconnected
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            ServerMessageHeader header = new ServerMessageHeader(EventType.CONNECTION_RESPONSE, getUsernameByConnection(connection));
            MessagePayload payload = new MessagePayload("Login effettuato con successo!");
            MessageFromServer message = new MessageFromServer(header, payload);
            connection.sendMessageToClient(message);

            System.out.println(nickname + " connected to server!");

            this.globalLobby.reconnectPlayerToGameLobby(nickname, connection);
        }
    }

    private boolean checkNickname(String nickname) {
        int MAX_LENGTH_NICKNAME = 20;
        if (nickname.length() > MAX_LENGTH_NICKNAME) {
            return false;
        }
        if (clientsConnected.containsKey(nickname)){
            return false;
        }
        return true;
    }

    void onDisconnect(Connection playerConnection) throws RemoteException {
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

    public void receiveMessageFromClient(MessageFromClient message) throws IOException {
        System.out.println("\nSono il server... ho ricevuto il messaggio: "+ message.toString() + "da un client\n");
        EventType messageType = message.getHeader().getMessageType();

        switch (messageType) {
            //TODO: sarà poi aggiunta tipo un MessageType es. GLOBAL_LOBBY_PHASE oppure ACTION
            case JOIN_GLOBAL_LOBBY, CREATE_GAME_LOBBY, NUM_PLAYER_WANTED, JOIN_SPECIFIC_GAME_LOBBY, GAME_LOBBY_ID, JOIN_RANDOM_GAME_LOBBY -> handleGlobalLobbyPhase(message);
        }
    }

    private void handleGlobalLobbyPhase(MessageFromClient message) throws IOException {
        EventType messageType = message.getHeader().getMessageType();
        switch (messageType) {
            case JOIN_GLOBAL_LOBBY -> handleJoinGlobalLobby(message);
            case CREATE_GAME_LOBBY, NUM_PLAYER_WANTED -> handleCreateGameLobby(message);
            case JOIN_SPECIFIC_GAME_LOBBY, GAME_LOBBY_ID -> handleJoinSpecificGameLobby(message);
            case JOIN_RANDOM_GAME_LOBBY -> handleJoinRandomGameLobby(message);
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    private void handleJoinGlobalLobby(MessageFromClient message) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di join global lobby da parte di " + message.getHeader().getNicknameSender());
        this.globalLobby.addPlayerToWaiting(message.getHeader().getNicknameSender(), clientsConnected.get(message.getHeader().getNicknameSender()));

        ServerMessageHeader header = new ServerMessageHeader(EventType.ASK_GLOBAL_LOBBY_DECISION, message.getHeader().getNicknameSender());
        MessagePayload payload = new MessagePayload("Server wants to know what you want to do");
        MessageFromServer messageToClient = new MessageFromServer(header, payload);
        clientsConnected.get(message.getHeader().getNicknameSender()).sendMessageToClient(messageToClient);
    }

    private void handleCreateGameLobby(MessageFromClient message) throws IOException {
        EventType messageType = message.getHeader().getMessageType();
        switch (messageType) {
            case CREATE_GAME_LOBBY -> {
                System.out.println("Sono il server... ho ricevuto la richiesta di creare una nuova game lobby da parte di " + message.getHeader().getNicknameSender());
                System.out.println("devo prima sapere il num di player wanted...");
                ServerMessageHeader header = new ServerMessageHeader(EventType.ASK_NUM_PLAYER_WANTED, message.getHeader().getNicknameSender());
                MessagePayload payload = new MessagePayload("Server wants to know how many players you want to play with");
                MessageFromServer messageToClient = new MessageFromServer(header, payload);
                clientsConnected.get(message.getHeader().getNicknameSender()).sendMessageToClient(messageToClient);
            }
            case NUM_PLAYER_WANTED -> {
                System.out.println("Sono il server... ho ricevuto il num di player wanted da parte di " + message.getHeader().getNicknameSender());
                System.out.println("ora devo creare la game lobby...");
                int wantedPlayers = Integer.parseInt(message.getPayload().getContent());
                this.globalLobby.playerCreatesGameLobby(wantedPlayers, message.getHeader().getNicknameSender(), clientsConnected.get(message.getHeader().getNicknameSender()));
                System.out.println("game lobby creata con successo!");
            }
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    private void handleJoinSpecificGameLobby(MessageFromClient message) throws IOException {
        EventType messageType = message.getHeader().getMessageType();
        switch (messageType) {
            case JOIN_SPECIFIC_GAME_LOBBY -> {
                System.out.println("Sono il server... ho ricevuto la richiesta di join specific game lobby da parte di " + message.getHeader().getNicknameSender());
                System.out.println("devo prima sapere l'id della game lobby...");
                ServerMessageHeader header = new ServerMessageHeader(EventType.ASK_GAME_LOBBY_ID, message.getHeader().getNicknameSender());
                MessagePayload payload = new MessagePayload("Server wants to know the name of the game lobby id you want to join");
                MessageFromServer messageToClient = new MessageFromServer(header, payload);
                clientsConnected.get(message.getHeader().getNicknameSender()).sendMessageToClient(messageToClient);
            }
            case GAME_LOBBY_ID -> {
                System.out.println("Sono il server... ho ricevuto l'id della game lobby da parte di " + message.getHeader().getNicknameSender());
                System.out.println("ora devo aggiungere il player alla game lobby richiesta...");
                String gameLobbyId = message.getPayload().getContent();
                this.globalLobby.playerJoinsGameLobbyId(Integer.parseInt(gameLobbyId), message.getHeader().getNicknameSender(), clientsConnected.get(message.getHeader().getNicknameSender()));
            }
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    private void handleJoinRandomGameLobby(MessageFromClient message) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di join random game lobby da parte di " + message.getHeader().getNicknameSender());
        System.out.println("ora devo aggiungere il player alla game lobby random con un posto libero...");

        this.globalLobby.playerJoinsFirstFreeSpotInRandomGame(message.getHeader().getNicknameSender(), clientsConnected.get(message.getHeader().getNicknameSender()));
    }



}
