package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.Message;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

/**
 * Abstract class that creates a new client
 */
public abstract class Client extends UnicastRemoteObject {

    @Serial
    private static final long serialVersionUID = 1922163671273214749L;

    static final int DISCONNECTION_TIME = 15000;

    private final String nickname;
    private final String ip;
    private final int port;
    private String token;

    transient Timer pingTimer;

    final transient Queue<Message> messageQueue;

    /**
     * Constructor of the class Client that creates a new client
     * @param nickname the nickname of the client
     * @param ip the ip of the client
     * @param port the port of the client
     * @throws RemoteException if there are problems with the connection
     */
    public Client(String nickname,String ip, int port) throws RemoteException {
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;

        this.messageQueue = new LinkedList<>();
        this.pingTimer = new Timer();
    }

    /**
     * @return the nickname of the client
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @return the ip of the client
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return the port of the client
     */
    public int getPort() {
        return port;
    }

    /**
     * @return the token of the client
     */
    public String getToken() {
        return token;
    }

    /**
     * Method to set the token of the client
     * @param token the token of the client
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method to start the connection between client and server regardless of the type of connection (socket or RMI)
     * @throws Exception if there are problems with the connection
     */
    public abstract void startConnection() throws Exception;

    /**
     * Method to receive a message from the server regardless of the type of connection (socket or RMI)
     * @param message the message that the server sends to the client
     */
    public abstract void receiveMessageFromServer(Message message);

    /**
     * Method to get the next message from the queue of messages of the client.
     * while the queue is empty the thread waits,
     * when a new message is added to the queue the thread is notified and the message is polled from the queue
     * @return the next message from the queue of messages of the client
     */
    public synchronized Message getNextMessage() {
        while (messageQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return messageQueue.poll();
    }

    /**
     * Method to send a message to the server regardless of the type of connection (socket or RMI)
     * @param message the message that the client wants to send to the server
     * @throws Exception if there are problems with the connection
     */
    public abstract void sendMessageToServer(Message message) throws Exception;

    /**
     * Method to close the connection between client and server regardless of the type of connection (socket or RMI)
     * @throws Exception if there are problems with the connection
     */
    public abstract void closeConnection() throws Exception;

}