package it.polimi.ingsw.network.client;



import it.polimi.ingsw.message.MessageFromServer;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientHandler implements Runnable {
    private Client client;
    private Thread messageHandlerThread;
    private BlockingQueue<MessageFromServer> queueToHandle = new LinkedBlockingQueue<>();
    //serve da buffer tra il thread del clientHandler e il thread che riceve i messaggi dalla connessione di rete (sia essa di tipo RMI o Socket)

//TODO l updater handler fa una cosa simile possiamo unire le due funzioni oppure tenere solo questo e l'handler non gestisce le cose con i thread ma in maniera sequenziale
    /*
    public void run() {
        while (true) {
            MessageFromServer message;
            try {
                message = queueToHandle.take(); // prende un messaggio dalla coda di messaggi
                System.out.println("ho preso il messaggio dalla coda di messaggi...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                System.out.println("ora gestisco il messaggio...");
                handleMessageFromServer(message); // gestisce il messaggio
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

     */


    public void run() {
        while (true) {
            synchronized(this) {
                while(queueToHandle.isEmpty()) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                MessageFromServer message = queueToHandle.poll();
                try {
                    System.out.println("ora gestisco il messaggio...");
                    handleMessageFromServer(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public synchronized void addMessageToQueue(MessageFromServer message) {
        queueToHandle.add(message);
        System.out.println("aggiunto il messaggio alla coda di messaggi...");
        notify();
    }

    public void createMessageHandlerThread(Client client){
        // Creazione del thread per la gestione dei messaggi
        messageHandlerThread = new Thread(() -> {
            while (true) {
                MessageFromServer message = null;
                try {
                    message = client.getNextMessage();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if (client instanceof ClientSocket) {
                    // se il client è un ClientSocket, aggiunge il messaggio alla coda di messaggi
                    synchronized (this) {
                        addMessageToQueue(message);
                    }
                } else {
                    // se il client è un ClientRMI, gestisce il messaggio direttamente
                    try {
                        handleMessageFromServer(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        messageHandlerThread.start();
    }

    public void createConnection(int connectionType, String nickname, String ip, int port) throws Exception {
        if (connectionType == 0) {
            client = new ClientSocket(nickname, ip, port);
            System.out.println("creato ClientSocket in createConnection()...");
        } else if (connectionType == 1){
            client = new ClientRMI(nickname, ip, port);
            System.out.println("creato ClientRMI in createConnection()...");
        }
        createMessageHandlerThread(client);
        System.out.println("creato messageHandlerThread...");
        client.startConnection();
        System.out.println("provo a startare la connection di tipo " + connectionType + "...");
    }

    public synchronized void handleMessageFromServer(MessageFromServer message) throws IOException {
        System.out.println("sono il clientHandler.. " + message.toString());

        if (message.getServerMessageHeader().getMessageType().equals(EventType.DISCONNECT_REQUEST)) {
            //TODO non si può usare questa funzione
            //client.getRMIHandler().disconnectMe();
            System.exit(0);
        } else if (message.getServerMessageHeader().getMessageType().equals(EventType.PING)) {
            //System.out.println("Ping received su socket");
        } else if (message.getServerMessageHeader().getMessageType().equals(EventType.JOIN_GLOBAL_LOBBY)) {
            System.out.println("\nJoined global lobby\n");
        } else {
            //TODO: tutti gli altri casi
        }
    }

}