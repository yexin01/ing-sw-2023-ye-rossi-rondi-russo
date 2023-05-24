package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

import java.io.Serial;
import java.io.Serializable;


public class BoardBoxView implements Serializable {
    private final boolean occupiable;
    private final ItemTileView itemTileView;
    private final int x;
    private final int y;
    private final int freeEdges;

    @Serial
    private static final long serialVersionUID = -3213024846906965897L;

    public BoardBoxView(boolean occupiable, ItemTileView itemTileView, int x, int y, int freeEdges) {
        this.occupiable = occupiable;
        this.itemTileView = itemTileView;
        this.x = x;
        this.y = y;
        this.freeEdges = freeEdges;
    }

    public boolean isOccupiable() {
        return occupiable;
    }

    public ItemTileView getItemTileView() {
        return itemTileView;
    }
    public Type getType() {
        return itemTileView.getTypeView();
    }
    public int getId() {
        return itemTileView.getTileID();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFreeEdges() {
        return freeEdges;
    }
}

