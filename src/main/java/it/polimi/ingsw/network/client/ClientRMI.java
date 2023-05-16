package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.server.RMIHandler;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

/**
 * Class that creates a new client RMI
 */
public class ClientRMI extends Client implements RMIClientConnection {

    @Serial
    private static final long serialVersionUID = 5273563045160703715L;

    private transient RMIHandler server;

    /**
     * Constructor of the class ClientRMI that creates a new client RMI
     * @param nickname the nickname of the client
     * @param ip the ip of the client
     * @param port the port of the client
     * @throws RemoteException if there are problems with the connection
     */
    public ClientRMI (String nickname, String ip, int port) throws RemoteException {
        super(nickname, ip, port);
    }

    /**
     * Method that starts the connection with the server RMI and calls the login method on the server RMI handler
     * @throws Exception if there are problems with the connection
     */
    @Override
    public void startConnection() throws Exception {
        Registry registry = LocateRegistry.getRegistry(getIp(), getPort());
        server = (RMIHandler) registry.lookup("MyShelfieServer");
        System.out.println("chiamo login su RMIHANDLER...");
        server.login(getNickname(), this);
    }

    /**
     * Method that sends a message to the server RMI handler calling the receiveMessageFromClient method on the server RMI handler
     * @param message the message to send
     * @throws Exception if there are problems with the connection
     */
    @Override
    public void sendMessageToServer(Message message) throws Exception {
        if (server == null) {
            throw new RemoteException();
        }
        server.receiveMessageFromClient(message);
    }

    /**
     * Method that receives a message from the server RMI handler and adds it to the queue of messages to send to the client
     * @param message the message to send
     */
    @Override
    public void receiveMessageFromServer(Message message) {
        System.out.println("sono il client... ho ricevuto il messaggio: " +message.toString() +" dal server!-------");
        addMessage(message);
    }

    /**
     * Method that adds a message to the queue of messages to send to the client and notifies the thread in waiting that a new message has been added to the queue
     * @param message the message to add
     */
    public synchronized void addMessage(Message message) {
        messageQueue.add(message);
        notify();
    }

    /**
     * Method that gets the next message from the queue of messages to send to the client and puts the thread in waiting if the queue is empty
     * @return the next message from the queue of messages to send to the client
     */
    @Override
    public synchronized Message getNextMessage() {
        while (messageQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return messageQueue.poll();
    }

    /**
     * Method that pings the server RMI handler to check if the connection is still active creating a new timer that calls the ping method on the server RMI handler
     * and if it is not active it starts the disconnection procedure
     * @throws RemoteException if there are problems with the connection
     */
    @Override
    public void ping() throws RemoteException {
        super.pingTimer.cancel();
        super.pingTimer = new Timer();
        super.pingTimer.schedule(new PingHandler(this), DISCONNECTION_TIME);
    }

    /**
     * Method that disconnects the client and sets the server RMI handler to null
     * @throws RemoteException if there are problems with the connection
     */
    @Override
    public void disconnectMe() throws RemoteException {
        server = null;
    }

    /**
     * Method that closes the connection with the server RMI handler and sets the server RMI handler to null
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void closeConnection() throws IOException {
        server.disconnectMe();
        server = null;
    }

}
