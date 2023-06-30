package it.polimi.ingsw.network.server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * RMI server that creates a new RMI connection between server and client (server side)
 */
public class RMIServer {

    private final Server server;
    private final int port;

    /**
     * Constructor of the class RMIServer that creates a new RMI server that creates a new RMI connection between server and client (server side)
     * @param server the server that creates the RMI server
     * @param port the port of the RMI server
     */
    public RMIServer(Server server, int port) {
        this.server = server;
        this.port = port;
    }

    /**
     * Method that starts the RMI server when called by Server class and binds the RMI handler to the registry
     */
    public void startServer() {
        String ipAddress = server.getIpAddress();
        try {
            System.setProperty("java.rmi.server.hostname", ipAddress);

            RMIHandlerImplementation rmiHandler = new RMIHandlerImplementation(server);
            Registry registry = LocateRegistry.createRegistry(port);
            registry.bind("MyShelfieServer", rmiHandler);
        } catch (IOException | AlreadyBoundException e ) {
            System.out.println("already bound!!");
        }
    }

}