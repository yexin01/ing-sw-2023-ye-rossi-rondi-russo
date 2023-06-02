package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

/**
 *The MessageToServerHandler class handles sending messages to the server.

 */
public class MessageToserverHandlerTurn {
    private final ClientInterface clientInterface;
    private final Client client;
    /**
     * Constructs a MessageToServerHandler object with the specified client interface and client.
     * @param clientInterface The client interface used for communication with the server.
     * @param client The client used for sending messages to the server.
     */
    public MessageToserverHandlerTurn(ClientInterface clientInterface, Client client) {
        this.clientInterface = clientInterface;
        this.client = client;
    }

    /**
     * Handles sending a message to the server.
     @param newValue The new value associated with the message.
     @param payloadKey The key representing the payload type of the message.
     @param nickname The nickname of the client.
     @param messageType The type of message being sent.
     */
    public synchronized void handleMessageToServer(Object newValue, KeyAbstractPayload payloadKey, String nickname,MessageType messageType){
        MessageHeader header=new MessageHeader(messageType, nickname);
        MessagePayload payload=new MessagePayload(payloadKey);
        payload.put(Data.VALUE_CLIENT,newValue);
        Message message=new Message(header,payload);
        try {
            client.sendMessageToServer(message);
        } catch (Exception e) {

        }
    }
}
