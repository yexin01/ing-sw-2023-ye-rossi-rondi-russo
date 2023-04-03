package it.polimi.ingsw.model;



import it.polimi.ingsw.json.GameRules;

import java.util.ArrayList;
import java.util.Observable;

public class Board extends Observable {

    public Board() {
        playerChoiceX=-1;
        playerChoiceY=-1;
        finishPlayerChoice=-1;
        columnSelected=-1;
    }
    private BoardBox[][] matrix;

    public BoardBox[][] getMatrix() {return matrix;}
    public void setMatrix(BoardBox[][] matrix) {
        this.matrix = matrix;
        setChanged();
        notifyObservers(matrix);
    }

    public BoardBox getBoardBox(int x,int y) {
        return matrix[x][y];
    }

//PLAYER CHOICE
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
            if (playerChoiceY < -1 || playerChoiceY >= matrix[0].length) {
                throw new IllegalArgumentException(" value must be between 0 and " + (matrix[0].length - 1));

            }
            this.playerChoiceY = playerChoiceY;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid column:Rewrite the column" + e.getMessage());
            setFinishPlayeropposite();
        }
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

    private boolean finishPlayer;
//TODO it will be removed when the non-deprecated version is implemented
    public void setFinishPlayeropposite() {
        finishPlayer = !finishPlayer;
        setChanged();
        notifyObservers(finishPlayer);
    }
    public void printMatrix(){
        for (int i = 0; i < matrix.length; i++) {
            System.out.printf("row"+i+" ");
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j].getTile()!=null) {
                    System.out.printf("%-10s",+j+""+matrix[i][j].getTile().getType());
                } else {
                    System.out.printf("%-10s",+j+"EMPTY");
                }
            }
            System.out.println("");
        }
    }
    //BOOKSHELF
    private int columnSelected;
    public int getColumnSelected() {
        return columnSelected;
    }
    public void setColumnSelected(Integer columnSelected) {
        //TODO import num column bookshelf
        //TODO OR THIS WILL BE IN THE BOOKSHELF
        //TODO it depends on how the controller is implemented

        int maxColumBookshelf=4;
        try {
            if (columnSelected < -1 || columnSelected > 4) {
                throw new IllegalArgumentException(" value must be between 0 and  "+maxColumBookshelf);
            }
            this.columnSelected = columnSelected;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid bookshelf column:Rewrite the column" + e.getMessage());
            setFinishPlayeropposite();
        }
    }
    private boolean endGame;//true when a player has completely filled the bookshelf
                            //the game ends when the first player has to start the turn

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    private Integer finishPlayerChoice;
    //TODO it will be removed when the non-deprecated version is implemented
    public Integer getFinishPlayerChoice() {
        return finishPlayerChoice;
    }

    public void setFinishPlayerChoice(Integer finishPlayerChoice) {
        this.finishPlayerChoice = finishPlayerChoice;
    }
}

/*
    private int numOfTile;
    public int getnumOfTile() {
        return numOfTile;
    }

    public void setNumOfTile(Integer numOfTile) {
        GameRules boardAndGame;
        try {
            boardAndGame = new GameRules();
        } catch (Exception e) {
            System.err.println("Error initializing ReadBoardAndGame: " + e.getMessage());
            return;
        }

        int maxNumOfTile = boardAndGame.getMaxSelectableTiles();

        try {
            if (numOfTile < -1 ||  numOfTile > maxNumOfTile) {
                throw new IllegalArgumentException("value must be between 0 and " + maxNumOfTile);
            }
            this.numOfTile = numOfTile;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid numOf tile: " + e.getMessage());
            setFinishPlayeropposite();
        }
    }

 */