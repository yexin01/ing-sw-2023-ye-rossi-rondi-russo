package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.handlers.ManagerHandlers;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements Runnable {

    private Client client;
    private Thread messageHandlerThread;
    private BlockingQueue<Message> queueToHandle = new LinkedBlockingQueue<>();
    //serve da buffer tra il thread del clientHandler e il thread che riceve i messaggi dalla connessione di rete (sia essa di tipo RMI o Socket)

    private boolean isRMI;

   public void run() {
        while (true) {
            synchronized (this) {
                while (queueToHandle.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                Message message = queueToHandle.poll();
                try {
                    handleMessageFromServer(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void addMessageToQueue(Message message) {
        queueToHandle.add(message);
        notify();
    }

    public void createMessageHandlerThread(Client client) {
        this.client = client;
        messageHandlerThread = new Thread(() -> {
            while (true) {
                Message message = null;
                try {
                    message = client.getNextMessage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (isRMI) {
                    // se il client è un ClientRMI, gestisce il messaggio direttamente
                    try {
                        handleMessageFromServer(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // se il client è un ClientSocket, aggiunge il messaggio alla coda di messaggi
                    synchronized (this) {
                        addMessageToQueue(message);
                        try {
                            handleMessageFromServer(message);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        });
        messageHandlerThread.start();
    }

    public void createConnection(int connectionType, String nickname, String ip, int port) throws Exception {
        if (connectionType == 0) {
            client = new ClientSocket(nickname, ip, port);
            isRMI = false;
            System.out.println("creato ClientSocket in createConnection()...");
            connection="RMI";
        } else {
            client = new ClientRMI(nickname, ip, port);
            isRMI = true;
            System.out.println("creato ClientRMI in createConnection()...");
        }
        createMessageHandlerThread(client);
        System.out.println("creato messageHandlerThread...");
        System.out.println("provo a startare la connection di tipo " + connection + "...");
        client.startConnection();

    }

    public synchronized void handleMessageFromServer(Message message) throws IOException {
        System.out.println("sono il clientHandler.. " + message.toString());


        KeyConnectionPayload key = (KeyConnectionPayload) message.getPayload().getKey();
        System.out.println(message.getHeader().getMessageType());
        System.out.println(message.getPayload().getKey());
        System.out.println(message.getPayload().getContent(Data.CONTENT));

        /*
        switch (key){
            case JOIN_GLOBAL_LOBBY:
                System.out.println("\nJoined global lobby\n");

                break;

        }

         */



        //TODO qui si gestiscono i messaggi che arrivano dal server
        /*
        EventType type = message.getHeader().getMessageType();

        switch (type) {
            case CONNECTION_CREATED:
                System.out.println("Connection created");
                break;
            case DISCONNECTION_FORCED: //il server forza la disconnessione del client
                //TODO non si può usare questa funzione
                //client.getRMIHandler().disconnectMe();
                System.exit(0);
                break;
            case PING:
                //System.out.println("Ping received su socket");
                break;
            case JOIN_GLOBAL_LOBBY:
                System.out.println("\nJoined global lobby\n");
                break;
            default:
                break;

        }
         */
    }
}
