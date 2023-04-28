package it.polimi.ingsw.network.client;

package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.networkmessages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is the one sent to the server to send messages to the client
 */
public interface RMIClientConnection extends Remote {

    void messageToClient(Message message) throws RemoteException;

    void ping() throws RemoteException;

    void disconnectMe() throws RemoteException;

}