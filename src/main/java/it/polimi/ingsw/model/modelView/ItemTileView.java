package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

public class ItemTileView {

    private Type type;

    public Type getType() {
        return type;
    }

    public ItemTileView(Type type,int tileID) {
        this.type = type;
        this.tileID = tileID;
    }

    private int tileID;

    public int getTileID() {
        return tileID;
    }
}
