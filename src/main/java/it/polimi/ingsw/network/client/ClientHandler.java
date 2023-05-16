package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.handlers.*;
import it.polimi.ingsw.view.ClientInterface;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class that creates a new client handler that handles the messages received from the server
 */
public class ClientHandler implements Runnable {
    private Client client;

    private Thread messageHandlerThread;
    private BlockingQueue<Message> queueToHandle = new LinkedBlockingQueue<>();
    private ManagerHandlers managerHandlers=new ManagerHandlers();
    // is a buffer between the clientHandler thread and the thread that receives messages from the network connection (whether it is RMI or Socket)
    private boolean isRMI;

    /**
     * the run method of the class ClientHandler that handles the messages received from the server
     * when the queue of messages to handle is not empty, it takes the first message from the queue and handles it
     */
    public void run() {
        while (true) {
            synchronized (this) {
                while (queueToHandle.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Message message = queueToHandle.poll();
                try {
                    handleMessageFromServer(message);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Method to add a message to the queue of messages to handle
     * @param message the message to add to the queue
     */
    public synchronized void addMessageToQueue(Message message) {
        queueToHandle.add(message);
        notify();
    }

    /**
     * Method to create a new message handler thread that handles the messages received from the server:
     * if the client is a ClientRMI, it handles the message directly
     * if the client is a ClientSocket, it adds the message to the queue of messages to handle
     * @param client the client that receives the messages from the server
     */
    public void createMessageHandlerThread(Client client) {
        this.client = client;
        messageHandlerThread = new Thread(() -> {
            while (true) {
                Message message = null;
                try {
                    message = client.getNextMessage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (isRMI) {
                    try {
                        handleMessageFromServer(message);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    synchronized (this) {
                        addMessageToQueue(message);
                        try {
                            handleMessageFromServer(message);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        messageHandlerThread.start();
    }

    /**
     * Method to create a new connection between client and server
     * @param isRMI 0 if the connection is a socket connection, 1 if the connection is an RMI connection
     * @param ip the ip of the server
     * @param port the port of the server
     * @param clientInterface the client interface of the client
     * @throws Exception if there are problems with the connection
     */
    public void createConnection(int isRMI,String ip, int port,ClientInterface clientInterface) throws Exception {
        String connection;
        String nickname=clientInterface.getClientView().getNickname();
        System.out.println(clientInterface.getClientView().getNickname()+"NEL CLIENT HANDLER");
        if (isRMI==0) {
            client = new ClientSocket(nickname, ip, port);
            this.isRMI = false;
            System.out.println("creato ClientSocket in createConnection()...");
            connection="SOCKET";
        } else {
            client = new ClientRMI(nickname, ip, port);
            this.isRMI = true;
            System.out.println("creato ClientRMI in createConnection()...");
            connection="RMI";
        }
        managerHandlers.registerEventHandler(MessageType.DATA,new TurnHandler(clientInterface,client,new StartAndEndGameHandler(clientInterface,this.client)));
        LobbyHandler lobbyHandler=new LobbyHandler(clientInterface,client);
        managerHandlers.registerEventHandler(MessageType.LOBBY,lobbyHandler);
        managerHandlers.registerEventHandler(MessageType.ERROR,new ErrorHandler(clientInterface,client));
        managerHandlers.registerEventHandler(MessageType.CONNECTION,new ConnectionHandler(clientInterface,client));
        clientInterface.getClientView().setMessageToserverHandler(new MessageToserverHandlerTurn(clientInterface,client));

        System.out.println("creato messageHandlerThread...");
        System.out.println("provo a startare la connection di tipo " + connection + "...");
        createMessageHandlerThread(client);
        client.startConnection();

    }

    /**
     * Method to handle the messages received from the server and to call the right handler for the message received
     * @param message the message received from the server
     * @throws Exception if there are problems with the connection
     */
    public synchronized void handleMessageFromServer(Message message) throws Exception {
        managerHandlers.handleMessageFromServer(message);
    }

}
