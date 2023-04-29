package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.networkmessages.NetworkMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * This interface is the one sent to the server to send messages to the client through RMI protocol
 */
public interface RMIClientConnection extends Remote {

    /**
     * Sends a message to the client connected to the server through RMI protocol
     * @param message message sent to client
     */
    void messageToClient(NetworkMessage message) throws RemoteException;

    /**
     * Sends a ping message to client connected to the server through RMI protocol
     * @throws RemoteException in case of problems with communication with client
     */
    void ping() throws RemoteException;

    /**
     * Disconnects the client from the server through RMI protocol
     * @throws RemoteException in case of problems with communication with client
     */
    void disconnectMe() throws RemoteException;

}