package it.polimi.ingsw.network.client;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.networkmessages.NetworkMessage;
import it.polimi.ingsw.network.server.RMIHandler;

import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Timer;

/**
 * This class represents the RMI Client implementation of the client
 */
public class ClientRMI extends Client implements RMIClientConnection {

    @Serial
    private static final long serialVersionUID = 1259827311643177219L;

    private transient RMIHandler server;

    /**
     * This constructor creates a ClientRMI
     * @param username is the username of the client
     * @param ip is the ip of the server
     * @param port is the port of the server
     * @param token is the token of the client
     * @throws RemoteException if there are connection problems
     */
    public ClientRMI (String username, String ip, int port, String token) throws RemoteException {
        super(username, ip, port, token);
    }

    /**
     * This method starts the connection with the server using RMI protocol and registers the client to the server
     * using the login method of the server interface RMIHandler and passing the username and the client itself
     * @throws RemoteException if there are connection problems
     * @throws NotBoundException if the server is not bound
     */
    @Override
    public void startConnection() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(getIp(), getPort());
        server = (RMIHandler) registry.lookup("MyShelfieServer");

        server.login(getUsername(),this);
    }

    /**
     * This method closes the connection with the server by disconnecting the client from the server and setting the server to null
     * @throws RemoteException if there are connection problems
     */
    @Override
    public void closeConnection() throws RemoteException {
        server.disconnectMe();
        server = null;
    }

    /**
     * This method sends a message to the server using the onMessage method of the server interface RMIHandler and passing the message itself
     * @param message is the message to send
     * @throws RemoteException if there are connection problems
     */
    @Override
    public void sendMessage(MessageFromClient message) throws RemoteException {
        if (server == null) {
            throw new RemoteException();
        }
        server.onMessage(message);
    }

    //TODO: this is receiveMessageFromServer
    /**
     * This method receives a message from the server and adds it to the message queue of the client
     * @param message is the message received from the server
     * @throws RemoteException if there are connection problems
     */
    @Override
    public void messageToClient(NetworkMessage message) throws RemoteException {
        synchronized (getMessageQueue()) {
            getMessageQueue().add(message);
        }
    }

    /**
     * This method pings the server to check if the connection is still active and if it is not it closes the connection with the server and sets the server to null
     * @throws RemoteException if there are connection problems
     */
    @Override
    public void ping() throws RemoteException {
        //TODO: to adjust
        super.pingTimer.cancel();
        super.pingTimer = new Timer();
        //super.pingTimer.schedule(new PingTimerTask(super.disconnectionListener), Client.DISCONNECTION_TIME);
    }

    /**
     * This method disconnects the client from the server and sets the server to null
     * @throws RemoteException if there are connection problems
     */
    @Override
    public void disconnectMe() throws RemoteException {
        server = null;
    }

    /* TODO: serve?
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientRMI clientRMI = (ClientRMI) o;
        return Objects.equals(server, clientRMI.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), server);
    }

     */

}
