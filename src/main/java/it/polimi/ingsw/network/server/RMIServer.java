package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIServer {

    private final Server server;
    private final int port;

    public RMIServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    public void startServer() {
        try {
            RMIHandlerImplementation rmiHandler = new RMIHandlerImplementation(server);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("MyShelfieServer", rmiHandler);
        } catch (IOException e ) {
            System.out.println("already bound!!");
        } catch (AlreadyBoundException e) {
            throw new RuntimeException(e);
        }
    }

}
