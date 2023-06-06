package it.polimi.ingsw.model;

/**
 *Class that represents the single tile of the game
 */
public class ItemTile{
    private Type type;
    private int tileID;

    /**
     * Constructs an ItemTile object with the specified type and tile ID.
     * @param type The type of the tile.
     * @param tileID The ID of the tile.
     */
    public ItemTile(Type type,int tileID) {
        this.type = type;
        this.tileID = tileID;
    }
    /**
     * Returns the type of the tile.
     * @return The type of the tile.
     */
    public Type getType() {
        return type;
    }

    /**
     * Returns the ID of the tile.
     * @return The tile ID.
     */

    public int getTileID() {
        return tileID;
    }
    /**
     * Sets the ID of the tile.
     * @param tileID The tile ID to be set.
     */
    public void setTileID(int tileID) {
        this.tileID = tileID;
    }


}