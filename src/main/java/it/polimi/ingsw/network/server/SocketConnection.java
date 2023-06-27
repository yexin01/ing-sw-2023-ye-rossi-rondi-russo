package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.KeyConnectionPayload;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.message.MessageHeader;
import it.polimi.ingsw.message.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Socket connection between server and client (server side)
 */
public class SocketConnection extends Connection implements Runnable {
    private final SocketServer socketServer;
    private final Socket socket;

    private final Object outLock = new Object();
    private final Object inLock = new Object();

    private boolean connected;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Thread listener;

    /**
     * Constructor of the class SocketConnection that creates a new socket connection between server and client
     * and starts a new thread to listen for messages from the client and send them to the server (server side) through the socket connection
     * @param socketServer the server that creates the socket connection
     * @param socket the socket connection between server and client
     * @throws IOException if there are problems with the input/output streams of the socket connection
     */
    SocketConnection(SocketServer socketServer, Socket socket) throws IOException {
        this.socketServer = socketServer;
        this.socket = socket;

        this.connected = true;

        try {
            synchronized (inLock) {
                this.in = new ObjectInputStream(socket.getInputStream());
            }
            synchronized (outLock) {
                this.out = new ObjectOutputStream(socket.getOutputStream());
            }
        } catch (IOException e) {
            System.out.println("Error while creating input/output streams");
            disconnect();
        }
        listener = new Thread(this);
        listener.start();
    }

    /**
     * Method that creates the connection through the socket and when the stream is closed it disconnects the client
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (inLock) {
                    Message message = (Message) in.readObject();

                    if (message != null) {
                        if(message.getPayload().getKey().equals(KeyConnectionPayload.CONNECTION_CREATION)){
                            socketServer.login(message.getHeader().getNickname(), this);
                        } else {
                            socketServer.receiveMessageFromClient(message);
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("\nclient: "+ super.getClientPinger().getNickname() +" disconnected on his own");
                try {
                    disconnect();
                } catch (RemoteException ex) {
                    System.out.println("Error while disconnecting client");
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (Exception e) {
                System.out.println("Error while reading message from client");
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Method that checks if the socket connection is active or not
     * @return true, if the socket connection is active, false otherwise
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    /**
     * Method that sends a message to the client (server side) through the socket connection
     * @param message the message to send to the client
     * @throws IOException if there are problems with the output stream of the socket connection
     */
    @Override
    public void sendMessageToClient(Message message) throws IOException {
        if (connected) {
            try {
                synchronized (outLock) {
                    out.writeObject(message);
                    out.reset();
                }
            } catch (IOException e) {
                System.out.println("Error while sending message to client");
                disconnect();
            }
        }
    }

    /**
     * Method that disconnects the client from the server (server side) and closes the socket connection
     * @throws IOException if there are problems with the input/output streams of the socket connection
     */
    @Override
    public void disconnect() throws IOException {
        if (connected) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing socket");
            }

            assert listener != null;
            listener.interrupt(); // Interrupts the thread

            connected = false;

            socketServer.onDisconnect(this);
            System.out.println("Disconnection completed!");
        }
    }

    /**
     * Method that sends a ping message to the client (server side) through the socket connection
     * @throws IOException if there are problems with the output stream of the socket connection
     */
    @Override
    public void ping() throws IOException {
        MessageHeader header = new MessageHeader(MessageType.PING, super.getClientPinger().getNickname());
        Message message = new Message(header);

        sendMessageToClient(message);
    }

}