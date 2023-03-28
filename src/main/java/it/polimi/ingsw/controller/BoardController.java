package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.Random;

public class BoardController {
    private Board board;

    public Board getBoard(){return board;}



    public BoardController(Board board) {
        this.board = board;
    }
    public void fillBag(int numPlayers){
        inizializedBoard();
        //IMPORTARLE DA JSON
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
    public void inizializedBoard() {
        //IMPORTARE IL 22
        //IMPORTARE IL 22 DA un FILE JASON
        int j=0;
        board.setTiles(new ArrayList<ItemTile>());
        for(Type t: Type.values()){
            for (int i = 0; i < 22; i++) {
                board.getTiles().add(new ItemTile(t,j++));
            }
        }

    }
    public void firstFill(int [][] matrix){
        //importa da jason la matrix
        int dimensione = 9;   // o qualsiasi altra dimensione desiderata
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
    private boolean near(BoardBox boardbox,int numTile) {
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
    private boolean sameRowOrSameColumn(BoardBox boardbox, int numTile) {
        if(
                (board.getSelectedBoard().get(numTile-1).getX()==boardbox.getX() &&
                        board.getSelectedBoard().get(numTile-2).getX()==boardbox.getX()) ||
                        (board.getSelectedBoard().get(numTile-1).getY()==boardbox.getY() &&
                                board.getSelectedBoard().get(numTile-2).getY()==boardbox.getY())) {
            return true;
        }
        return false;
    }
    public boolean isSelectable(BoardBox boardbox, int numTile){
        switch(numTile) {
            case 0:
                if(boardbox.getFreeEdges()>0){
                    board.setSelectedBoard(new ArrayList<BoardBox>());
                    board.getSelectedBoard().add(boardbox);
                    return true;
                }
                break;
            case 1:
                if(boardbox.getFreeEdges()>0 && near(boardbox,numTile)){
                    board.getSelectedBoard().add(boardbox);
                    return true;
                }
                break;
            case 2:
                if(boardbox.getFreeEdges()>0 && near(boardbox,numTile)&& sameRowOrSameColumn(boardbox,numTile)){
                    board.getSelectedBoard().add(boardbox);
                    return true;
                }
                break;
            default:

                break;
        }
        return false;
    }
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
        //sposta le tessere isolate nel sacchetto
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

