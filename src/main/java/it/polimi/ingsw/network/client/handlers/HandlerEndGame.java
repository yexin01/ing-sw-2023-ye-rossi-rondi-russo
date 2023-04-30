package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;

public class HandlerEndGame extends MessageHandler{
    public HandlerEndGame(ClientSocket connection, ClientInterface clientInterface) {
        super(connection, clientInterface);
    }

    @Override
    public void handleMessage(MessageFromServer mes) {
        //TODO cambiare panel della clientInterface e stampare classifica
    }
}
