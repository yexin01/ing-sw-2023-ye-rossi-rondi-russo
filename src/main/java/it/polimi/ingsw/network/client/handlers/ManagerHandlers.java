package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.messages.MessageFromServerType;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ManagerHandlers {

    private HashMap<MessageFromServerType, MessageHandler> eventHandlerMap;

    public ManagerHandlers() {

        this.eventHandlerMap = new HashMap<>();

    }

    public void registerEventHandler(MessageFromServerType eventType, MessageHandler messageHandler) {
        this.eventHandlerMap.put(eventType, messageHandler);
    }

    public void handleMessage(MessageFromServerType eventType, MessageFromServer message) throws RemoteException {
        MessageHandler messageHandler = this.eventHandlerMap.get(eventType);
        if (messageHandler != null) {
            messageHandler.handleMessage(message);
        }
    }
}
