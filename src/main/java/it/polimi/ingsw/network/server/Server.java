package it.polimi.ingsw.network.server;

package it.polimi.ingsw.network.server;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.GamePhase;
import it.polimi.ingsw.controller.GamePhaseController;
import it.polimi.ingsw.controller.SetupController;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

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

    private Map<String, Connection> clients;

    private GameController gameController;
    private GamePhaseController gamePhaseController;
    private SetupController setupController;
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
        gameController = new GameController();

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
                    newPlayerLogin(username, connection);
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

        if (clients.get(username) == null || !clients.get(username).isConnected()) { // Player Reconnection
            clients.replace(username, connection);

            String token = UUID.randomUUID().toString();
            connection.setToken(token);

            if (gamePhaseController.getCurrentGamePhase() == GamePhase.GAME_SETUP) { // Game in lobby state
                MessagePayload payload = new MessagePayload(null);
                payload.put(PayloadKeyServer.NETWORK_CONTENT, "Reconnection to lobby successful!");
                ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.CONNECTION_RESPONSE, username);
                connection.sendMessage(new MessageFromServer(header, payload));
            } else { // Game started
                MessagePayload payload = new MessagePayload(null);
                payload.put(PayloadKeyServer.NETWORK_CONTENT, "Reconnection to game successful!");
                ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.CONNECTION_RESPONSE, username);
                connection.sendMessage(new MessageFromServer(header, payload));

                //TODO: to reconnect the client to his game (with an handler)
                //gameController.onConnectionMessage(new LobbyMessage(username, token, null, false))

            }
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, username + " reconnected to server!"));
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.BROADCAST, username);
            sendMessageToAllExcept(new MessageFromServer(header, payload), username);

        } else { // Player already connected
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, username + " already connected to server!");
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.NETWORK_ERROR, username);
            sendMessageToAllExcept(new MessageFromServer(header, payload), username);

            connection.sendMessage(new MessageFromServer(header,payload));
            connection.disconnect();
        }
    }

    /**
     * Handles a new player login
     *
     * @param username   username of the player who is trying to login
     * @param connection connection of the client
     * @throws IOException when send message fails
     */
    private void newPlayerLogin(String username, Connection connection) throws Exception {

        if (gamePhaseController.getCurrentGamePhase() == GamePhase.GAME_STARTED) { // Game Started
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, "Game already started! Cannot join!");
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.NETWORK_ERROR, username);
            connection.sendMessage(new MessageFromServer(header, payload));

            connection.disconnect();

        } else if (setupController.isFull()) { // Waiting room is full
            MessagePayload payload = new MessagePayload(null);
            payload.put(PayloadKeyServer.NETWORK_CONTENT, "Max number of player reached!");
            ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.NETWORK_ERROR, username);
            connection.sendMessage(new MessageFromServer(header, payload));

            connection.disconnect();

        } else { // New player
            if (setupController.checkNickname(username)) { // Username valid
                clients.put(username, connection);

                String token = UUID.randomUUID().toString();
                connection.setToken(token);

                MessagePayload payload = new MessagePayload(null);
                payload.put(PayloadKeyServer.NETWORK_CONTENT, "Successfully connected as a new player");
                ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.CONNECTION_RESPONSE, username);
                connection.sendMessage(new MessageFromServer(header, payload));

                payload = new MessagePayload(null);
                payload.put(PayloadKeyServer.NETWORK_CONTENT, username + " connected to server!");
                header = new ServerMessageHeader(MessageFromServerType.BROADCAST, username);
                sendMessageToAllExcept(new MessageFromServer(header, payload), username);

            } else { // Username not valid
                MessagePayload payload = new MessagePayload(null);
                payload.put(PayloadKeyServer.NETWORK_CONTENT, "Invalid Username");
                ServerMessageHeader header = new ServerMessageHeader(MessageFromServerType.NETWORK_ERROR, username);
                connection.sendMessage(new MessageFromServer(header, payload));

                //TODO: a bit extreme, maybe we can just send a message to the client
                connection.disconnect();

            }
        }
    }

    /**
     * Process a message sent to server
     *
     * @param message message sent to server
     */
    void onMessage(MessageFromServer message) {
        if (message != null && message.getSenderUsername() != null && (message.getToken() != null || message.getSenderUsername().equals("god"))) {
            if (message.getContent().equals(MessageContent.SHOOT)) {
                String messageString = message.toString();
                LOGGER.log(Level.INFO, messageString);
            } else {
                LOGGER.log(Level.INFO, "Received: {0}", message);
            }

            String msgToken = message.getToken();
            Connection conn;

            synchronized (clientsLock) {
                conn = clients.get(message.getSenderUsername());
            }

            if (conn == null) {
                LOGGER.log(Level.INFO, "Message Request {0} - Unknown username {1}", new Object[]{message.getContent().name(), message.getSenderUsername()});
            } else if (msgToken.equals(conn.getToken())) { // Checks that sender is the real player
                NetworkMessage response = gameController.onMessage(message);

                updateTimer();

                // send message to client
                sendMessage(message.getSenderUsername(), response);
            }
        }
    }

    /**
     * Updates the timer state
     */
    private void updateTimer() {
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
    }

    /**
     * Called when a player disconnects
     *
     * @param playerConnection connection of the player that just disconnected
     */
    void onDisconnect(Connection playerConnection) {
        String username = getUsernameByConnection(playerConnection);

        if (username != null) {
            LOGGER.log(Level.INFO, "{0} disconnected from server!", username);

            if (gameController.getGameState() == PossibleGameState.GAME_ROOM) {
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
        LOGGER.log(Level.INFO, "Send to all: {0}", message);
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
                    LOGGER.severe(e.getMessage());
                }
            }
        }
        LOGGER.log(Level.INFO, "Send to all except {0}: {1}", new Object[]{username, message});
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
                        LOGGER.severe(e.getMessage());
                    }
                    break;
                }
            }
        }

        LOGGER.log(Level.INFO, "Send: {0}, {1}", new Object[]{message.getSenderUsername(), message});
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
                LOGGER.severe(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }
    }
}
