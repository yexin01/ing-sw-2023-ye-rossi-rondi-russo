package it.polimi.ingsw.model;

public class BookshelfBox {
    private ItemTile itemTile;
    private int x;
    private int y;
    /* It's all done within Bookshelf.findAdjacentTilesGroups
    private boolean occupied; --> null check is enough
    private boolean checkable;
    private boolean visited;

     */

    public BookshelfBox(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public ItemTile getItemTile() {
        return itemTile;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    /* It's all done within Bookshelf.findAdjacentTilesGroups
    public boolean isOccupied(){
        return occupied;
    }

    public boolean isCheckable() {
        return checkable;
    }

    public boolean isVisited(){
        return visited;
    }

     */
}
