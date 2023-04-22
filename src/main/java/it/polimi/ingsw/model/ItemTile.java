package it.polimi.ingsw.model;

import java.util.Observable;

public class ItemTile{
    private Type type;

    public Type getType() {
        return type;
    }

    public ItemTile(Type type,int tileID) {
        this.type = type;
        this.tileID = tileID;
    }

    private int tileID;

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }


}