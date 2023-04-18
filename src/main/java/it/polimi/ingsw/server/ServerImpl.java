package it.polimi.ingsw.server;


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.controller.GameController;

import java.rmi.RemoteException;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.UnicastRemoteObject;

public class ServerImpl extends UnicastRemoteObject implements Server {

    private Turn model;
    private GameController controller;

    public ServerImpl() throws RemoteException {
        super();
    }

    public ServerImpl(int port) throws RemoteException {
        super(port);
    }

    public ServerImpl(int port, RMIClientSocketFactory csf, RMIServerSocketFactory ssf) throws RemoteException {
        super(port, csf, ssf);
    }

    @Override
    public void register(Client client) {
        this.model = new Turn();
        this.model.addObserver((o, arg) -> {
            try {
                client.update(new TurnView(model), arg);
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + e.getMessage() + ". Skipping the update...");
            }
        });

        this.controller = new Game(model, client);
    }

    @Override
    public void update(Client client, Choice arg) {
        this.controller.update(client, arg);
    }
}
