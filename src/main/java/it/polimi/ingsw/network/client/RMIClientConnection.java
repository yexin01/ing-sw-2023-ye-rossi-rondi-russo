package it.polimi.ingsw.network.client;

import it.polimi.ingsw.network.messages.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientConnection extends Remote {

    void sendMessage(Message message) throws RemoteException;

    void ping() throws RemoteException;

    void disconnect() throws RemoteException;

    //TODO suggerimenti sotto
    /*
    void reconnect() throws RemoteException;

    void reconnectFailed() throws RemoteException;

    void reconnectSucceeded() throws RemoteException;

    void reconnectTimeout() throws RemoteException;

    void reconnectRefused() throws RemoteException;

     */
}
