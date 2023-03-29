package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

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

    public void fillBag(int numPlayers){
        inizializedBoard();
        //TODO import these matrices from a file jason

        int matrice2gioc[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int matrice3gioc[][] ={
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0}
        };
        int matrice4gioc[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 1},
        };

        switch (numPlayers) {
            case 2:
                firstFill(matrice2gioc);
                break;
            case 3:
                firstFill(matrice3gioc);
                break;
            case 4:
                firstFill(matrice4gioc);
                break;
            default:
                firstFill(matrice2gioc);
                break;
        }


    }

    /**
     * instantiate numTilesType for each tye of tile
     *import numTilesType from a file jason
     */
    public void inizializedBoard() {
        //TODO  import the number of tiles of each type from a file jason
        int numTilesType=22;
        int j=0;
        board.setTiles(new ArrayList<ItemTile>());
        for(Type t: Type.values()){
            for (int i = 0; i < numTilesType; i++) {
                board.getTiles().add(new ItemTile(t,j++));
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
    public void firstFill(int [][] matrix){
        //TODO import the dimension of the board from a file jason
        int dimensione = 9;
        board.setMatrix(new BoardBox[dimensione][dimensione]);
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

    /**check that the BoardBox present in position numTile-1 and the BoardBox passed by parameter are adjacent
     *
     * @param boardbox
     * @param numTile number of BoardBox selected starts from 0
     * @return
     */
    public boolean near(BoardBox boardbox,int numTile) {

            if
        ((board.getSelectedBoard().get(numTile - 1).getX() - boardbox.getX() == 1 ||
                board.getSelectedBoard().get(numTile - 1).getX() - boardbox.getX() == -1 ||
                board.getSelectedBoard().get(numTile - 1).getY() - boardbox.getY() == 1 ||
                board.getSelectedBoard().get(numTile - 1).getY() - boardbox.getY() == -1)
                && (board.getSelectedBoard().get(numTile - 1).getX() == boardbox.getX() ||
                board.getSelectedBoard().get(numTile - 1).getY() == boardbox.getY())) {
            return true;
        }
        return false;
    }

    /**
     * adjacent, in the same row or column and adjacent
     * @return
     */
    public boolean checkSelection() {
        List<BoardBox> selectedBoard = board.getSelectedBoard();
        //at least one free edge
        for (int i = 0; i < selectedBoard.size(); i++) {
            if(selectedBoard.get(i).getFreeEdges()<=0){
                return false;
        }

        if (selectedBoard.size() <= 1) {
            return true;
        }
        //on the same row or column
        boolean allTilesInSameRow = true;
        boolean allTilesInSameColumn = true;
        int firstX = selectedBoard.get(0).getX();
        int firstY = selectedBoard.get(0).getY();
        for (int i = 1; i < selectedBoard.size(); i++) {
            if (selectedBoard.get(i).getX() != firstX) {
                allTilesInSameColumn = false;
            }
            if (selectedBoard.get(i).getY() != firstY) {
                allTilesInSameRow = false;
            }
        }
        if (allTilesInSameRow && allTilesInSameColumn) {
            return false;
        }
        //adjacent
        for (int i = 1; i < selectedBoard.size(); i++) {
            BoardBox currentTile = selectedBoard.get(i);
            BoardBox previousTile = selectedBoard.get(i - 1);
            if (currentTile.getX() == previousTile.getX() && Math.abs(currentTile.getY() - previousTile.getY()) != 1) {
                return false;
            } else if (currentTile.getY() == previousTile.getY() && Math.abs(currentTile.getX() - previousTile.getX()) != 1) {
                return false;
            } else if (currentTile.getX() != previousTile.getX() && currentTile.getY() != previousTile.getY()) {
                return false;
            }
        }
        return true;
    }
}

    /**adds the boardBox to the Arraylist
     *
     * @param boardbox
     * @return
     */

    public boolean addTile(BoardBox boardbox) {
        board.getSelectedBoard().add(boardbox);
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

    /**
     *
     */
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
