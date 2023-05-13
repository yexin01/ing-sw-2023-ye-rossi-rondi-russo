package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

public class MessageToserverHandlerTurn {
    private final ClientInterface clientInterface;
    private final Client client;

    public MessageToserverHandlerTurn(ClientInterface clientInterface, Client client) {
        this.clientInterface = clientInterface;
        this.client = client;
    }


    public void handleMessageToServer(Object newValue, KeyAbstractPayload turnPhase, String nickname,MessageType messageType) throws Exception {
        MessageHeader header=new MessageHeader(messageType, nickname);
        MessagePayload payload=new MessagePayload(turnPhase);
        payload.put(Data.VALUE_CLIENT,newValue);
        Message message=new Message(header,payload);
        client.sendMessageToServer(message);
    }
}
