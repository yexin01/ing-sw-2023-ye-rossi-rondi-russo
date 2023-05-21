package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

public class LobbyHandler extends MessageHandler {


    public LobbyHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        getClientInterface().askLobbyDecision();
    }
}


