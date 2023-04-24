package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * This class represents the RMI Server
 */
public class RMIServer {
    private final Server server;
    private final int port;

    /**
     * Constructs the server with main server and RMI port
     *
     * @param server instance of main server
     * @param port   port of RMI server
     */
    RMIServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Starts the RMI Server
     */
    void startServer() {
        try {
            RMIHandlerImplementation rmiHandler = new RMIHandlerImplementation(server);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("Server", rmiHandler);
        } catch (IOException | AlreadyBoundException e) {
            System.out.println("RMI server non started;");
        }
    }
}
