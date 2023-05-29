package it.polimi.ingsw.model;

/**
 *class representing the cell of the Board
 */

public class BoardBox {
    /**
     * BoardBox constructor.
     * @param x: board matrix row;
     * @param y: board matrix column;
     */
    public BoardBox(int x,int y) {
        this.x=x;
        this.y=y;

    }
    private int x;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private int y;

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    private ItemTile tile;

    public ItemTile getTile() {
        return tile;
    }

    public void setTile(ItemTile tile) {
        this.tile = tile;
    }

    private boolean occupiable;

    public boolean isOccupiable() {
        return occupiable;
    }

    public void setOccupiable(boolean occupiable) {
        this.occupiable = occupiable;
    }


    private int freeEdges;

    public int getFreeEdges() {
        return freeEdges;
    }

    public void setFreeEdges(int freeEdges) {
        this.freeEdges = freeEdges;
    }

    public void increasefreeEdges(){
        freeEdges++;
    }



}
