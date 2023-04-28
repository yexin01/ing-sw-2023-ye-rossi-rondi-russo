package it.polimi.ingsw.network.server;

import com.google.gson.JsonObject;
import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.messages.ConnectionResponseMessage;
import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
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

    private Game game;
    private GameController gameController;
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
        game = new Game();
        gameController = new GameController(game);

        Thread pingThread = new Thread(this);
        pingThread.start();

    }

    private void startServers() {
        SocketServer serverSocket = new SocketServer(this, socketPort);
        serverSocket.startServer();

        //TODO da mandare al client
        System.out.println("Socket Server Started");

        RMIServer rmiServer = new RMIServer(this, rmiPort);
        rmiServer.startServer();

        //TODO da mandare al client
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

            if (gameController.getGameState() == PossibleGameState.GAME_ROOM) { // Game in lobby state
                connection.sendMessage(
                        new ConnectionResponse("Successfully reconnected", token, MessageStatus.OK)
                );
            } else { // Game started
                connection.sendMessage(
                        gameController.onConnectionMessage(new LobbyMessage(username, token, null, false))
                );
            }
            //TODO da mandare nella lobby
            System.out.println(username+" reconnected to server!");
        } else { // Player already connected
            connection.sendMessage(
                    //TODO: aggiusta messaggi
                    new ConnectionResponseMessage("Player already connected", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} already connected to server!", username);
        }
    }

    /**
     * Handles a new player login
     *
     * @param username   username of the player who is trying to login
     * @param connection connection of the client
     * @throws IOException when send message fails
     */
    private void newPlayerLogin(String username, Connection connection) throws IOException {
        if (gameController.getGameInstance().isGameStarted()) { // Game Started
            connection.sendMessage(
                    new ConnectionResponse("Game is already started!", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} attempted to connect!", username);
        } else if (gameController.isLobbyFull()) { // Lobby Full
            connection.sendMessage(
                    new ConnectionResponse("Max number of player reached", null, MessageStatus.ERROR)
            );

            connection.disconnect();
            LOGGER.log(Level.INFO, "{0} tried to connect but game is full!", username);
        } else { // New player
            if (isUsernameLegit(username)) { // Username legit
                clients.put(username, connection);

                String token = UUID.randomUUID().toString();
                connection.setToken(token);

                connection.sendMessage(
                        new ConnectionResponse("Successfully connected", token, MessageStatus.OK)
                );

                LOGGER.log(Level.INFO, "{0} connected to server!", username);
            } else { // Username not legit
                connection.sendMessage(
                        new ConnectionResponse("Invalid Username", null, MessageStatus.ERROR)
                );

                connection.disconnect();
                LOGGER.log(Level.INFO, "{0} tried to connect with invalid name!", username);
            }
        }
    }

    /**
     * Checks if all player of the loaded game have joined the game
     */
    private void checkLoadReady() {
        synchronized (clientsLock) {
            if (clients.entrySet().stream().noneMatch(entry -> entry.getValue() == null || !entry.getValue().isConnected())) {
                waitForLoad = false;
                gameController.sendPrivateUpdates();
            }
        }
    }

    /**
     * Checks if a username is legit by checking that is not equal to a forbidden username
     *
     * @param username username to check
     * @return if a username is legit
     */
    private boolean isUsernameLegit(String username) {
        for (String forbidden : GameConstants.getForbiddenUsernames()) {
            if (username.equalsIgnoreCase(forbidden)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Process a message sent to server
     *
     * @param message message sent to server
     */
    void onMessage(Message message) {
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
                Message response = gameController.onMessage(message);

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
    public void sendMessageToAll(Message message) {
        for (Map.Entry<String, Connection> client : clients.entrySet()) {
            if (client.getValue() != null && client.getValue().isConnected()) {
                try {
                    client.getValue().sendMessage(message);
                } catch (IOException e) {
                    LOGGER.severe(e.getMessage());
                }
            }
        }
        LOGGER.log(Level.INFO, "Send to all: {0}", message);
    }

    /**
     * Sends a message to a client
     *
     * @param username username of the client who will receive the message
     * @param message  message to send
     */
    public void sendMessage(String username, Message message) {
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