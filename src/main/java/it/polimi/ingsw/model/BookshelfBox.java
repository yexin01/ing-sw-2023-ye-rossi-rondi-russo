package it.polimi.ingsw.model;

public class BookshelfBox {
    private ItemTile itemTile;
    private boolean occupied;
    private boolean checkable;
    private boolean visited;

    public ItemTile getItemTile() {
        return itemTile;
    }
    public boolean isOccupied(){
        return occupied;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public boolean isVisited(){
        return visited;
    }
}
