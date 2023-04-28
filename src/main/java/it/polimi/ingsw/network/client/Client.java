package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public abstract class Client extends UnicastRemoteObject {

    @Serial
    private static final long serialVersionUID = 2056368610379158146L;
    // per avere random serialversionUID clicca Option+Enter sul mac dopo =

    private final String username;
    private final String ip;
    private final int port;

    //TODO serve token per lo scambio dei messaggi?
    //TODO lavora con listener

    final transient List<Message> messageQueue;

    public Client(String username,String ip, int port) throws RemoteException {
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.messageQueue = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public List<Message> getMessageQueue() {
        return messageQueue;
    }

    public abstract void startConnection() throws Exception;

    public abstract void closeConnection() throws Exception;

    public abstract void disconnectMe() throws RemoteException;

    public abstract void sendMessage(Message message) throws Exception;

}

