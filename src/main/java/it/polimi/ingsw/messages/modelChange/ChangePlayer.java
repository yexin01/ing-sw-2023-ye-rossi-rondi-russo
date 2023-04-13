package it.polimi.ingsw.messages.modelChange;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;

import java.io.Serializable;

public class ChangePlayer implements Serializable {
    private final String nickname;
    private final int points;

    private final Bookshelf bookshelf;



    public ChangePlayer(String nickname, int points, Bookshelf bookshelf) {
        this.nickname = nickname;
        this.points = points;
        this.bookshelf = bookshelf;
    }


    public ItemTile[][] getMatrix() {
        return bookshelf.getMatrix();
    }

    public int getMaxTilesColumn(int column) {
        return bookshelf.getMaxTilesColumn(column);
    }

    public String getNickname() {
        return nickname;
    }

    public int getPoints() {
        return points;
    }
}
