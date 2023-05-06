package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromClient;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.handlers.HandlerUpdater;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * This class represents a Socket connection with a server as a client
 */

public class ClientSocket extends Client implements Runnable{

    @Serial
    private static final long serialVersionUID = -6118099010326718532L;

    private transient Socket socket;

    private HandlerUpdater handlerUpdater;
    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    private transient Thread messageReceiver;



    /**
     * Constructs a connection over the socket with the server as a client with the given username and token and ip and port of the server to connect to
     * @param username is the username of the client
     * @param ip is the ip of the server to connect to
     * @param port is the port of the server to connect to
     * @param token is the token of the client
     * @throws RemoteException if there are connection problems
**/
    public ClientSocket(String username, String ip, int port, String token,ClientInterface clientInterface) throws RemoteException {
        super(username,ip,port,token,clientInterface);
    }



    /**
     * This method starts the connection with the server as a client over the socket and starts the message receiver thread to receive messages from the server
     * @throws IOException if there are connection problems
     **/
    @Override
    public void startConnection() throws IOException {
        socket = new Socket(getIp(), getPort());
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        //TODO ask to giuliaR how to set it
        MessageFromClient message=new MessageFromClient(EventType.CONNECT,getUsername(),null);
        sendMessage(message);

        messageReceiver = new Thread(this);
        messageReceiver.start();
    }

    /**
     * This method disconnects the client from the server by closing the connection and interrupting the message receiver thread with the method closeConnection
     * @throws RemoteException if there are connection problems
     **/
    //TODO questo verrà tolto lo farà l handler disconnectionHandler
    @Override
    public void disconnectMe() throws RemoteException {
        try {
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while closing connection");
        }
    }

    /**
     * This method sends a message to the server over the socket connection using the writeObject method of the ObjectOutputStream
     * @param message is the message to send
     * @throws RemoteException if there are connection problems
     **/
    @Override
    public void sendMessage(MessageFromClient message) throws RemoteException {
        if (out != null) {
            try {
                out.writeObject(message);
                out.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method closes the connection with the server by closing the socket and setting the ObjectInputStream and the ObjectOutputStream to null and interrupting the message receiver thread
     * @throws RemoteException if there are connection problems
     **/
    @Override
    public void closeConnection() throws RemoteException {
        try {
            if (!socket.isClosed()) {
                socket.close();
            }
            messageReceiver.interrupt();
            in = null;
            out = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This method receives messages from the server over the socket connection using the readObject method of the ObjectInputStream and adds them to the message queue of the client to be processed by the client itself
     **/
    @Override
    public void run() {
        //TODO to adapt
        while (!socket.isClosed()) {
            try {
                MessageFromServer message = (MessageFromServer) in.readObject();
                synchronized (messageQueue) {
                    messageQueue.add(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public HandlerUpdater getHandlerUpdater() {
        return handlerUpdater;
    }

    public void setHandlerUpdater(HandlerUpdater handlerUpdater) {
        this.handlerUpdater = handlerUpdater;
    }
}
