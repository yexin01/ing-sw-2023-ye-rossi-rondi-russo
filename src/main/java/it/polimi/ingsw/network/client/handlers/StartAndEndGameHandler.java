package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.message.MessageFromServer;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class StartAndEndGameHandler extends MessageHandler {


    protected StartAndEndGameHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(MessageFromServer mes) throws RemoteException {
        //TODO se evntTypeStart settare tutta la clientView con gli attributi del messaggio
        //TODO endGame stampare classifica... chiedere al giocatore se vuole partecipare ad un'altra partita
    }
}
