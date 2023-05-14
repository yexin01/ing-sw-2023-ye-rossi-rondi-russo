package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * Socket server that creates a new socket connection between server and client (server side) that extends Thread so that it can run in parallel with the other threads
 */
public class SocketServer extends Thread {
    private final Server server;
    private final int port;

    private ServerSocket serverSocket;

    /**
     * Constructor of the class SocketServer that creates a new socket server that creates a new socket connection between server and client (server side)
     * @param server the server that creates the socket server
     * @param port the port of the socket server
     */
    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Method that starts the socket server when called by Server class
     */
    void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            start();
        } catch (IOException e) {
            System.out.println("Server socket already created with port " + port);
        }
    }

    /**
     * Method that always accepts new socket connections between server and client (server side) and creates a new thread for each connection
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();
                new SocketConnection(this, client);
            } catch (IOException e) {
                System.out.println("Server socket closed");
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * Method to login to the server through the socket connection so that the server can add the player to the clientsConnected list
     * @param nickname the nickname of the player that wants to login to the server
     * @param connection the socket connection between server and client (server side)
     * @throws Exception if there are problems with the input/output streams of the socket connection
     */
    public void login(String nickname, Connection connection) throws Exception {
        server.loginToServer(nickname, connection);
    }

    /**
     * Method to receive a message from the client through the socket connection (server side) and send it to the server
     * @param message the message received from the client through the socket connection
     * @throws Exception if there are problems with the input/output streams of the socket connection
     */
    public void receiveMessageFromClient(Message message) throws Exception {
        server.receiveMessageFromClient(message);
    }

    /**
     * Method to disconnect the client from the server
     * @param playerConnection the socket connection between server and client (server side)
     * @throws IOException if there are problems with the input/output streams of the socket connection
     */
    public void onDisconnect(Connection playerConnection) throws IOException {
        server.onDisconnect(playerConnection);
    }

}
