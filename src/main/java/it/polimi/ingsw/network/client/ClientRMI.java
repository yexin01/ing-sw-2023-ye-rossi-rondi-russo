package it.polimi.ingsw.network.client;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.messages.MessageFromClient2;
import it.polimi.ingsw.messages.MessageFromServer2;
import it.polimi.ingsw.network.server.RMIHandler;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

public class ClientRMI extends Client implements RMIClientConnection {

    @Serial
    private static final long serialVersionUID = 5273563045160703715L;

    private transient RMIHandler server;

    public ClientRMI (String nickname, String ip, int port) throws RemoteException {
        super(nickname, ip, port);
    }

    @Override
    public void startConnection() throws Exception {
        Registry registry = LocateRegistry.getRegistry(getIp(), getPort());
        server = (RMIHandler) registry.lookup("MyShelfieServer");
        System.out.println("chiamo login su RMIHANDLER...");
        server.login(getNickname(), this);
    }

    @Override
    public void sendMessageToServer(MessageFromClient2 message) throws IOException {
        if (server == null) {
            throw new RemoteException();
        }
        server.receiveMessageFromClient(message);
    }

    @Override
    public void receiveMessageFromServer(MessageFromServer2 message) {
        System.out.println("sono il client... ho ricevuto il messaggio: " +message.toString() +" dal server!-------");
        addMessage(message); //to the queue
    }

    public synchronized void addMessage(MessageFromServer2 message) {
        messageQueue.add(message);
        // Notifica il thread in attesa che è stato aggiunto un nuovo messaggio
        notify();
    }

    @Override
    public synchronized Message getNextMessage() {
        while (messageQueue.isEmpty()) {
            try {
                // Il thread si mette in attesa finché non viene aggiunto un nuovo messaggio
                wait();
            } catch (InterruptedException e) {
                // Gestione dell'interruzione del thread
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return messageQueue.poll();
    }

    @Override
    public void ping() throws RemoteException {
        //System.out.println("Ping received on RMI");

        super.pingTimer.cancel();
        super.pingTimer = new Timer();
        super.pingTimer.schedule(new PingTimerTask(this), DISCONNECTION_TIME);

    }

    @Override
    public void disconnectMe() throws RemoteException {
        server = null;
    }

    @Override
    public void closeConnection() throws RemoteException {
        server.disconnectMe();
        server = null;
    }

    public RMIHandler getRMIHandler(){
        return server;
    }

}
