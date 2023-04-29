package it.polimi.ingsw.network.client.messageHandlers;

import it.polimi.ingsw.network.client.ClientListener;
import it.polimi.ingsw.network.client.ClientSocket;
import it.polimi.ingsw.view.ClientView;

public class SetupHandler extends MessageHandler {


    protected SetupHandler(ClientSocket connection, ClientListener clientListener, ClientView clientView) {
        super(connection, clientListener, clientView);
    }

    @Override
    void handleMessage(MessageFromServer message) {
        //TODO gestisce la creazione degli handler e setta board,points,bookshelf... da MesageFromServer allInfo
    }
}
