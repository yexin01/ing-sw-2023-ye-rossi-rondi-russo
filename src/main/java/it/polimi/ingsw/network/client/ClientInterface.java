package it.polimi.ingsw.network.client;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.view.ClientView;

//TODO will change

public interface ClientInterface {
    String getNickname();

    void askNickname();



    int[] askCoordinates();
    int[] askOrder();
    int[] askColumn();


    public ClientView getCurrentView();


    void shutdown();
}
