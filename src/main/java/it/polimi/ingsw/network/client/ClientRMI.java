package it.polimi.ingsw.network.client;

import it.polimi.ingsw.client.ClientImpl;
import it.polimi.ingsw.network.messages.Message;
import it.polimi.ingsw.network.server.RMIHandler;
import it.polimi.ingsw.server.AppServer;

import java.io.Serial;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientRMI extends Client implements RMIClientConnection {

    @Serial
    private static final long serialVersionUID = 8860650382225140804L;

    private transient RMIHandler server;

    public ClientRMI (String username, String password, String ip, int port) throws RemoteException {
        super(username, password, ip, port);
    }

    @Override
    public void startConnection() throws RemoteException, NotBoundException {

        // TODO to adapt
        /*
        Registry registry = LocateRegistry.getRegistry();
        AppServer server = (AppServer) registry.lookup("server");

        ClientImpl client = new ClientImpl(server.connect());
        client.run();

         */

        Registry registry = LocateRegistry.getRegistry(getIp(), getPort());
        server = (RMIHandler) registry.lookup("server");

        server.login(getUsername(),this);
    }

    @Override
    public void closeConnection() throws RemoteException {
        server.disconnect();
        server = null;
    }

    @Override
    public void sendMessage(Message message) throws RemoteException {
        synchronized (messageQueue) {
            messageQueue.add(message);
        }
    }

    @Override
    public void ping() throws RemoteException {
        //TODO
    }

    @Override
    public void disconnect() throws RemoteException {
        server = null;
    }

}
