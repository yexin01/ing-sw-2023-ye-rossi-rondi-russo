package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.message.MessageFromServer;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public abstract class  MessageHandler {
    private final ClientInterface clientInterface;
    private final Client client;

    protected MessageHandler(ClientInterface clientInterface, Client client) {
        this.clientInterface = clientInterface;
        this.client = client;
    }


    public abstract void handleMessage(MessageFromServer mes) throws RemoteException;

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public Client getClient() {
        return client;
    }
}
