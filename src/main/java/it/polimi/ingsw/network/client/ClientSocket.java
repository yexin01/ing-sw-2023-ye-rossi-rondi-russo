package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.Socket;
import java.util.Timer;

/**
 * Class that creates a new client socket
 */
public class ClientSocket extends Client implements Runnable {

    @Serial
    private static final long serialVersionUID = -5234333747123834779L;

    private transient Socket socket;

    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    private transient Thread messageReceiver;

    /**
     * Constructor of the class ClientSocket that creates a new client socket
     * @param nickname the nickname of the client
     * @param address the address of the client
     * @param port the port of the client
     * @throws IOException if there are problems with the connection
     */
    public ClientSocket(String nickname, String address, int port) throws IOException {
        super(nickname, address, port);
    }

    /**
     * Method that starts the connection with the server socket and creates a new thread for the message receiver
     * and a new message for the connection creation to the server socket to initialize the connection
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void startConnection() throws IOException {
        //System.out.println("Connecting to server from socket with ip: " + getIp() + " and port: " + getPort());
        socket = new Socket(getIp(), getPort());
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        MessageHeader header = new MessageHeader(MessageType.CONNECTION, getNickname());
        MessagePayload payload = new MessagePayload(KeyConnectionPayload.CONNECTION_CREATION);
        Message message = new Message(header, payload);

        sendMessageToServer(message);

        messageReceiver = new Thread(this);
        messageReceiver.start();
    }

    /**
     * Method that sends a message to the server socket
     * @param message the message to send
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void sendMessageToServer(Message message) throws IOException {
        if (out != null) {
            out.writeObject(message);
            out.reset();
        }
    }

    /**
     * run method of the thread that receives messages from the server socket and calls the receiveMessageFromServer method
     * if the message is a ping message it cancels the ping timer and creates a new one that means that the connection is still active
     * if the thread is interrupted it closes the connection with the server socket
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = (Message) in.readObject();

                if (message != null && !message.getHeader().getMessageType().equals(MessageType.PING) ) {
                    receiveMessageFromServer(message);
                } else if (message != null && message.getHeader().getMessageType().equals(MessageType.PING)) {
                    receiveMessageFromServer(message);
                    super.pingTimer.cancel();
                    super.pingTimer = new Timer();
                    super.pingTimer.schedule(new PingHandler(this), DISCONNECTION_TIME);
                }
            } catch (IOException e) {
                try {
                    disconnect();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (ClassNotFoundException e) {
                try {
                    out.writeObject("error while reading message from server (class not found)");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    /**
     * Method that tries to close the connection with the server socket and if it fails it sends an error message
     * @throws IOException if there are problems with the connection
     */
    private void disconnect() throws IOException {
        try {
            closeConnection();
        } catch (IOException e) {
            out.writeObject("Error while closing connection");
        }
    }

    /**
     * Method that closes the connection with the server socket and interrupts the message receiver thread
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void closeConnection() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
        }
        messageReceiver.interrupt();

        in = null;
        out = null;
    }

    /**
     * Method that receives a message from the server socket and adds it to the message queue if it is not a ping message
     * @param message the message received from the server socket
     */
    @Override
    public synchronized void receiveMessageFromServer(Message message) {
        if(!message.getHeader().getMessageType().equals(MessageType.PING)){
            addMessage(message);
        }
    }

    /**
     * Method that adds a message to the message queue and notifies the thread that receives messages from the server socket
     * @param message the message to add
     */
    public synchronized void addMessage(Message message) {
        messageQueue.add(message);
        notify();
    }

}