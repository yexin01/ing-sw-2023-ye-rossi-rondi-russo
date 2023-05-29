package it.polimi.ingsw.model;

/**
 *Class that represents the single tile of the game
 */
public class ItemTile{
    private Type type;
    private int tileID;

    /**
     * Constructor of the ItemTile with the type and id;
     * @param type
     * @param tileID
     */
    public ItemTile(Type type,int tileID) {
        this.type = type;
        this.tileID = tileID;
    }
    public Type getType() {
        return type;
    }



    public int getTileID() {
        return tileID;
    }

    public void setTileID(int tileID) {
        this.tileID = tileID;
    }


}