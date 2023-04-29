package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.handlers.MessageHandler;

import java.util.HashMap;

public class ManagerHandlers {

    private HashMap<String, MessageHandler> eventHandlerMap;

    public ManagerHandlers() {
        this.eventHandlerMap = new HashMap<>();
    }

    public void registerEventHandler(String eventType, MessageHandler messageHandler) {
        this.eventHandlerMap.put(eventType, messageHandler);
    }

    public void handleMessage(String eventType, MessageFromServer message) {
        MessageHandler messageHandler = this.eventHandlerMap.get(eventType);
        if (messageHandler != null) {
            messageHandler.handleMessageFromServer(message);
        }
    }
}
