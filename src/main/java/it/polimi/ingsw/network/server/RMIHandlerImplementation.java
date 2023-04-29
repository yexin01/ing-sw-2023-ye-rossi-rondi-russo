package it.polimi.ingsw.network.server;

import it.polimi.ingsw.network.client.RMIClientConnection;
import it.polimi.ingsw.network.networkmessages.NetworkMessage;

import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * This class is the implementation of the interface RMIHandler
 */
public class RMIHandlerImplementation extends UnicastRemoteObject implements RMIHandler {
    @Serial
    private static final long serialVersionUID = 3776047796084380457L;

    private final transient Server server;
    private transient RMIConnection rmiSession;

    RMIHandlerImplementation(Server server) throws RemoteException {
        this.server = server;
    }


    @Override
    public void login(String username, RMIClientConnection client) {
        rmiSession = new RMIConnection(server, client);
        server.login(username, rmiSession);
    }

    /**
     * Sends a message to the server
     *
     * @param message message sent to server
     */
    @Override
    public void onMessage(NetworkMessage message) {
        server.onMessage(message);
    }

    @Override
    public void disconnectMe() {
        rmiSession.disconnect();
    }

/*
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RMIHandlerImplementation that = (RMIHandlerImplementation) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(rmiSession, that.rmiSession);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), server, rmiSession);
    }

 */
}