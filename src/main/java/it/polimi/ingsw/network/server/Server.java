package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Server implements Runnable{
    private final Object clientsLock = new Object();
    private static int rmiPort = 1099;
    private static int socketPort = 1099;
    private static String ipAddress = "192.0.0.1";

    private static Server instance = null;
    private RMIServer rmiServer;
    private SocketServer socketServer;

    private ExecutorService executor;

    private ConcurrentHashMap<String, Connection> clientsConnected; //mappa di tutti i client con una connessione attiva, per quelli disconnessi c'è la lista dei disconnessi nelle gameLobby
    private GlobalLobby globalLobby;

    private final static int MAX_PLAYERS = 4;
    private final static int MIN_PLAYERS = 2;

    public Server(int rmiPort, int socketPort, String ipAddress) {
        // Verifica se il server è già stato avviato
        if (instance != null) {
            return;
        }
        Server.rmiPort = rmiPort;
        Server.socketPort = socketPort;
        Server.ipAddress = ipAddress;
        synchronized (clientsLock) {
            this.clientsConnected = new ConcurrentHashMap<>();
        }
        startServers();
        executor = new ThreadPoolExecutor(4, 20, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        globalLobby = new GlobalLobby();
    }

    /*
    Per avviare il server da terminale macbook: (da cambiare il path in base alla posizione del progetto):
    
    cd ~/Desktop/prog_sw/progsw_ingsw2023/ing-sw-2023-ye-rossi-rondi-russo
    mvn clean install
    javac src/main/java/it/polimi/ingsw/network/server/Server.java
    java src/main/java/it/polimi/ingsw/network/server/Server 6000 7000 192.168.1.100

     */
    /* se vogliamo farlo da terminale
    public static void main(String[] args){
        int rmiPort = 51633; // porta di default
        int socketPort = 51634; // porta di default
        String ipAddress = "127.0.0.1"; // indirizzo IP di default

        if (args.length >= 1) {
            rmiPort = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            socketPort = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            ipAddress = args[2];
        }

        Server server = new Server(ipAddress, rmiPort, socketPort);
        server.run();
    }
     */
    //TODO: poi saranno da togliere i parametri di default dalla CLI e forse da fare un metodo per avviare il server da terminale
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int portRmi = 1099;
        int portSocket = 1100;
        String ipAddress = "192.0.0.1";

        boolean correctInput = false;
        System.out.println("Insert the port Rmi (default 1099): (Press Enter button to use default port)");
        while (!correctInput) {
            try {
                System.out.print("> ");
                sc.reset();
                if (sc.nextLine().equals("")) {
                    portRmi = 1099;
                    break;
                }
                portRmi = Integer.parseInt(sc.next()); //did this because of InputMismatchException
                if (portRmi < 1024 || portRmi > 65535) System.out.println("Port must be between 1024 and 65535, retry");
                else correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Port must be a number, retry");
            }
        }

        correctInput = false;
        System.out.println("Insert the port Socket (default 1100): (Press Enter button to use default port)");
        while (!correctInput) {
            try {
                System.out.print("> ");

                sc.reset();
                if (sc.nextLine().equals("")) {
                    portSocket = 1100;
                    break;
                }

                portSocket = Integer.parseInt(sc.next()); //did this because of InputMismatchException
                if (portSocket < 1024 || portSocket > 65535) System.out.println("Port must be between 1024 and 65535, retry");
                else if (portSocket == portRmi) System.out.println("Port already in use, retry");
                else correctInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Port must be a number, retry");
            }
        }

        correctInput = false;
        System.out.println("Insert the IP address (default " + ipAddress + "): (Press button Enter to use default IP)");
        while (!correctInput) {
            try {
                System.out.print("> ");

                sc.reset();
                if (sc.nextLine().equals("")) {
                    ipAddress = "192.0.0.1";
                    break;
                }

                ipAddress = sc.next();
                if (!ipAddress.matches("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                        "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")) System.out.println("IP address not valid, retry");
                else correctInput = true;
            } catch (InputMismatchException e) {
                System.out.println("IP address not valid, retry");
            }
        }

        Server server = new Server(portRmi,portSocket,ipAddress);
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
            instance = new Server(rmiPort, socketPort, ipAddress);
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

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
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

    public synchronized void onDisconnect(Connection playerConnection) throws IOException {
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

    public synchronized void receiveMessageFromClient(Message message) throws Exception {
        System.out.println("\nSono il server... ho ricevuto il messaggio: "+ message.toString() + "da un client\n");
        MessageType messageType = message.getHeader().getMessageType();

        switch (messageType) {
            case LOBBY -> handleGlobalLobbyPhase(message);
            case DATA-> handleData(message);
            case ERROR -> handleErrorFromClient(message);
            default -> throw new IllegalStateException("Unexpected value: " + messageType);
        }
    }

    private synchronized void handleGlobalLobbyPhase(Message message) throws Exception {
        System.out.println("Sono il server... ho ricevuto la richiesta di join global lobby da parte di " + message.getHeader().getNickname());

        KeyLobbyPayload keyLobbyPayload = (KeyLobbyPayload) message.getPayload().getKey();
        switch (keyLobbyPayload) {
            case CREATE_GAME_LOBBY -> handleCreateGameLobby(message);
            case JOIN_SPECIFIC_GAME_LOBBY -> handleJoinSpecificGameLobby(message);
            case JOIN_RANDOM_GAME_LOBBY -> handleJoinRandomGameLobby(message);
            case QUIT_SERVER -> handleQuitServer(message);
            default -> throw new IllegalStateException("Unexpected value: " + keyLobbyPayload);
        }
    }

    private synchronized void handleCreateGameLobby(Message message) throws IOException {
        int wantedPlayers = (int) message.getPayload().getContent(Data.VALUE_CLIENT);
        System.out.println("Sono il server... ho ricevuto la richiesta di creare una nuova game lobby da parte di " + message.getHeader().getNickname() + " con " + wantedPlayers + " giocatori");

        if(wantedPlayers < MIN_PLAYERS || wantedPlayers > MAX_PLAYERS){
            MessageHeader header = new MessageHeader(MessageType.ERROR, message.getHeader().getNickname());
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
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

    private synchronized void handleQuitServer(Message message) throws Exception {
        System.out.println("Sono il server... ho ricevuto la richiesta di quit server da parte di " + message.getHeader().getNickname());
        System.out.println("ora devo disconnettere il player dalla global lobby...");

        this.globalLobby.disconnectPlayerFromGlobalLobby(message.getHeader().getNickname());
        System.out.println("Sono il server... ho disconnesso " + message.getHeader().getNickname() + " dalla globalLobby");
        this.clientsConnected.remove(message.getHeader().getNickname());
        System.out.println("Sono il server... ho disconnesso " + message.getHeader().getNickname() + " dalla lista di clientsConnected del server");
        clientsConnected.get(message.getHeader().getNickname()).disconnect();
        System.out.println("Sono il server... ho disconnesso " + message.getHeader().getNickname() + " dalla connessione con il server");
    }

    private synchronized void handleEndGamePhase(int gameId) throws IOException {
        System.out.println("Sono il server... ho ricevuto la richiesta di terminare la game lobby con id " + gameId + "perché è stata completata la partita!");
        System.out.println("ora devo terminare la game lobby...");

        this.globalLobby.endGameLobbyFromGlobalLobby(gameId);
    }

    private synchronized void handleData(Message message) throws IOException {
        String nickname = message.getHeader().getNickname();

        if(globalLobby.isPlayerActiveInAnyGameLobby(nickname)){
            System.out.println("Sono il server... ho ricevuto un messaggio di tipo DATA da parte di " + nickname);
            System.out.println("ora devo inoltrare il messaggio alla game lobby del giocatore al metodo handledata...");

            GameLobby gameLobby = this.globalLobby.findGameLobbyByNickname(nickname);
            gameLobby.handleTurn(message);

        } else {
            System.out.println("Sono il server... ho ricevuto un messaggio di tipo DATA da parte di " + nickname);
            System.out.println("ma il giocatore non è attivo in nessuna game lobby, quindi non posso inoltrare il messaggio");

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(nickname).sendMessageToClient(messageToClient);
        }
    }

    private synchronized void handleErrorFromClient(Message message) throws IOException {
        String nickname = message.getHeader().getNickname();

        if (globalLobby.isPlayerActiveInAnyGameLobby(nickname)) {
            System.out.println("Sono il server... ho ricevuto un messaggio di tipo ERROR da parte di " + nickname);
            System.out.println("ora devo inoltrare il messaggio alla game lobby del giocatore al metodo handleError...");

            GameLobby gameLobby = this.globalLobby.findGameLobbyByNickname(nickname);
            gameLobby.handleErrorFromClient(message);

        } else {
            System.out.println("Sono il server... ho ricevuto un messaggio di tipo ERROR da parte di " + nickname);
            System.out.println("ma il giocatore non è attivo in nessuna game lobby, quindi non posso inoltrare il messaggio");

            MessageHeader header = new MessageHeader(MessageType.ERROR, nickname);
            MessagePayload payload = new MessagePayload(KeyErrorPayload.ERROR_LOBBY);
            payload.put(Data.ERROR, ErrorType.ERR_GAME_NOT_FOUND);
            Message messageToClient = new Message(header, payload);
            clientsConnected.get(nickname).sendMessageToClient(messageToClient);
        }
    }

}
