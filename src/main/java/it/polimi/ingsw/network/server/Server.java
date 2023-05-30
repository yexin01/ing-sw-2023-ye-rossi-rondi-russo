package it.polimi.ingsw.network.server;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Server class that handles the connection with the clients
 */
public class Server implements Runnable{
    private final Object clientsLock = new Object();
    private static int rmiPort;
    private static int socketPort;
    private static String ipAddress = null;

    private static Server instance = null;
    private RMIServer rmiServer;
    private SocketServer socketServer;

    private ExecutorService executor;

    private ConcurrentHashMap<String, Connection> clientsConnected; //map of all the clients with an active connection, for the disconnected ones there is the list of disconnected in the gameLobby
    private GlobalLobby globalLobby;

    private static int MAX_PLAYERS;
    private static int MIN_PLAYERS;
    static int MAX_LENGTH_NICKNAME;
    static int MIN_LENGTH_NICKNAME;

    /**
     * Constructor of the server
     * @param rmiPort port for the rmi connection
     * @param socketPort port for the socket connection
     * @param ipAddress ip address of the server
     */
    public Server(int rmiPort, int socketPort, String ipAddress) {
        GameRules gameRules;
        try {
            gameRules = new GameRules();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MAX_PLAYERS= gameRules.getMaxPlayers();
        MIN_PLAYERS= gameRules.getMinPlayers();
        MAX_LENGTH_NICKNAME= gameRules.getMaxCharactersPlayers();
        MIN_LENGTH_NICKNAME= gameRules.getMinCharactersPlayers();
        // it checks if there is already an instance of the server
        if (instance != null) {
            return;
        }
        Server.rmiPort = rmiPort;
        Server.socketPort = socketPort;
        synchronized (clientsLock) {
            this.clientsConnected = new ConcurrentHashMap<>();
        }
        startServers(ipAddress);
        executor = new ThreadPoolExecutor(4, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        globalLobby = new GlobalLobby();
    }

    /**
     * Method to set the ip address of the server
     * @param ipAddress ip address of the server
     */
    private static void setIpAddress(String ipAddress) {
        Server.ipAddress = ipAddress;
    }

    public String getIpAddress() {
        return Server.ipAddress;
    }

    /*
    Per avviare il server da terminale macbook: (da cambiare il path in base alla posizione del progetto):
    
    cd ~/Desktop/prog_sw/progsw_ingsw2023/ing-sw-2023-ye-rossi-rondi-russo
    mvn clean install
    javac src/main/java/it/polimi/ingsw/network/server/Server.java
    java src/main/java/it/polimi/ingsw/network/server/Server 6000 7000 192.168.1.100

     */
    //TODO: da fare il jar per lanciare da terminale windows e mac
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int portRmi = 1099;
        int portSocket = 1100;
        String ipAddress = "127.0.0.1";

        boolean correctInput = false;
        System.out.println("Insert the RMI port (default "+portRmi+"): (Press Enter to use default port)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();
                if (input.equals("")) {
                    portRmi = 1099;
                    break;
                }
                portRmi = Integer.parseInt(input);
                if (portRmi < 1024 || portRmi > 65535)
                    System.out.println("Port must be between 1024 and 65535, retry");
                else
                    correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Port must be a number, retry");
            }
        }

        correctInput = false;
        System.out.println("Insert the Socket port (default "+portSocket+"): (Press Enter to use default port)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();
                if (input.equals("")) {
                    portSocket = 1100;
                    break;
                }
                portSocket = Integer.parseInt(input);
                if (portSocket < 1024 || portSocket > 65535)
                    System.out.println("Port must be between 1024 and 65535, retry");
                else if (portSocket == portRmi)
                    System.out.println("Port already in use, retry");
                else
                    correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Port must be a number, retry");
            }
        }

        correctInput = false;
        System.out.println("Insert the IP address (default " + ipAddress + "): (Press Enter to use default IP)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                String input = sc.nextLine();
                if (input.equals("")) {
                    ipAddress = "127.0.0.1";
                    break;
                }
                ipAddress = input;
                if (!ipAddress.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"))
                    System.out.println("Invalid IP address, retry");
                else
                    correctInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid IP address, retry");
            }
        }
        System.out.println("Starting server with RMI port: " + portRmi + ", Socket port: " + portSocket + ", IP address: " + ipAddress + "\n");

        Server server = new Server(portRmi, portSocket, ipAddress);
        server.run();
    }

    /**
     * Starts the server (RMI and Socket) and prints the IP address of the server on the console
     */
    private void startServers(String ipAddress){
        if (instance != null) {
            System.out.println("Servers already started");
            return;
        }
        instance = this;
        setIpAddress(ipAddress);

        instance.rmiServer = new RMIServer(this, rmiPort);
        instance.rmiServer.startServer();
        System.out.println("RMI Server started on port: " + rmiPort );

        instance.socketServer = new SocketServer(this, socketPort);
        instance.socketServer.startServer();
        System.out.println("Socket Server started on port: " + socketPort + "\n");

        System.out.println("Server started successfully with IP address: " + ipAddress + "\n");
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

    /**
     * This method is used to get the instance of the server if it is already started and to start it if it is not already started
     * @return the instance of the server if it is already started, otherwise it starts the server and returns the instance of the server
     * @throws IOException if the server is not started
     */
    public static synchronized Server getInstance() throws IOException {
        if (instance == null) {
            instance = new Server(rmiPort, socketPort, ipAddress);
        }
        return instance;
    }

    /**
     * Method that handles the login of a player to the server into 2 cases: if the player is already known or not known by the server
     * @param nickname of the player
     * @param connection of the player
     * @throws Exception if the player is already connected or if the nickname is already used
     */
    public synchronized void loginToServer(String nickname, Connection connection) throws Exception {
        //it works on the map of clientsConnected then it will enter in the globalLobby and then in the gameLobby
        try {
            synchronized (clientsLock) {
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

    /**
     * Method that handles the login of a new player to the server
     * @param nickname of the player
     * @param connection of the player
     * @throws Exception if the player used an invalid nickname (too long or too short)
     */
    private synchronized void newPlayerLogin(String nickname, Connection connection) throws Exception {
        if (checkNickname(nickname)) { // nickname legit
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            System.out.println(nickname + " connected to server!");
            this.globalLobby.addPlayerToWaiting(nickname, connection,false);

        } else { // nickname not legit
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_CONNECTION);
            payload.put(Data.ERROR, ErrorType.ERR_NICKNAME_LENGTH);
            connection.sendMessageToClient(new Message(header,payload));

            connection.disconnect();
            System.out.println("Attention! " + nickname + " tried to connect with invalid name length! Connection refused!");
        }
    }

    /**
     * Method that handles the login of a player to the server if the player is already known by the server into 2 cases: if the player was disconnected or if the nickname is already used
     * @param nickname of the player
     * @param connection of the player
     * @throws Exception if the player is already connected or if the nickname is already used
     */
    private synchronized void knownPlayerLogin(String nickname, Connection connection) throws Exception {
        if (globalLobby.isPlayerDisconnectedInAnyGameLobby(nickname)) { // player was disconnected
            clientsConnected.put(nickname, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            MessageHeader header = new MessageHeader(MessageType.CONNECTION, nickname);
            MessagePayload payload = new MessagePayload(KeyConnectionPayload.BROADCAST);
            String content = "Login was successful! You will be reconnected to your previous game...";
            payload.put(Data.CONTENT, content);
            connection.sendMessageToClient(new Message(header, payload));

            System.out.println(nickname + " reconnected to server!");
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

    /**
     * Method that checks if the nickname is valid (not too long or too short)
     * @param nickname of the player
     * @return true if the nickname is valid, false otherwise
     */
    private boolean checkNickname(String nickname) {
        return nickname.length() <= MAX_LENGTH_NICKNAME && nickname.length() >= MIN_LENGTH_NICKNAME;
    }

    /**
     * Method that handles the disconnection of a player from the server
     * @param playerConnection of the player
     * @throws IOException if the player is not connected to the server
     */
    public synchronized void onDisconnect(Connection playerConnection) throws IOException {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            synchronized (clientsLock) {
                System.out.println(username + " disconnected from server!");
                this.globalLobby.disconnectPlayerFromGlobalLobby(username);
                clientsConnected.remove(username);
            }
        }
    }

    /**
     * Method that returns the username of a player given his connection from the map of clientsConnected to the server
     * @param connection of the player
     * @return the username of the player
     */
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

    /**
     * Method that handles the message received from a client
     * @param message received from the client
     * @throws Exception if the message is not valid
     */
    public synchronized void receiveMessageFromClient(Message message) throws Exception {
        MessageType messageType = message.getHeader().getMessageType();
        switch (messageType) {
            case LOBBY -> handleGlobalLobbyPhase(message);
            case DATA-> handleData(message);
            case ERROR -> handleErrorFromClient(message);
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    /**
     * Method that handles a message of type LOBBY received from a client into 4 cases: create a new game lobby, join a specific game lobby, join a random game lobby, quit the server
     * @param message received from the client
     * @throws Exception if the message is not valid
     */
    private synchronized void handleGlobalLobbyPhase(Message message) throws Exception {
        KeyLobbyPayload keyLobbyPayload = (KeyLobbyPayload) message.getPayload().getKey();
        switch (keyLobbyPayload) {
            case CREATE_GAME_LOBBY -> handleCreateGameLobby(message);
            case JOIN_SPECIFIC_GAME_LOBBY -> handleJoinSpecificGameLobby(message);
            case JOIN_RANDOM_GAME_LOBBY -> handleJoinRandomGameLobby(message);
            case QUIT_SERVER -> handleQuitServer(message);
            default -> throw new IllegalStateException("Unexpected value: " + keyLobbyPayload);
        }
    }

    /**
     * Method that handles the creation of a new game lobby by a client given the number of players wanted in the game
     * @param message received from the client
     * @throws IOException if the number of players wanted is not valid
     */
    private synchronized void handleCreateGameLobby(Message message) throws IOException {
        int wantedPlayers = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        if(wantedPlayers < MIN_PLAYERS || wantedPlayers > MAX_PLAYERS){
            MessageHeader header = new MessageHeader(MessageType.ERROR, message.getHeader().getNickname());
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_NUM_PLAYER_WANTED);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(message.getHeader().getNickname()).sendMessageToClient(messageToClient);

            System.out.println("Attention! " + message.getHeader().getNickname() + " tried to create a game lobby with invalid number of players!");
            return;
        }
        this.globalLobby.playerCreatesGameLobby(wantedPlayers, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    /**
     * Method that handles the join of a client to a specific game lobby given the id of the game lobby he wants to join
     * @param message received from the client
     * @throws IOException if the game lobby is full or if the game lobby is not found in the list of game lobbies of the server
     */
    private synchronized void handleJoinSpecificGameLobby(Message message) throws IOException {
        int gameId = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        this.globalLobby.playerJoinsGameLobbyId(gameId, message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    /**
     * Method that handles the join of a client to a random game lobby with a free spot in it if there is one otherwise it creates a new game lobby with minimum number of players and adds the client to it
     * @param message received from the client
     * @throws IOException if the game lobby is full
     */
    private synchronized void handleJoinRandomGameLobby(Message message) throws IOException {
        this.globalLobby.playerJoinsFirstFreeSpotInRandomGame(message.getHeader().getNickname(), clientsConnected.get(message.getHeader().getNickname()));
    }

    /**
     * Method that handles the quit of a client from the server by disconnecting him from the global lobby and removing him from the list of clients connected to the server
     * @param message received from the client
     * @throws Exception if the client is not found in the list of clients connected to the server
     */
    private synchronized void handleQuitServer(Message message) throws Exception {
        this.globalLobby.disconnectPlayerFromGlobalLobby(message.getHeader().getNickname());
        this.clientsConnected.remove(message.getHeader().getNickname());
        clientsConnected.get(message.getHeader().getNickname()).disconnect();
    }

    /**
     * Method that handles a message of type DATA received from a client
     * @param message received from the client
     * @throws IOException if the message is not valid
     */
    private synchronized void handleData(Message message) throws IOException {
        String nickname = message.getHeader().getNickname();

        if(globalLobby.isPlayerActiveInAnyGameLobby(nickname)){
            GameLobby gameLobby = this.globalLobby.findGameLobbyByNickname(nickname);
            gameLobby.handleTurn(message);
        } else {
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(nickname).sendMessageToClient(messageToClient);
        }
    }

    /**
     * Method that handles a message of type ERROR received from a client and forwards it to the game lobby of the client who sent it to the server
     * if he is active in a game lobby otherwise it sends an error message
     * @param message received from the client
     * @throws IOException if the message is not valid
     */
    private synchronized void handleErrorFromClient(Message message) throws IOException {
        String nickname = message.getHeader().getNickname();

        if (globalLobby.isPlayerActiveInAnyGameLobby(nickname)) {
            GameLobby gameLobby = this.globalLobby.findGameLobbyByNickname(nickname);
            gameLobby.handleErrorFromClient(message);

        } else {
            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(nickname).sendMessageToClient(messageToClient);
        }
    }

}
