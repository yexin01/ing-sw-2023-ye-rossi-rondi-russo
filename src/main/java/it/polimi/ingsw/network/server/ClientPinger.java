package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.rmi.RemoteException;

public class ClientPinger implements Runnable {
    private final String nickname;
    private final Connection connection;
    private final Object clientsLock;

    public ClientPinger(String nickname, Connection connection, Object clientsLock) {
        this.nickname = nickname;
        this.connection = connection;
        this.clientsLock = clientsLock;
    }

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
                System.out.println("Connection lost for client: " + nickname + ". Disconnecting ClientPinger thread associated.");
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
                System.out.println("ClientPinger interrupted for client: " + nickname +"\n");
                Thread.currentThread().interrupt();
            }
        }
    }

    public String getNickname() {
        return nickname;
    }

}
