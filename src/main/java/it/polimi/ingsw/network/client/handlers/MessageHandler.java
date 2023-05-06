package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer;

import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public interface MessageHandler {
    public void handleMessage(MessageFromServer mes, ClientInterface clientInterface, ClientSocket clientSocket) throws RemoteException;
}
