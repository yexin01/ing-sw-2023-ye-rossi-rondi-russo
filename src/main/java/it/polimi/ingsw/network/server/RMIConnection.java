package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.RMIClientConnection;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * RMI connection class that creates a new RMI connection between server and client (server side)
 */
public class RMIConnection extends Connection {
    private final Server server;
    private final RMIClientConnection clientSession;
    private boolean connected = true;

    private ClientPinger clientPinger;

    /**
     * Constructor of the class RMIConnection that creates a new RMI connection
     * @param server the server that creates the RMI connection
     * @param clientSession the RMI connection between server and client (server side)
     */
    public RMIConnection(Server server, RMIClientConnection clientSession) {
        this.server = server;
        this.clientSession = clientSession;
    }

    /**
     * Method that returns if the connection is connected or not
     * @return true if the connection is connected, false if it is not
     */
    @Override
    public boolean isConnected() {
        return connected;
    }

    /**
     * Method that sends a message to the client
     * @param message the message that the server wants to send to the client
     * @throws RemoteException if there are problems with the connection
     */
    @Override
    public void sendMessageToClient(Message message) throws RemoteException {
        clientSession.receiveMessageFromServer(message);
    }

    /**
     * Method that pings the client to check if the connection is still alive or not through the RMI connection
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void ping() throws IOException {
        try {
            clientSession.ping();
        } catch (RemoteException e) {
            disconnect();
        }
    }

    /**
     * Method that disconnects the client from the server through the RMI connection so that the server can remove the player from the clientsConnected list
     * @throws IOException if there are problems with the connection
     */
    @Override
    public void disconnect() throws IOException {
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

    /**
     * Method that sets the client pinger of the RMI connection so that the server can ping the client to check if the connection is still alive or not
     * @param clientPinger the client pinger of the RMI connection
     */
    @Override
    public void setClientPinger(ClientPinger clientPinger){
        this.clientPinger = clientPinger;
    }

    /**
     * Method that returns the client pinger of the RMI connection
     * @return the client pinger of the RMI connection
     */
    @Override
    public ClientPinger getClientPinger(){
        return clientPinger;
    }

}
