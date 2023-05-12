package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

import java.rmi.RemoteException;

public class LobbyHandler extends MessageHandler {


    public LobbyHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(Message mes) throws Exception {
        KeyLobbyPayload key= (KeyLobbyPayload) mes.getPayload().getKey();
        System.out.println(mes.getPayload().getContent(Data.CONTENT));
        switch(key){
            case GLOBAL_LOBBY_DECISION -> {
                KeyLobbyPayload data = (KeyLobbyPayload) mes.getPayload().getKey();
                MessagePayload messagePayload=null;
                Message message=getClientInterface().askLobbyDecision();
                getClient().sendMessageToServer(message);
            }
            case CREATE_GAME_LOBBY-> {
                //System.out.println("NELLA LOBBY");
                System.out.println(mes.getPayload().getContent(Data.CONTENT));
            }

        }

    }
}


