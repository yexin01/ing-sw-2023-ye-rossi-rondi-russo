package it.polimi.ingsw.client;

import it.polimi.ingsw.server.AppServer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Hello world!
 *
 */
public class AppClientRMI
{
    public static void main( String[] args ) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        AppServer server = (AppServer) registry.lookup("server");

        ClientImpl client = new ClientImpl(server.connect());
        client.run();
    }
}
