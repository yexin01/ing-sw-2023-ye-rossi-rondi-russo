package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.json.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardController {
    private Board board;

    public Board getBoard(){return board;}



    public BoardController(Board board) {
        this.board = board;
    }

    /** this method associates the matrix to be used
     * based on the number of players in the game
     *
     * @param numPlayers numbers of players in the game
     */
    public void firstFill(int numPlayers, GameRules gameRules) throws Exception {
        initializedBoard(gameRules);
        int[][] matrix = gameRules.getMatrix(numPlayers);
        //TODO FIRTSFILL
        board.setMatrix(new BoardBox[matrix.length][matrix[0].length]);
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                this.board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    randomNumber = random.nextInt(board.getTiles().size());
                    board.getMatrix()[i][j].setTile(board.getTiles().get(randomNumber));
                    board.getTiles().remove(randomNumber);
                    board.getMatrix()[i][j].setOccupiable(true);
                    board.getMatrix()[i][j].setOccupied(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    setFreeEdges(i,j);
                    // System.out.println(matrix[i][j].getEdges());
                }
            }
        }
    }
    /**
     * instantiate numTilesType for each tye of tile
     *import numTilesType from a file jason
     */
 /*   public void initializedBoard() {
        //TODO  import the number of tiles of each type from a file json
        int numTilesType=22;
        int j=0;
        board.setTiles(new ArrayList<ItemTile>());
        for(Type t: Type.values()){
            for (int i = 0; i < numTilesType; i++) {
                board.getTiles().add(new ItemTile(t,j++));
            }
        }

    }

  */
    public void initializedBoard(GameRules gameRules) throws Exception {
        int[] numTilesOfType = gameRules.getNumTilesPerType();
        int j = 0;
        board.setTiles(new ArrayList<ItemTile>());
        for (Type t : Type.values()) {
            for (int i = 0; i < numTilesOfType[t.ordinal()]; i++) {
                board.getTiles().add(new ItemTile(t, j++));
            }
        }
    }
    /**The matrix is scrolled twice:
     * the first timein correspondence with a 1 of the matrix passed as a parameter the two flags are set true
     *      and an Item tile is associated, randomly extracted from the arraylist containing the missing tiles of the game .
     * the second time it calculates the number of free edges of each BoardBox by inserting 0 int the unoccupiable cells
     *
     * @param matrix : it varies according to the number of players
     */


    /**
     * calculates the number of free edges of the cell having x and y coordinates of the Board
     *
     * @param x:board.matrix row
     * @param y: board.matrix column
     */
    public void setFreeEdges(int x, int y){
        if(x>0) {
            //sopra
            if (!board.getMatrix()[x - 1][y].isOccupied() || !board.getMatrix()[x - 1][y].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
        if(x< board.getMatrix().length-1) {
            //sotto
            if (!board.getMatrix()[x + 1][y].isOccupied()|| !board.getMatrix()[x + 1][y].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
        if(y>0) {
            //sinistra
            if (!board.getMatrix()[x][y - 1].isOccupied()|| !board.getMatrix()[x][y-1].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
        if(y< board.getMatrix().length-1) {
            //destra
            if (!board.getMatrix()[x][y + 1].isOccupied()|| !board.getMatrix()[x][y+1].isOccupiable())
                board.getMatrix()[x][y].increasefreeEdges();
        }else board.getMatrix()[x][y].increasefreeEdges();
    }

    /**
     * Updates the free edges of the adjacent cells after the tile
     * in row x and column y of the board is chosen by the user
     * @param x:board.matrix row
     * @param y: board.matrix column
     */

    public void increaseNear(int x, int y){
        if(x>0) {
            //sopra
            if (board.getMatrix()[x-1][y].isOccupied())
                board.getMatrix()[x-1][y].increasefreeEdges();
        }
        if(x< board.getMatrix().length-1) {
            //sotto
            if (board.getMatrix()[x+1][y].isOccupied())
                board.getMatrix()[x+1][y].increasefreeEdges();
        }
        if(y>0) {
            //sinistra
            if (board.getMatrix()[x][y-1].isOccupied())
                board.getMatrix()[x][y-1].increasefreeEdges();
        }
        if(y< board.getMatrix().length-1) {
            //destra
            if (board.getMatrix()[x][y+1].isOccupied())
                board.getMatrix()[x][y+1].increasefreeEdges();
        }
    }

    public boolean allAdjacent(){
        List<BoardBox> selectedBoard = board.getSelectedBoard();
        for (int i = 1; i < selectedBoard.size(); i++) {
            BoardBox currentTile = selectedBoard.get(i);
            BoardBox previousTile = selectedBoard.get(i - 1);
            if (Math.abs(currentTile.getX() - previousTile.getX()) != 1 && Math.abs(currentTile.getY() - previousTile.getY()) != 1) {
                return false;
            }
        }
        return true;
    }
    public boolean allSameRowOrSameColumn(){
        List<BoardBox> selectedBoard = board.getSelectedBoard();
        int firstX = selectedBoard.get(0).getX();
        int firstY = selectedBoard.get(0).getY();
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 1; i < selectedBoard.size(); i++) {
            if (selectedBoard.get(i).getX() != firstX) {
                allSameRow = false;
            }
            if (selectedBoard.get(i).getY() != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }
        return false;
    }

    /**
     * adjacent, in the same row or column and adjacent
     * @return
     */
    //TODO depends on how we implement the controller: this method checks the tile after each selection,
    // if we change the controller by checking the cards when the user has selected them all this method
    // must be changed by adding an arraylist to the board, checking at the end
    public boolean checkSelectable(BoardBox boardBox) throws Exception {
        GameRules gameRules = new GameRules();
        int numSelectableTiles = gameRules.getMaxSelectableTiles();
        List<BoardBox> selectedBoard = board.getSelectedBoard();
        if ((boardBox.getFreeEdges() <= 0) || (selectedBoard.size() > (numSelectableTiles+1))) {
            System.err.println("You chose more than "+numSelectableTiles+" tiles write -1 to reset the choice");
            return false;
        }

        board.getSelectedBoard().add(boardBox);
        if (selectedBoard.size() == 1) {
            return true;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedBoard.remove(selectedBoard.size() - 1);
            return false;
        }
        return true;
    }


    /**
     *
     * @return itemTiles of the arraylist selected Board
     */
    public ArrayList<ItemTile> selected(){
        ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
        for(int i = 0; i< board.getSelectedBoard().size(); i++ ){
            selectedItems.add(board.getSelectedBoard().get(i).getTile());
            board.getMatrix()[board.getSelectedBoard().get(i).getX()][board.getSelectedBoard().get(i).getY()].setOccupied(false);
            increaseNear(board.getSelectedBoard().get(i).getX(), board.getSelectedBoard().get(i).getY());
            board.getMatrix()[board.getSelectedBoard().get(i).getX()][board.getSelectedBoard().get(i).getY()].setTile(null);
            board.getMatrix()[board.getSelectedBoard().get(i).getX()][board.getSelectedBoard().get(i).getY()].setFreeEdges(0);
        }

        return selectedItems;
    }

    /**check that there are not at least two adjacent cells
     *
     * @return
     */

    public boolean checkRefill(){
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    if(board.getMatrix()[i][j].getFreeEdges()!=4)
                        return false;
                }

            }
        }
        return true;

    }


    public void refill(){
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    board.getTiles().add(board.getMatrix()[i][j].getTile());
                    board.getMatrix()[i][j].setTile(null);
                    board.getMatrix()[i][j].setOccupied(false);
                }
            }
        }
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupiable()){
                    randomNumber = random.nextInt(board.getTiles().size());
                    board.getMatrix()[i][j].setTile(board.getTiles().get(randomNumber));
                    board.getTiles().remove(randomNumber);
                    board.getMatrix()[i][j].setOccupied(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    board.getMatrix()[i][j].setFreeEdges(0);
                    setFreeEdges(i,j);
                }
            }
        }
    }


}
