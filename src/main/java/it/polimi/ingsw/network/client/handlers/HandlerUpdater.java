package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

public class HandlerUpdater implements Runnable {
    private final ClientSocket socket;
    private final ClientInterface clientInterface;
    private ManagerHandlers managerHandlers;

    public HandlerUpdater(ClientSocket socket, ClientInterface clientInterface) {
        this.socket = socket;
        this.clientInterface = clientInterface;
        managerHandlers=new ManagerHandlers();
        managerHandlers.registerEventHandler(EventType.COLUMN,new DataHandler());
        managerHandlers.registerEventHandler(EventType.BOARD_SELECTION,new DataHandler());
        managerHandlers.registerEventHandler(EventType.ORDER_TILES,new DataHandler());
        managerHandlers.registerEventHandler(EventType.DISCONNECT,new DisconnectionHandler());
        managerHandlers.registerEventHandler(EventType.SETUP,new HandlerSetup());
        managerHandlers.registerEventHandler(EventType.START_GAME,new StartAndEndGameHandler());
        managerHandlers.registerEventHandler(EventType.END_GAME,new StartAndEndGameHandler());

        this.thread = new Thread(this);
        this.thread.start();
        waitingForAck = false;
    }

    public ClientSocket getClient() {
        return socket;
    }

    public ClientInterface getClientInterface() {
        return clientInterface;
    }

    private final LinkedList<MessageFromServer> messageQueue = new LinkedList<>();
    private boolean waitingForAck = false;
    private boolean waitNextMessage = false;
    private Thread thread;

    @Override
    public void run() {
        //TODO idea iniziale, poi in base a come verrà implementata può cambiare
        System.out.println("UPDATER");
        //String lastHandledMessage = null;
        //boolean waitingForAck = false;
        while (!Thread.currentThread().isInterrupted()) {
            ArrayList<MessageFromServer> messages = socket.receiveMessages();
            for (MessageFromServer s : messages) {
                if (!messageQueue.isEmpty()) {
                    if (s.getServerMessageHeader().getMessageFromServer() != EventType.ACK) {
                        messageQueue.add(s);
                    } else {
                        messageQueue.remove(0);
                        if (messageQueue.isEmpty()) {
                            waitingForAck = false;
                        } else {
                            try {
                                managerHandlers.handleMessageFromServer(messageQueue.get(0),getClientInterface(),getClient());
                            } catch (RemoteException e) {
                                throw new RuntimeException(e);
                            }
                            waitingForAck = true;
                        }

                    }
                } else {
                    messageQueue.add(s);
                    try {
                        managerHandlers.handleMessageFromServer(s,getClientInterface(),getClient());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    waitingForAck = true;
                }
            }
        }
    }
}
