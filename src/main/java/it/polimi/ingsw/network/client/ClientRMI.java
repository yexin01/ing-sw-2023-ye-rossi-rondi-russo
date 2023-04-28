package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.networkmessages.Message;
import it.polimi.ingsw.network.server.RMIHandler;

import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI extends Client implements RMIClientConnection {

    @Serial
    private static final long serialVersionUID = 1259827311643177219L;

    private transient RMIHandler server;

    public ClientRMI (String username, String ip, int port) throws RemoteException {
        super(username, ip, port);
    }

    @Override
    public void startConnection() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(getIp(), getPort());
        server = (RMIHandler) registry.lookup("server");

        server.login(getUsername(),this);
    }

    @Override
    public void closeConnection() throws RemoteException {
        server.disconnectMe();
        server = null;
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        if (server == null) {
            throw new RemoteException();
        }
        server.onMessage(message);
    }

    @Override
    public void messageToClient(Message message) throws RemoteException {
        synchronized (getMessageQueue()) {
            getMessageQueue().add(message);
        }
    }

    @Override
    public void ping() throws RemoteException {
        //TODO
        super.pingTimer.cancel();
        super.pingTimer = new Timer();
        super.pingTimer.schedule(new PingTimerTask(super.disconnectionListener), Client.DISCONNECTION_TIME);
    }

    @Override
    public void disconnectMe() throws RemoteException {
        server = null;
    }

}
