package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

import java.io.Serializable;

public class BookshelfView implements Serializable {
    private final ItemTileView[][] bookshelfView;

    public BookshelfView(ItemTileView[][] bookshelfView) {
        this.bookshelfView = bookshelfView;
    }

    public ItemTileView[][] getBookshelfView(){return bookshelfView;}
    public Type getTypeBookshelf(int x, int y) {
        return bookshelfView[x][y].getType();
    }
    public int getIdBookshelf(int x, int y) {
        return bookshelfView[x][y].getTileID();
    }
}
