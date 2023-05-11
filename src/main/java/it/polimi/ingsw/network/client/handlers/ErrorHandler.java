package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

public class ErrorHandler extends MessageHandler {
    public ErrorHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(Message mes) throws Exception {
//TODO handler Error
    }
}
