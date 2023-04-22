package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.model.Type;

public class MatrixView {
    private final BoardBoxView[][] boardView;

    public MatrixView(BoardBoxView[][] boardView) {
        this.boardView = boardView;
    }


    public BoardBoxView[][] getMatrix() {
        return boardView;
    }
    public BoardBoxView getBoardBoxView(int x,int y) {
        return boardView[x][y];
    }

    public Type getTypeBoardBox(int x, int y) {
        return boardView[x][y].getItemTileView().getType();
    }
    public int getIdBoardBox(int x, int y) {
        return boardView[x][y].getItemTileView().getTileID();
    }
}
