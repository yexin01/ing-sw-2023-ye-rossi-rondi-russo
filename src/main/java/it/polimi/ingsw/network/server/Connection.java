package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.MessageFromServer2;

import java.io.IOException;

public abstract class Connection {
    private boolean connected = true;
    private String token;

    private transient ClientPinger clientPinger;

    public boolean isConnected() {
        return connected;
    }

    public abstract void sendMessageToClient(MessageFromServer2 message) throws IOException;

    public abstract void ping() throws Exception;

    public abstract void disconnect() throws Exception;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setClientPinger(ClientPinger clientPinger) {
        this.clientPinger = clientPinger;
    }

    public ClientPinger getClientPinger() {
        return clientPinger;
    }
}