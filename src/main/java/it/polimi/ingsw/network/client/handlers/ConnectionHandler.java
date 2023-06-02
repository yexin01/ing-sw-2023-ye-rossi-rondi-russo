package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.Data;
import it.polimi.ingsw.message.KeyConnectionPayload;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;
/**
 * The ConnectionHandler class extends the MessageHandler class and handles incoming messages related to connection events.
 * It provides methods to handle and process connection-related messages, such as reconnection notifications.
 */
public class ConnectionHandler extends MessageHandler{
    /**
     * Constructs a ConnectionHandler object with the specified client interface and client.
     *
     * @param clientInterface The client interface used for displaying messages and interacting with the client.
     * @param client The client object associated with this connection handler.
     */
    public ConnectionHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }
    /**
     * Overrides the handleMessage method from the MessageHandler class.
     * Handles the incoming message and performs the necessary actions based on the message content.
     *
     * @param mes The message object to be handled.
     * @throws Exception if an error occurs while handling the message.
     */
    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        KeyConnectionPayload key= (KeyConnectionPayload) mes.getPayload().getKey();
        getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.CONTENT));
        if(key.equals(KeyConnectionPayload.RECONNECTION)){
            if(mes.getPayload().getContent(Data.WHO_CHANGE).equals(getClientInterface().getClientView().getNickname())){
                getClientInterface().getClientView().somethingWrong();
            }
        }
    }
}
