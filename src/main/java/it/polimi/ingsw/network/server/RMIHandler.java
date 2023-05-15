package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.RMIClientConnection;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI handler interface that is sent to the client to communicate with the server
 */
public interface RMIHandler extends Remote {

    /**
     * Method that receives a message from the client and sends it to the server
     * @param message the message received from the client
     * @throws Exception if there are problems with the connection
     */
    void receiveMessageFromClient(Message message) throws Exception;

    /**
     * Method to login to the server through the RMI connection so that the server can add the player to the clientsConnected list
     * @param username the username of the player that wants to login to the server
     * @param client the RMI connection between server and client
     * @throws Exception if there are problems with the connection
     */
    void login(String username, RMIClientConnection client) throws Exception;

    /**
     * Method to disconnect from the server through the RMI connection so that the server can remove the player from the clientsConnected list
     * @throws IOException if there are problems with the connection
     */
    void disconnectMe() throws IOException;

}
