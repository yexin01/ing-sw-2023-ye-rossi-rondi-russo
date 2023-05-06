package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;

import java.rmi.RemoteException;
import java.util.HashMap;

public class ManagerHandlers {

    private HashMap<EventType, MessageHandler> eventHandlerMap;

    public ManagerHandlers() {
        this.eventHandlerMap = new HashMap<>();
    }

    public void registerEventHandler(EventType eventType, MessageHandler messageHandler) {
        this.eventHandlerMap.put(eventType, messageHandler);
    }

    public synchronized void handleMessageFromServer(MessageFromServer message, ClientInterface clientInterface, ClientSocket clientSocket) throws RemoteException {
        EventType eventType=message.getServerMessageHeader().getMessageFromServer();
        MessageHandler messageHandler = this.eventHandlerMap.get(eventType);
        if (messageHandler != null) {
            messageHandler.handleMessage(message,clientInterface,clientSocket);
        }
    }

}

