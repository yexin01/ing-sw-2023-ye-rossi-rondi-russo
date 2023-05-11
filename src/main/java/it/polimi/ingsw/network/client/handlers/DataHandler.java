package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.MessageFromServer;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class DataHandler extends MessageHandler {

    protected DataHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(MessageFromServer mes) throws RemoteException {

    }

}
