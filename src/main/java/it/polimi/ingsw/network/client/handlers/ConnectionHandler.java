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
    public void handleMessage(Message mes) throws Exception {
        KeyConnectionPayload key= (KeyConnectionPayload) mes.getPayload().getKey();
        getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.CONTENT));
        if(key.equals(KeyConnectionPayload.RECONNECTION)){
            getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.CONTENT));
            getClientInterface().getClientView().somethingWrong();
        }
    }
}
