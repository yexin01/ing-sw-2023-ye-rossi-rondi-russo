package it.polimi.ingsw.model;

import java.util.Observable;

public class ItemTile extends Observable {
    private Type type;

    public Type getType() {
        return type;
    }

    public ItemTile(Type type,int value) {
        this.type = type;
        this.value=value;
        setChanged();
        notifyObservers(type);
    }

    private int value;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        setChanged();
        notifyObservers(value);
    }


}