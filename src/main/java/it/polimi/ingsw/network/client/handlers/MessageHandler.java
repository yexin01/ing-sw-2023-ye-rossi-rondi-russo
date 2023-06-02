package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;
/**
 * The MessageHandler class is an abstract class that provides a base implementation for handling messages received from the server.
 * It contains references to the ClientInterface and Client objects for communication and interaction with the client application.
 */
public abstract class  MessageHandler {
    private final ClientInterface clientInterface;
    private final Client client;
    /**
     * Constructor MessageHandler.
     * @param clientInterface The client interface for communication with the client application.
     * @param client The client object for interacting with the server.
     */
    protected MessageHandler(ClientInterface clientInterface, Client client) {
        this.clientInterface = clientInterface;

        this.client = client;
    }

    /**
     * Handles the received message.
     * @param mes The message to handle.
     * @throws Exception if an error occurs while handling the message.
     */
    public abstract void handleMessage(Message mes) throws Exception;
    /**
     * Gets the client interface.
     * @return The client interface.
     */
    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    /**
     * Gets the client object.
     * @return The client object.
     */
    public Client getClient() {
        return client;
    }

}
