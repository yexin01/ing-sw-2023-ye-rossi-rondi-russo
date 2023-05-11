package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.Message;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public abstract class Client extends UnicastRemoteObject {

    @Serial
    private static final long serialVersionUID = 1922163671273214749L;

    static final int DISCONNECTION_TIME = 1500;

    private final String nickname;
    private final String ip;
    private final int port;
    private String token;

    transient Timer pingTimer;

    final transient Queue<Message> messageQueue;

    public Client(String nickname,String ip, int port) throws RemoteException {
        this.nickname = nickname;
        this.ip = ip;
        this.port = port;

        this.messageQueue = new LinkedList<>();
        this.pingTimer = new Timer();
    }

    public String getNickname() {
        return nickname;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public abstract void startConnection() throws Exception;

    public abstract void receiveMessageFromServer(Message message);

    public synchronized Message getNextMessage() {
        while (messageQueue.isEmpty()) {
            try {
                // Il thread si mette in attesa finché non viene aggiunto un nuovo messaggio
                wait();
            } catch (InterruptedException e) {
                // Gestione dell'interruzione del thread
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return messageQueue.poll();
    }

    public abstract void sendMessageToServer(Message message) throws IOException;

    public abstract void closeConnection() throws Exception;

}