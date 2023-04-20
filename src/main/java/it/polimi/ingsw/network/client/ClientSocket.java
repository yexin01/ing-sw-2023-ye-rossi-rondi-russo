package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientSocket extends Client implements Runnable{

    @Serial
    private static final long serialVersionUID = -6118099010326718532L;

    private transient Socket socket;

    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    private transient Thread messageReceiver;

    public ClientSocket(String username, String password, String ip, int port) throws RemoteException {
        super(username, password, ip, port);
    }

    @Override
    public void startConnection() throws IOException {
        socket = new Socket(getIp(), getPort());
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        //sendMessage(new ConnectionRequest(getUsername()));

        messageReceiver = new Thread(this);
        messageReceiver.start();
    }

    @Override
    public void disconnectMe() throws RemoteException {
        try {
            closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
            //Logger.getLogger("client").severe(e.getMessage());
        }
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        if (out != null) {
            try {
                out.writeObject(message);
                out.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

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

    @Override
    public void run() {
        //TODO to adapt
        while (!socket.isClosed()) {
            try {
                Message message = (Message) in.readObject();
                synchronized (messageQueue) {
                    messageQueue.add(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




}
