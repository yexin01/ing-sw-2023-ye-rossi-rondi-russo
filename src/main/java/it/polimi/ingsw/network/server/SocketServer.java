package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketServer extends Thread {
    private final Server server;
    private final int port;

    private ServerSocket serverSocket;

    public SocketServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            start();
        } catch (IOException e) {
            System.out.println("Server socket already created with port " + port);
        }
    }

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

    public void login(String nickname, Connection connection) throws Exception {
        server.loginToServer(nickname, connection);
    }

    public void receiveMessageFromClient(Message message) throws Exception {
        server.receiveMessageFromClient(message);
    }

    public void onDisconnect(Connection playerConnection) throws IOException {
        server.onDisconnect(playerConnection);
    }

}
