package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

import java.io.Serializable;

public class BoardView implements Serializable {
    private final BoardBoxView[][] boardView;

    public BoardView(BoardBoxView[][] boardView) {
        this.boardView = boardView;
    }


    public BoardBoxView[][] getMatrix() {
        return boardView;
    }
    public BoardBoxView getBoardBoxView(int x,int y) {
        return boardView[x][y];
    }

    public Type getTypeBoardBox(int x, int y) {
        return boardView[x][y].getItemTileView().getTypeView();
    }
    public int getIdBoardBox(int x, int y) {
        return boardView[x][y].getItemTileView().getTileID();
    }
}
