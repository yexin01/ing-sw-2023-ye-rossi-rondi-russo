package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The remote interface of the client RMI sent to the server to communicate with the client. It is implemented by the client RMI class.
 */
public interface RMIClientConnection extends Remote {

    /**
     * Method that receives a message from the server and sends it to the client
     * @param message the message received from the server
     * @throws RemoteException if there are problems with the connection
     */
    void receiveMessageFromServer(Message message) throws RemoteException;

    /**
     * Method that sends a ping message to the client
     * @throws RemoteException if there are problems with the connection
     */
    void ping() throws RemoteException;

    /**
     * Method to disconnect from the server through the RMI connection so that the server can remove the player from the clientsConnected list
     * @throws RemoteException if there are problems with the connection
     */
    void disconnectMe() throws RemoteException;

}
