package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

import java.io.Serial;
import java.io.Serializable;

public class ItemTileView implements Serializable {

    private Type type;

    @Serial
    private static final long serialVersionUID = -1354272688526436838L;

    public Type getTypeView() {
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
