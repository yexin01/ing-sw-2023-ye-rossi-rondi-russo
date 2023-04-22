package it.polimi.ingsw.model.modelView;

import java.io.Serializable;

public class BoardBoxView implements Serializable {
    private final boolean occupiable;
    private final ItemTileView itemTileView;

    public BoardBoxView(boolean occupiable, ItemTileView itemTileView) {
        this.occupiable = occupiable;
        this.itemTileView = itemTileView;
    }

    public boolean isOccupiable() {
        return occupiable;
    }

    public ItemTileView getItemTileView() {
        return itemTileView;
    }
}

