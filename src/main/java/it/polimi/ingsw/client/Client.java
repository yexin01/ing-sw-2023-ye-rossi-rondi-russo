package it.polimi.ingsw.client;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    /**
     * Notify the client of a model change
     * @param o     The resulting model view
     * @param arg   The causing event
     */
    void update(TurnView o, Turn.Event arg) throws RemoteException;
}
