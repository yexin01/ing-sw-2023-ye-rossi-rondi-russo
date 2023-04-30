package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.network.networkmessages.NetworkMessage;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * This abstract class represents a Client that can connect to the server
 */
public abstract class Client extends UnicastRemoteObject {

    @Serial
    private static final long serialVersionUID = 2056368610379158146L;

    static final int DISCONNECTION_TIME = 15000;

    private final String username;
    private final String ip;
    private final int port;
    private String token;

    transient Timer pingTimer;
    transient DisconnectionListener disconnectionListener;
    final transient List<MessageFromClient> messageQueue;

    //TODO lavora con ClientHandler per la gestione della coda di messaggi

    /**
     * This constructor creates a Client
     * @param username is the username of the client
     * @param ip is the ip of the server
     * @param port is the port of the server
     * @param token is the token of the client
     * @throws RemoteException if there are connection problems
     */
    public Client(String username,String ip, int port, String token, DisconnectionListener disconnectionListener) throws RemoteException {
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.token = token;
        this.pingTimer = new Timer();
        this.disconnectionListener = disconnectionListener;

        this.messageQueue = new ArrayList<>();
    }

    /**
     * This method returns the username of the client
     * @return the username of the client
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method returns the ip of the server
     * @return the ip of the server
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method returns the port of the server
     * @return the port of the server
     */
    public int getPort() {
        return port;
    }

    /**
     * This method returns the token of the client
     * @return the token of the client
     */
    public String getToken() {
        return token;
    }

    /**
     * This method sets the token of the client
     * @param token is the token of the client
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * This method returns the message queue of the client
     * @return the message queue of the client
     */
    public List<MessageFromClient> getMessageQueue() {
        return messageQueue;
    }

    /**
     * This method starts the connection with the server
     * @throws RemoteException if there are connection problems
     */
    public abstract void startConnection() throws Exception;

    /**
     * This method closes the connection with the server
     * @throws Exception if there are connection problems
     */
    public abstract void closeConnection() throws Exception;

    /**
     * This method sends a message to the server
     * @throws RemoteException if there are connection problems
     */
    public abstract void disconnectMe() throws RemoteException;

    /**
     * This method sends a message to the server
     * @param message is the message to send
     * @throws Exception if there are connection problems
     */
    public abstract void sendMessage(MessageFromClient message) throws Exception;

    /**
     * @return the list of messages in the queue
     */
    List<MessageFromClient> receiveMessages() {
        ArrayList<MessageFromClient> copyList;

        synchronized (messageQueue) {
            copyList = new ArrayList<>(List.copyOf(messageQueue));
            messageQueue.clear();
        }

        return copyList;
    }

}
