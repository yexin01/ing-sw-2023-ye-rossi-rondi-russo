package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.Socket;
import java.util.Timer;

public class ClientSocket extends Client implements Runnable {

    @Serial
    private static final long serialVersionUID = -5234333747123834779L;

    private transient Socket socket;

    private transient ObjectInputStream in;
    private transient ObjectOutputStream out;

    private transient Thread messageReceiver;

    public ClientSocket(String nickname, String address, int port) throws IOException {
        super(nickname, address, port);
    }

    @Override
    public void startConnection() throws IOException {
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

    @Override
    public void sendMessageToServer(Message message) throws IOException {
        if (out != null) {
            out.writeObject(message);
            out.reset();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Message message = (Message) in.readObject();

                if (message != null && !message.getHeader().getMessageType().equals(MessageType.PING) ) {
                    receiveMessageFromServer(message);
                    //System.out.println("NELLA RUN");
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

    private void disconnect() throws IOException {
        try {
            closeConnection();
        } catch (IOException e) {
            out.writeObject("Error while closing connection");
        }
    }

    @Override
    public void closeConnection() throws IOException {
        if (!socket.isClosed()) {
            socket.close();
        }
        messageReceiver.interrupt();

        in = null;
        out = null;
    }

    @Override
    public void receiveMessageFromServer(Message message) {
        if(message.getHeader().getMessageType().equals(MessageType.PING)){
            //System.out.println("Ping received on socket");
        } else {
           // System.out.println("sono il client... ho ricevuto il messaggio: " +message.toString() +" dal server!-------");
            addMessage(message); //to the queue
        }
    }

    public synchronized void addMessage(Message message) {
        messageQueue.add(message);
       // System.out.println("ARRIVATO MESSAGGIO");
        notify();
    }

}