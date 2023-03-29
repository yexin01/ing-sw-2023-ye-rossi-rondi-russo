package it.polimi.ingsw.model;


public class BoardBox {
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

    private boolean occupied;

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
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

    public BoardBox(int x,int y) {
        this.x=x;
        this.y=y;

    }


}
