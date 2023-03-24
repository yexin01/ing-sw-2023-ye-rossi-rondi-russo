package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.Observable;

public class Board extends Observable {
    private BoardBox[][] matrix;

    private boolean finishPlayer;


    public BoardBox getBoardBox(int x,int y) {
        return matrix[x][y];
    }

    public BoardBox[][] getMatrix() {
        return matrix;
    }

    public void setMatrix(BoardBox[][] matrix) {
        this.matrix = matrix;
        setChanged();
        notifyObservers(matrix);
    }

    private ArrayList<ItemTile> tiles;

    public ArrayList<ItemTile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<ItemTile> tiles) {
        this.tiles = tiles;
        setChanged();
        notifyObservers(tiles);
    }

    private ArrayList<BoardBox> selectedBoard;

    public ArrayList<BoardBox> getSelectedBoard() {
        return selectedBoard;
    }

    public void setSelectedBoard(ArrayList<BoardBox> selectedBoard) {

        this.selectedBoard = selectedBoard;
        setChanged();
        notifyObservers(selectedBoard);
    }

    private BoardBox[][] selectable;

    private Integer playerChoiceX;

    public Integer getPlayerChoiceX() {
        return playerChoiceX;
    }

    public void setPlayerChoiceX(Integer playerChoiceX) {
        this.playerChoiceX = playerChoiceX;
    }


    private Integer playerChoiceY;




    public Integer getPlayerChoiceY() {
        return playerChoiceY;
    }

    public void setPlayerChoiceY(Integer playerChoiceY) {
        this.playerChoiceY = playerChoiceY;
    }



    public Board() {
        playerChoicenumTile=-1;
        playerChoiceX=-1;
        playerChoiceY=-1;
    }


    public boolean isFinishPlayer() {
        return finishPlayer;
    }

    public void setFinishPlayer(boolean finishPlayer) {
        this.finishPlayer = finishPlayer;
        setChanged();
        notifyObservers(finishPlayer);
    }
    public void setFinishPlayeropposite() {
        finishPlayer = finishPlayer;
        setChanged();
        notifyObservers(finishPlayer);
    }


    private Integer playerChoicenumTile;
    public Integer getPlayerChoicenumTile() {
        return playerChoicenumTile;
    }

    public void setPlayerChoicenumTile(Integer playerChoicenumTile) {
        this.playerChoicenumTile = playerChoicenumTile;
    }
}
