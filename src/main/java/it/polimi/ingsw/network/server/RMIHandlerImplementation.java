package it.polimi.ingsw.network.server;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.RMIClientConnection;

import java.io.IOException;
import java.io.Serial;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class RMIHandlerImplementation extends UnicastRemoteObject implements RMIHandler {

    @Serial
    private static final long serialVersionUID = 1401929399999030519L;

    private final transient Server server;
    private transient RMIConnection rmiSession;

    public RMIHandlerImplementation(Server server) throws RemoteException {
        this.server = server;
    }

    public void receiveMessageFromClient(Message message) throws IOException {
        server.receiveMessageFromClient(message);
    }

    @Override
    public void login(String nickname, RMIClientConnection client) throws Exception {
        rmiSession = new RMIConnection(server, client);
        System.out.println("chiamo login sul server quello vero...");
        server.loginToServer(nickname, rmiSession);
    }

    @Override
    public void disconnectMe() throws RemoteException{
        rmiSession.disconnect();
    }

}


