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

    private ArrayList<BoardBox> selectedBoard=new ArrayList<>();

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
        try {
            if (playerChoiceX < -1 || playerChoiceX >= matrix.length) {
                throw new IllegalArgumentException(" value must be between 0 and " + (matrix.length - 1));
            }
            this.playerChoiceX = playerChoiceX;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid row:Rewrite the row" + e.getMessage());
            setFinishPlayeropposite();
        }
    }


    private Integer playerChoiceY;




    public Integer getPlayerChoiceY() {
        return playerChoiceY;
    }

    public void setPlayerChoiceY(Integer playerChoiceY) {
        try {
            if (playerChoiceY < -1 || playerChoiceY >= matrix.length) {
                throw new IllegalArgumentException(" value must be between 0 and " + (matrix[0].length - 1));

            }
            this.playerChoiceY = playerChoiceY;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid column:Rewrite the column" + e.getMessage());
            setFinishPlayeropposite();
        }
    }
    public Board() {
        playerChoiceX=-1;
        playerChoiceY=-1;
        finishPlayerChoice=-1;
    }


    public void setFinishPlayeropposite() {
        finishPlayer = !finishPlayer;
        setChanged();
        notifyObservers(finishPlayer);
    }

    private Integer finishPlayerChoice;

    public void printMatrix(){
        for (int i = 0; i < matrix.length; i++) {
            System.out.println(" ");
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getTile()!=null){
                    System.out.print("1 ");
                }
                else System.out.print("0 ");
            }
        }
    }



    private boolean endGame;

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }


    public Integer getFinishPlayerChoice() {
        return finishPlayerChoice;
    }

    public void setFinishPlayerChoice(Integer finishPlayerChoice) {
        this.finishPlayerChoice = finishPlayerChoice;
    }
}