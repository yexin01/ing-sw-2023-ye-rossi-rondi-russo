package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;
/**
 * The LobbyHandler class extends the MessageHandler class and handles incoming messages related to lobby decisions.
 * It prompts the client interface to ask for a lobby decision when a message is received.
 */

public class LobbyHandler extends MessageHandler {

    /**
     * Constructs a LobbyHandler object with the specified client interface and client.
     *
     * @param clientInterface The client interface used for displaying messages and interacting with the client.
     * @param client The client object associated with this lobby handler.
     */
    public LobbyHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }
    /**
     * Overrides the handleMessage method from the MessageHandler class.
     * Handles the incoming message by asking the client interface for a lobby decision.
     *
     * @param mes The message object to be handled.
     * @throws Exception if an error occurs while handling the message.
     */
    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        getClientInterface().askLobbyDecision();
    }
}


