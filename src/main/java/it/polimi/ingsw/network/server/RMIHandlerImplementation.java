package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.RMIClientConnection;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI handler implementation that creates a new RMI connection between server and client (server side)
 */
public class RMIHandlerImplementation extends UnicastRemoteObject implements RMIHandler {

    @Serial
    private static final long serialVersionUID = 1401929399999030519L;

    private final transient Server server;
    private transient RMIConnection rmiSession;

    /**
     * Constructor of the class RMIHandlerImplementation that creates a new RMI handler implementation that creates a new RMI connection
     * @param server the server that creates the RMI handler implementation
     * @throws RemoteException if there are connection problems
     */
    public RMIHandlerImplementation(Server server) throws RemoteException {
        this.server = server;
    }

    /**
     * Method that receives a message from the client and sends it to the server
     * @param message the message received from the client
     * @throws Exception if there are problems with the connection
     */
    @Override
    public synchronized void receiveMessageFromClient(Message message) throws Exception {
        server.receiveMessageFromClient(message);
    }

    /**
     * Method to login to the server through the RMI connection so that the server can add the player to the clientsConnected list
     * @param nickname the nickname of the player that wants to login to the server
     * @param client the RMI connection between server and client (server side)
     * @throws Exception if there are problems with the connection
     */
    @Override
    public void login(String nickname, RMIClientConnection client) throws Exception {
        rmiSession = new RMIConnection(server, client);
        server.loginToServer(nickname, rmiSession);
    }

    /**
     * Method to disconnect from the server through the RMI connection so that the server can remove the player from the clientsConnected list
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void disconnectMe() throws IOException {
        rmiSession.disconnect();
    }

}


