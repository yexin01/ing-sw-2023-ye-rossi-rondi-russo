package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Class that pings the client to check if the connection is still alive
 */
public class ClientPinger implements Runnable {
    private final String nickname;
    private final Connection connection;
    private final Object clientsLock;

    /**
     * Constructor of the class ClientPinger that creates a new client pinger
     * @param nickname the nickname of the client
     * @param connection the connection between server and client (server side)
     * @param clientsLock the lock of the clients
     */
    public ClientPinger(String nickname, Connection connection, Object clientsLock) {
        this.nickname = nickname;
        this.connection = connection;
        this.clientsLock = clientsLock;
    }

    /**
     * Method that pings the client to check if the connection is still alive or not through the RMI connection
     * if the connection is lost, the client is disconnected from the server, and the thread is interrupted
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            if (connection.isConnected()) {
                try {
                    connection.ping();
                } catch (Exception e) {
                    synchronized (clientsLock) {
                        try {
                            Server.getInstance().onDisconnect(connection);
                        } catch (RemoteException ex) {
                            ex.printStackTrace();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    Thread.currentThread().interrupt();
                }
            } else {
                synchronized (clientsLock) {
                    try {
                        Server.getInstance().onDisconnect(connection);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                Thread.currentThread().interrupt();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * @return the nickname of the client
     */
    public String getNickname() {
        return nickname;
    }

}
