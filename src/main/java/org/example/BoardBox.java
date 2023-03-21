package org.example;

public class BoardBox {
    private int x;
    private int y;
    private ItemTile tile;
    private boolean occupiable;
    private boolean occupied;
    private int freeEdges;

    //COSTRUTTOREEEEE
    public BoardBox(int x,int y,ItemTile tile){

        this.x=x;
        this.y=y;

    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public void increasefreeEdges(){
        freeEdges++;
    }
    public void decreasefreeEdges(){
        freeEdges--;
    }

    public ItemTile getItemtile(){
        return tile;
    }

    public void setItemtile(ItemTile tile){
        this.tile=tile;
    }

    public boolean getoccupiable(){
        return occupiable;
    }

    public void setoccupiable(boolean valore){
        occupiable=valore;
    }
    public void setoccupied(boolean valore){
        occupied=valore;
    }


    public boolean getoccupied(){
        return occupied;
    }

    public int getEdges(){
        return freeEdges;
    }


}
