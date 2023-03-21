package it.polimi.ingsw.model;
import java.util.ArrayList;

public class Board {
    private BoardBox[][] matrix;
    private ArrayList<BoardBox> selectedBoard;
    private ArrayList<ItemTile> selectedItems;
    private BoardBox[] selectable[];

    public Board(int [][] matrice){
        int dimensione = 9;   // o qualsiasi altra dimensione desiderata
        this.matrix = new BoardBox[dimensione][dimensione];
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                this.matrix[i][j] = new BoardBox(i,j);
                if(matrice[i][j]==1){
                    matrix[i][j].setoccupiable(true);
                    matrix[i][j].setoccupied(true);
                }
            }
        }
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                if(matrix[i][j].getoccupied()){
                    setFreeEdges(i,j);
                }
            }
        }
    }

    public void setFreeEdges(int x, int y){
        if(x>0) {
            //sopra
            if (matrix[x - 1][y].getoccupied())
                matrix[x - 1][y].increasefreeEdges();
        }
        if(x<matrix.length-1) {
            //sotto
            if (matrix[x + 1][y].getoccupied())
                matrix[x + 1][y].increasefreeEdges();
        }
        if(y>0) {
            //sinistra
            if (matrix[x][y - 1].getoccupied())
                matrix[x][y - 1].increasefreeEdges();
        }
        if(y< matrix.length-1) {
            //destra
            if (matrix[x][y + 1].getoccupied())
                matrix[x][y + 1].increasefreeEdges();
        }
    }

    public void decreaseFreeEdges(int x, int y){
        if(x>0) {
            //sopra
            if (matrix[x - 1][y].getoccupied())
                matrix[x - 1][y].decreasefreeEdges();
        }
        if(x<matrix.length-1) {
            //sotto
            if (matrix[x + 1][y].getoccupied())
                matrix[x + 1][y].decreasefreeEdges();
        }
        if(y>0) {
            //sinistra
            if (matrix[x][y - 1].getoccupied())
                matrix[x][y - 1].decreasefreeEdges();
        }
        if(y< matrix.length-1) {
            //destra
            if (matrix[x][y + 1].getoccupied())
                matrix[x][y + 1].decreasefreeEdges();
        }
    }

    public boolean isSelectable(BoardBox boardbox,int nTessera){

        switch(nTessera) {
            case 0:
                if(boardbox.getEdges()>0)
                    selectedBoard= new ArrayList<BoardBox>();
                selectedBoard.add(boardbox);
                return true;
            break;
            case 1:
                if(boardbox.getEdges()>0 && (selectedBoard.get(nTessera-1).getX()==boardbox.getX() ||
                        selectedBoard.get(nTessera-1).getY()==boardbox.getY()) {
                selectedBoard.add(boardbox);
                return true;
            }
            break;
            case 2:
                if(boardbox.getEdges()>0 &&
                        (selectedBoard.get(nTessera-1).getX()==boardbox.getX() &&
                                selectedBoard.get(nTessera-2).getX()==boardbox.getX()) ||
                        (selectedBoard.get(nTessera-1).getY()==boardbox.getY() &&
                                selectedBoard.get(nTessera-2).getY()==boardbox.getY())) {
                    selectedBoard.add(boardbox);
                    return true;
                }
                break;

            default:
                break;
        }

        return false;
    }



    public ArrayList<ItemTile> Selected(){
        selectedItems= new ArrayList<ItemTile>();
        for(int i=0;i<selectedBoard.size();i++ ){
            selectedItems.add(selectedBoard.get(i).getItemtile());
            setFreeEdges(selectedBoard.get(i).getX(),selectedBoard.get(i).getY());
            selectedBoard.get(i).setoccupied(false);
            selectedBoard.get(i).setItemtile(null);
        }
        return selectedItems;

        /*
        switch(nTessera) {
            case 0:
                selectedItems= new ArrayList<ItemTile>();
                selectedItems.add(boardbox.getItemtile());
                setFreeEdges(boardbox.getX(),boardbox.getY());
                boardbox.setoccupied(false);
                break;
            case :
                selectedItems.add(boardbox.getItemtile());
                setFreeEdges(boardbox.getX(),boardbox.getY());
                boardbox.setoccupied(false);
                break;
            case 2:
                selectedItems.add(boardbox.getItemtile());
                setFreeEdges(boardbox.getX(),boardbox.getY());
                boardbox.setoccupied(false);
            default:
                break;
        }
        return selectedPosition;
        */
    }


    public boolean checkFill(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getoccupied()){
                    if(matrix[i][j].getEdges()!=0)
                        return false;
                }

            }
        }
        return true;

    }







/*

    public BoardBox[][] getMatrix(){
        return matrix[][];
    }
    public BoardBox[] getselectedPosition(){
        return selectedPosition[];
    }

    public BoardBox[] getselectable(){
        return selectable[];
    }

*/
}

