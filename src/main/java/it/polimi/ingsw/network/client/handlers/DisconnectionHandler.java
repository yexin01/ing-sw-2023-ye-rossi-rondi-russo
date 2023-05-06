package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;

import java.rmi.RemoteException;

public class DisconnectionHandler implements MessageHandler{

    @Override
    public void handleMessage(MessageFromServer mes, ClientInterface clientInterface, ClientSocket clientSocket) throws RemoteException {
    //TODO gestire la disconnessione visualizzando un messaggio
    }
}
