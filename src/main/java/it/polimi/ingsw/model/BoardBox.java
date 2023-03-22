package it.polimi.ingsw.model;


public class BoardBox {
    private int x;
    private int y;
    private ItemTile tile;
    private boolean occupiable;
    private boolean occupied;
    private int freeEdges;

    //COSTRUTTOREEEEE
    public BoardBox(int x,int y){
        this.x=x;
        this.y=y;

    }
    public void setTile(ItemTile tile){
        this.tile=tile;
    }
    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
    public void setEdges(int edge){
        freeEdges=edge;
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

    public boolean getoccupied(){
        return occupied;
    }

    public void setoccupiable(boolean valore){
        occupiable=valore;
    }
    public void setoccupied(boolean valore){
        occupied=valore;
    }



    public int getEdges(){
        return freeEdges;
    }


}
