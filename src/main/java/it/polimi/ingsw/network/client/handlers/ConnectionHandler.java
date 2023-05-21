package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.Data;
import it.polimi.ingsw.message.KeyConnectionPayload;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

public class ConnectionHandler extends MessageHandler{
    public ConnectionHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        KeyConnectionPayload key= (KeyConnectionPayload) mes.getPayload().getKey();
        getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.CONTENT));
        if(key.equals(KeyConnectionPayload.RECONNECTION)){
            if(getClient().isOnlyOnePlayer() || mes.getPayload().getContent(Data.WHO_CHANGE).equals(getClientInterface().getClientView().getNickname())){
                getClient().setOnlyOnePlayer(false);
                getClientInterface().getClientView().somethingWrong();
            }
        }
    }
}
