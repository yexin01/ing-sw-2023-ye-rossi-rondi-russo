package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.RMIClientConnection;

import java.rmi.RemoteException;

public class RMIConnection extends Connection {
    private final Server server;
    private final RMIClientConnection clientSession;
    private boolean connected = true;

    private ClientPinger clientPinger;

    public RMIConnection(Server server, RMIClientConnection clientSession) {
        this.server = server;
        this.clientSession = clientSession;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void sendMessageToClient(Message message) throws RemoteException {
        clientSession.receiveMessageFromServer(message);
    }

    @Override
    public void ping() throws RemoteException {
        try {
            clientSession.ping();
        } catch (RemoteException e) {
            disconnect();
        }
    }

    @Override
    public void disconnect() throws RemoteException {
        if (connected) {
            connected = false;
            try {
                clientSession.disconnectMe();
            } catch (RemoteException e) {
                System.out.println("\nclient: "+clientPinger.getNickname()+" disconnected on his own");
            }
            server.onDisconnect(this);
            System.out.println("Disconnection completed!");
        }
    }

    @Override
    public void setClientPinger(ClientPinger clientPinger){
        this.clientPinger = clientPinger;
    }

    @Override
    public ClientPinger getClientPinger(){
        return clientPinger;
    }

}
