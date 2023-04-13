package it.polimi.ingsw.messages.modelChange;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Type;

import java.io.Serializable;


public class ChangeBoard implements Serializable {
    private final Board board;

    public ChangeBoard(Board board) {
        this.board = board;
    }


    public BoardBox[][] getMatrix() {
        return board.getMatrix();
    }

    public Type getTypeBoardBox(int x,int y) {
        return board.getBoardBox(x,y).getTile().getType();
    }

}
