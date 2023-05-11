package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer2;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class StartAndEndGameHandler implements MessageHandler {

    @Override
    public void handleMessage(MessageFromServer2 mes, ClientInterface clientInterface, ClientSocket clientSocket) throws RemoteException {
        //TODO se evntTypeStart settare tutta la clientView con gli attributi del messaggio
        //TODO endGame stampare classifica... chiedere al giocatore se vuole partecipare ad un'altra partita
    }
}
