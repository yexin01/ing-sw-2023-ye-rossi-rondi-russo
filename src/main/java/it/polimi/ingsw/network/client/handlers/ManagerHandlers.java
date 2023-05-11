package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.KeyAbstractPayload;
import it.polimi.ingsw.message.Message;

import it.polimi.ingsw.message.MessageType;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ManagerHandlers {

    private HashMap<MessageType, MessageHandler> eventHandlerMap;

    public ManagerHandlers() {
        this.eventHandlerMap = new HashMap<>();
    }

    public void registerEventHandler(MessageType eventType, MessageHandler messageHandler) {
        this.eventHandlerMap.put(eventType, messageHandler);
    }

    public synchronized void handleMessageFromServer(Message message) throws Exception {
        MessageType eventType=message.getHeader().getMessageType();
        MessageHandler messageHandler = this.eventHandlerMap.get(eventType);
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }

}



