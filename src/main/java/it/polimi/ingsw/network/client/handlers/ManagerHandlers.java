package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.MessageType;
import java.util.HashMap;
/**
 * The ManagerHandlers class manages the mapping between message types and their corresponding message handlers.
 * It allows for registering and retrieving message handlers based on the message type.
 */
public class ManagerHandlers {

    private HashMap<MessageType, MessageHandler> eventHandlerMap;
    /**
     * Constructor ManagerHandlers
     */
    public ManagerHandlers() {
        this.eventHandlerMap = new HashMap<>();
    }
    /**
     * Registers a message handler for a specific message type.
     * @param eventType The message type to register the handler for.
     * @param messageHandler The message handler to register.
     */
    public void registerEventHandler(MessageType eventType, MessageHandler messageHandler) {
        this.eventHandlerMap.put(eventType, messageHandler);
    }
    /**
     * Handles a message received from the server by invoking the appropriate message handler.
     * @param message The message received from the server.
     * @throws Exception if an error occurs while handling the message.
     */

    public synchronized void handleMessageFromServer(Message message) throws Exception {
        MessageType eventType=message.getHeader().getMessageType();
        MessageHandler messageHandler = this.eventHandlerMap.get(eventType);
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }

}



