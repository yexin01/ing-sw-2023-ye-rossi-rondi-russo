package it.polimi.ingsw.model.modelView;

public class BoardBoxView {
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

