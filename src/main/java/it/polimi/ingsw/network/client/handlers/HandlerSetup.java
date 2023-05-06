package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class HandlerSetup implements MessageHandler{


    @Override
    public void handleMessage(MessageFromServer mes, ClientInterface clientInterface, ClientSocket clientSocket) throws RemoteException {

    }
}


