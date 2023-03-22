package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Type;

public class ItemTile {
    private Type type;
    private int value;


    //COSTRUTTORE
    public ItemTile(Type type,int i){
        this.type=type;
        value=i;
    }

    public Type getType(){
        return type;
    }

    public int getvalue(){
        return value;
    }


}
