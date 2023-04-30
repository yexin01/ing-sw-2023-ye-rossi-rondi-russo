package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;

import java.rmi.RemoteException;

public class HandlerSetup extends MessageHandler{
    public HandlerSetup(ClientSocket connection, ClientInterface clientInterface) {
        super(connection, clientInterface);
    }

    @Override
    public void handleMessage(MessageFromServer mes) throws RemoteException {
        MessageFromClient message=new MessageFromClient(EventType.NICKNAME,null, getClientInterface().askNickname(), null);
        getConnection().sendMessage(message);
    }
}
