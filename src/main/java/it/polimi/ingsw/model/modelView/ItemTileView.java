package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Type;

import java.io.Serial;
import java.io.Serializable;

/**
 * Serializable immutable class represents the single tile.
 */

public class ItemTileView implements Serializable {

    private Type type;

    @Serial
    private static final long serialVersionUID = -1354272688526436838L;

    /**
     * Constructs an ItemTileView.
     * @param type The type of the item tile.
     * @param tileID The ID of the item tile.
     */
    public ItemTileView(Type type, int tileID) {
        this.type = type;
        this.tileID = tileID;
    }

    private int tileID;

    /**
     * Gets the type of the item tile.
     * @return The type of the item tile.
     */
    public Type getTypeView() {
        return type;
    }

    /**
     * Gets the ID of the item tile.
     * @return The ID of the item tile.
     */
    public int getTileID() {
        return tileID;
    }

    public ItemTile restoreItemTile(){
        ItemTile itemTile = new ItemTile(this.type, this.tileID);
        return itemTile;
    }
}
