package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientView;

import java.rmi.RemoteException;

public abstract class MessageHandler {
    private final ClientSocket connection;
    private final ClientInterface clientInterface;

    protected MessageHandler(ClientSocket connection, ClientInterface clientInterface) {
        this.connection = connection;
        this.clientInterface = clientInterface;
    }
    public abstract void handleMessage(MessageFromServer mes) throws RemoteException;


    public ClientSocket getConnection() {
        return connection;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    public ClientView getClientView() {
        return clientInterface.getCurrentView();
    }

}
