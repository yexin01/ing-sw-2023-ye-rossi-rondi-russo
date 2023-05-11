package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer2;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class HandlerSetup implements MessageHandler{


    @Override
    public void handleMessage(MessageFromServer2 mes, ClientInterface clientInterface, ClientSocket clientSocket) throws RemoteException {

    }
}


