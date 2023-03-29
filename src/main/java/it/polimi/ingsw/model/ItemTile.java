package it.polimi.ingsw.model;

import java.util.Observable;

public class ItemTile extends Observable {
    private Type type;

    public Type getType() {
        return type;
    }

    public ItemTile(Type type,int tileID) {
        this.type = type;
        this.tileID = tileID;
        setChanged();
        notifyObservers(type);
    }

    private int tileID;

    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
        setChanged();
        notifyObservers(tileID);
    }


}
