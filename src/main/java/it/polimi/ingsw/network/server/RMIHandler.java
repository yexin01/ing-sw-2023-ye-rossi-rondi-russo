package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.RMIClientConnection;
import it.polimi.ingsw.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is the one sent to the RMI client enabling him to send messages to server
 */
public interface RMIHandler extends Remote {

    void login(String username, RMIClientConnection client) throws RemoteException;

    /**
     * Sends a message to the server
     *
     * @param message message sent to server
     */
    void onMessage(Message message) throws RemoteException;

    void disconnectMe() throws RemoteException;
}
