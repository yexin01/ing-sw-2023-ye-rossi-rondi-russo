package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;

import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract class that creates a new connection between server and client (server side)
 */
public abstract class Connection implements Serializable {
    private boolean connected = true;
    private String token;

    private transient ClientPinger clientPinger;

    /**
     * Method that returns if the connection is connected or not
     * @return true if the connection is connected, false if it is not
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Method to send a message to the client through the connection regardless of the type of connection (socket or RMI)
     * @param message the message that the server wants to send to the client
     * @throws IOException if there are problems with the connection
     */
    public abstract void sendMessageToClient(Message message) throws IOException;

    /**
     * Method that pings the client to check if the connection is still alive or not through the connection regardless of the type of connection (socket or RMI)
     * @throws Exception if there are problems with the connection
     */
    public abstract void ping() throws Exception;

    /**
     * Method to disconnect from the server through the connection regardless of the type of connection (socket or RMI)
     * @throws Exception if there are problems with the connection
     */
    public abstract void disconnect() throws Exception;

    /**
     * @return the token of the connection
     */
    public String getToken() {
        return token;
    }

    /**
     * Method to set the token of the connection
     * @param token the token of the connection
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method to set the client pinger of the connection
     * @param clientPinger the client pinger of the connection
     */
    public void setClientPinger(ClientPinger clientPinger) {
        this.clientPinger = clientPinger;
    }

    /**
     * @return the client pinger of the connection
     */
    public ClientPinger getClientPinger() {
        return clientPinger;
    }
}