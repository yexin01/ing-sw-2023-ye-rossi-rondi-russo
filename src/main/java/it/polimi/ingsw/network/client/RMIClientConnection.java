package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.MessageFromServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientConnection extends Remote {

    public void receiveMessageFromServer(MessageFromServer message) throws RemoteException;

    //Sends a ping message to client
    public void ping() throws RemoteException;

    public void disconnectMe() throws RemoteException;

}
