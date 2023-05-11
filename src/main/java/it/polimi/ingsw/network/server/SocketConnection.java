package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.messages.ServerMessageHeader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

public class SocketConnection extends Connection implements Runnable {
    private final SocketServer socketServer;
    private final Socket socket;

    private final Object outLock = new Object();
    private final Object inLock = new Object();

    private boolean connected;

    private ObjectInputStream in;
    private ObjectOutputStream out;

    private Thread listener;

    SocketConnection(SocketServer socketServer, Socket socket) throws RemoteException {
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

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                synchronized (inLock) {
                    MessageFromClient message = (MessageFromClient) in.readObject();

                    if (message != null) {
                        if (message.getHeader().getMessageType() == EventType.CONNECTION_REQUEST) {
                            socketServer.login(message.getHeader().getNicknameSender(), this);
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
                }
            } catch (Exception e) {
                System.out.println("Error while reading message from client");
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void sendMessageToClient(MessageFromServer message) throws RemoteException {
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

    @Override
    public void disconnect() throws RemoteException {
        if (connected) {
            try {
                if (!socket.isClosed()) {
                    socket.close();
                }
            } catch (IOException e) {
                System.out.println("Error while closing socket");
            }

            listener.interrupt(); // Interrupts the thread
            connected = false;

            socketServer.onDisconnect(this);
        }
    }

    @Override
    public void ping() throws RemoteException {
        ServerMessageHeader header = new ServerMessageHeader(EventType.PING, super.getClientPinger().getNickname());
        MessageFromServer message = new MessageFromServer(header);

        sendMessageToClient(message);
    }
}