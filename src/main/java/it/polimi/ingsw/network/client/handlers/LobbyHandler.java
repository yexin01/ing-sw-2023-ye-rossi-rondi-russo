package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class LobbyHandler extends MessageHandler {


    public LobbyHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(Message mes) throws RemoteException {

    }

    /*
messaggio message struttura messaggi lobby

        createGame lobbi + numberOfPlayer valueClient


        joinspecifi game lobby  value client idgame


        joinedRandomGame

     */


}


