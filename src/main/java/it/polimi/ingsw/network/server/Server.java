package it.polimi.ingsw.network.server;


import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.PhaseGame;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * This class is the main server class which starts a Socket and a RMI server.
 * It handles all the client regardless of whether they are Sockets or RMI
 */
public class Server implements Runnable {
    private final Object clientsLock = new Object();
    private int socketPort = 12345;
    private int rmiPort = 12346;

    private HashMap<String, Connection> clients;
    private HashMap<String, Integer> playersId;
    private int maxPlayers;

    private GameController gameController;
    private PhaseController<PhaseGame> gamePhaseController;

    private boolean waitForLoad;


    /**
     * Starts the server with a new game
     *
     */
    public Server() throws Exception {
        synchronized (clientsLock) {
            clients = new HashMap<>();
        }

        startServers();
        //gameController = new GameController();
        gamePhaseController =new PhaseController<>(PhaseGame.GAME_SETUP);
        GameRules gameRules=new GameRules();
        maxPlayers=gameRules.getMaxPlayers();
        Thread pingThread = new Thread(this);
        pingThread.start();

    }

    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();

        System.out.println("Socket Server Started");

        RMIServer rmiServer = new RMIServer(this, rmiPort);
        rmiServer.startServer();

        System.out.println("RMI Server Started");
    }

    /**
     * Reserves server slots for player loaded from the game save
     *
     * @param loadedPlayers from the game save
     */
    private void reserveSlots(List<Player> loadedPlayers) {
        synchronized (clientsLock) {
            for (Player player : loadedPlayers) {
                clients.put(player.getNickname(), null);
            }
        }
    }

    public static void main(String[] args) {
        try {
            Server server = new Server();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds or reconnects a player to the server
     *
     * @param username   username of the player
     * @param connection connection of the client
     */
    void login(String username, Connection connection) {
        try {
            synchronized (clientsLock) {
                if (clients.containsKey(username)) {
                    knownPlayerLogin(username, connection);
                } else {
                    //newPlayerLogin(username, connection);
                }
            }
        } catch (IOException e) {
            connection.disconnect();
        }
    }

    /**
     * Handles a known player login
     *
     * @param username   username of the player who is trying to login
     * @param connection connection of the client
     * @throws IOException when send message fails
     */
    private void knownPlayerLogin(String username, Connection connection) throws IOException {
/*
        if (clients.get(username) == null || !clients.get(username).isConnected()) { // Player Reconnection
            clients.replace(username, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            //TODO gestiscono gli handler in modo da inviare i dati della modelview se il gioco e gia cominciato

            if (gamePhaseController.getCurrentPhase() == PhaseGame.GAME_SETUP) { // Game in lobby state
                sendError(ErrorType);

                /*MessagePayload payload = new MessagePayload(null);
                payload.put(PayloadKeyServer.NETWORK_CONTENT, "Reconnection to lobby successful!");
                ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.CONNECTION_RESPONSE, connection);
                connection.sendMessage(new MessageFromServer(header, payload));


            } else { // Game started
                //TODO conviene scrivere un metodo di invio di messaggio e chiamare sempre quello
                // (inserendo come parametro la stringa da stampare)

                MessagePayload payload = new MessagePayload();
                payload.put(KeyPayload.NETWORK_CONTENT,"Reconnection to game successful!");
                MessageFromServer message=new MessageFromServer(new ServerMessageHeader(EventType.DISCONNECT,username),payload);
                connection.sendMessage(message);

                //TODO: to reconnect the client to his game (with an handler)
                //gameController.onConnectionMessage(new LobbyMessage(username, token, null, false))

            }
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, username + " reconnected to server!"));
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.BROADCAST, connection);
            sendMessageToAllExcept(new MessageFromServer(header, payload), username);

        } else { // Player already connected
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, username + " already connected to server!");
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.NETWORK_ERROR, connection);
            sendMessageToAllExcept(new MessageFromServer(header, payload), username);

            connection.sendMessage(new MessageFromServer(header,payload));
            connection.disconnect();
        }*/
    }

    /**
     * Handles a new player login
     *
     * @param username   username of the player who is trying to login
     * @param connection connection of the client
     * @throws IOException when send message fails
     */
    private void newPlayerLogin(String username, Connection connection) throws Exception {


        if (gamePhaseController.getCurrentPhase() == PhaseGame.GAME_STARTED) {
            sendError(ErrorType.GAME_STARTED,username,connection);
        }
        ErrorType possibleError=addPlayer(username);
        if(possibleError==null){
            sendError(ErrorType.TOO_MANY_PLAYERS,username,connection);
        }else{
            String token = UUID.randomUUID().toString();
            connection.setToken(token);
            ServerMessageHeader header=new ServerMessageHeader(EventType.SETUP,username);
            MessagePayload payload=new MessagePayload();
            payload.put(KeyPayload.WHO_CHANGE,username);
            payload.put(KeyPayload.PLAYERS,playersId);
            //TODO mandarlo a tutti
            connection.sendMessage(new MessageFromServer(header, payload));

        }

    }

    /**
     * Process a message sent to server
     *
     * @param message message sent to server
     */
    void onMessage(MessageFromClient message) throws IOException {
        /*
        if (message != null && message.getNicknameSender() != null) {

            String msgToken = message.getToken();
            Connection conn;

            synchronized (clientsLock) {
                connection = clients.get(message.getNicknameSender());
            }

            if (connection == null) {
                System.out.println("Message request from unknown Username");

            } else {

                gameController.receiveMessageFromClient(message); //it handles the game logic and the response ack, ecc

                //TODO: create response message ping

                // send message to client
                sendMessage(message.getSenderUsername(), response);
            }
        }

         */
    }

    /**
     * Updates the timer state
     */
    private void updateTimer() {
        /*
        if (Game.getInstance().isGameStarted()) {
            Connection conn;

            synchronized (clientsLock) {
                conn = clients.get(gameController.getTurnOwnerUsername());
            }

            moveTimer.cancel();
            moveTimer = new Timer();
            moveTimer.schedule(new MoveTimer(conn, gameController.getTurnOwnerUsername()), moveTime);

            LOGGER.log(Level.INFO, "Move timer reset for user {0}, {1} seconds left", new Object[]{gameController.getTurnOwnerUsername(), moveTime / 1000});
        }

         */
    }

    /**
     * Called when a player disconnects
     *
     * @param playerConnection connection of the player that just disconnected
     */
    void onDisconnect(Connection playerConnection) {
        /*
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, "Successfully connected as a new player");
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.CONNECTION_RESPONSE, username);
            connection.sendMessage(new MessageFromServer(header, payload));

            LOGGER.log(Level.INFO, "{0} disconnected from server!", username);

            if (gamePhaseController.getCurrentPhase()== PhaseGame.GAME_SETUP) {
                synchronized (clientsLock) {
                    clients.remove(username);
                }


                gameController.onMessage(new LobbyMessage(username, null, null, true));
                LOGGER.log(Level.INFO, "{0} removed from client list!", username);
            } else {
                gameController.onConnectionMessage(new LobbyMessage(username, null, null, true));
                sendMessageToAll(new DisconnectionMessage(username));
            }
        }

         */
    }

    /**
     * Sends a message to all clients
     *
     * @param message message to send
     */
    public void sendMessageToAll(MessageFromServer message) {
        for (Map.Entry<String, Connection> client : clients.entrySet()) {
            if (client.getValue() != null && client.getValue().isConnected()) {
                try {
                    client.getValue().sendMessage(message);
                } catch (IOException e) {
                    //TODO: send a message to the server as an error

                }
            }
        }
        //LOGGER.log(Level.INFO, "Send to all: {0}", message);
    }

    /**
     * Sends a message to all clients except one
     * @param message message to send
     * @param username username of the client who will not receive the message
     */
    public void sendMessageToAllExcept(MessageFromServer message, String username) {
        for (Map.Entry<String, Connection> client : clients.entrySet()) {
            if (client.getValue() != null && client.getValue().isConnected() && !client.getKey().equals(username)) {
                try {
                    client.getValue().sendMessage(message);
                } catch (IOException e) {
                    //LOGGER.severe(e.getMessage());
                }
            }
        }
        //LOGGER.log(Level.INFO, "Send to all except {0}: {1}", new Object[]{username, message});
    }

    /**
     * Sends a message to a client
     *
     * @param username username of the client who will receive the message
     * @param message  message to send
     */
    public void sendMessage(String username, MessageFromServer message) {
        synchronized (clientsLock) {
            for (Map.Entry<String, Connection> client : clients.entrySet()) {
                if (client.getKey().equals(username) && client.getValue() != null && client.getValue().isConnected()) {
                    try {
                        client.getValue().sendMessage(message);
                    } catch (IOException e) {
                        //LOGGER.severe(e.getMessage());
                    }
                    break;
                }
            }
        }

        //LOGGER.log(Level.INFO, "Send: {0}, {1}", new Object[]{message.getSenderUsername(), message});
    }

    /**
     * Returns the username of the connection owner
     *
     * @param connection connection to check
     * @return the username
     */
    private String getUsernameByConnection(Connection connection) {
        Set<String> usernameList;
        synchronized (clientsLock) {
            usernameList = clients.entrySet()
                    .stream()
                    .filter(entry -> connection.equals(entry.getValue()))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
        }
        if (usernameList.isEmpty()) {
            return null;
        } else {
            return usernameList.iterator().next();
        }
    }

    /**
     * Process that pings all the clients to check if they are still connected
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (clientsLock) {
                for (Map.Entry<String, Connection> client : clients.entrySet()) {
                    if (client.getValue() != null && client.getValue().isConnected()) {
                        client.getValue().ping();
                    }
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //LOGGER.severe(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }

    public ErrorType addPlayer(String nickname) {
        if (playersId.containsKey(nickname)) {
            return ErrorType.DUPLICATE_NAME;
        } else if (playersId.size() >= maxPlayers) {
            return ErrorType.TOO_MANY_PLAYERS;
        } else {
            int playerCount = playersId.size();
            playersId.put(nickname, playerCount);
            return null;
        }
    }
    public void sendError(ErrorType error,String nickname,Connection connection){
        ServerMessageHeader header=new ServerMessageHeader(EventType.ERROR,nickname);
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.MESSAGE_ERROR,error.getErrorMessage());
        MessageFromServer message=new MessageFromServer(header,null);
        //TODO inviare il messaggio
        connection.disconnect();

    }


}
