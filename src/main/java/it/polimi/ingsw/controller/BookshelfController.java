package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

public class BookshelfController {
    private Bookshelf bookshelf;

    public BookshelfController(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }



}
