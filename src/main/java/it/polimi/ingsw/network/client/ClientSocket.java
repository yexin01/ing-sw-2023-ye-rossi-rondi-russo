package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.net.Socket;
import java.util.Timer;
import java.util.logging.Logger;

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

        ClientMessageHeader2 header = new ClientMessageHeader2(EventType.CONNECTION_REQUEST, getNickname());
        MessagePayload2 payload = new MessagePayload2("");
        MessageFromClient2 message = new MessageFromClient2(header, payload);

        sendMessageToServer(message);

        messageReceiver = new Thread(this);
        messageReceiver.start();
    }

    @Override
    public void sendMessageToServer(MessageFromClient2 message) throws IOException {
        if (out != null) {
            out.writeObject(message);
            out.reset();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                MessageFromServer2 message = (MessageFromServer2) in.readObject();

                if (message != null && message.getServerMessageHeader().getMessageType() != EventType.PING) {
                    receiveMessageFromServer(message);
                } else if (message != null && message.getServerMessageHeader().getMessageType() == EventType.PING) {
                    receiveMessageFromServer(message);
                    super.pingTimer.cancel();
                    super.pingTimer = new Timer();
                    super.pingTimer.schedule(new PingTimerTask(this), DISCONNECTION_TIME);
                }
            } catch (IOException e) {
                disconnect();
            } catch (ClassNotFoundException e) {
                try {
                    out.writeObject("error");
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    private void disconnect() {
        try {
            closeConnection();
        } catch (IOException e) {
            Logger.getLogger("client").severe(e.getMessage());
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
    public void receiveMessageFromServer(MessageFromServer2 message) {
        if(message.getServerMessageHeader().getMessageType().equals(EventType.PING)){
            //System.out.println("Ping received on socket");
        } else {
            System.out.println("sono il client... ho ricevuto il messaggio: " +message.toString() +" dal server!-------");
            addMessage(message); //to the queue
        }
    }

    //TODO: CONTROLLA SE ARRIVA ALL'HANDLER
    public synchronized void addMessage(MessageFromServer2 message) {
        messageQueue.add(message);
        // Notifica il thread in attesa che è stato aggiunto un nuovo messaggio
        notify();
    }

}