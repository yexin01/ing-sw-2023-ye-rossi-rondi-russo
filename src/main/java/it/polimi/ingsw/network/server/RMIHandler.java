package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.network.client.RMIClientConnection;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIHandler extends Remote {

    public void receiveMessageFromClient(MessageFromClient message) throws IOException;

    public void login(String username, RMIClientConnection client) throws Exception;

    public void disconnectMe() throws RemoteException;

}
