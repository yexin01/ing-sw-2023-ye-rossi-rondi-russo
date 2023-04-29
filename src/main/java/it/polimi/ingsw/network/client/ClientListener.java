package it.polimi.ingsw.network.client;

import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;


public interface ClientListener extends EventListener {
    String getNickname();

    void askNickname();

    void printBoardBox(BoardBoxView[][] boardBoxView);
    void printTilesSelected(ItemTileView[] tilesSelected);

    void askCoordinates(BoardBoxView[][] board);
    void askOrder(ItemTileView[] tileSelected);
    void askColumn(ItemTileView[][] bookshelf);

    public void addListener(EventType eventType, EventListener listener);



    void shutdown();
}
