package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;

public class MessageToserverHandler  {
    private final ClientInterface clientInterface;
    private final Client client;

    public MessageToserverHandler(ClientInterface clientInterface, Client client) {
        this.clientInterface = clientInterface;
        this.client = client;
    }


    public void handleMessageToServer(Object newValue, TurnPhase turnPhase,String nickname) throws Exception {
        MessageHeader header=new MessageHeader(MessageType.DATA, nickname);
        MessagePayload payload=new MessagePayload(turnPhase);
        payload.put(Data.VALUE_CLIENT,newValue);
        Message message=new Message(header,payload);
        client.sendMessageToServer(message);

    }
}
