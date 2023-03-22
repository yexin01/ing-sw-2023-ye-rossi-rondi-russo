package it.polimi.ingsw.model;



import java.util.ArrayList;
import java.util.Random;
public class Board {
    private BoardBox[][] matrix;
    private ArrayList<ItemTile> tiles;
    private ArrayList<BoardBox> selectedBoard;

    public void fill(int [][] matrice){
        //importa da jason la matrice
        int dimensione = 9;   // o qualsiasi altra dimensione desiderata
        this.matrix = new BoardBox[dimensione][dimensione];
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                this.matrix[i][j] = new BoardBox(i,j);
                if(matrice[i][j]==1){
                    randomNumber = random.nextInt(tiles.size());
                    matrix[i][j].setTile(tiles.get(randomNumber));
                    tiles.remove(randomNumber);
                    matrix[i][j].setoccupiable(true);
                    matrix[i][j].setoccupied(true);
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getoccupied()){
                    setFreeEdges(i,j);

                }
            }
        }
    }


    public void initialize() {
        //IMPORTARE IL 22
        //IMPORTARE IL 22 DA un FILE JASON
        int j=0;
        tiles=new ArrayList<ItemTile>();
        for(Type t:Type.values()){
            for (int i = 0; i < 22; i++) {
                tiles.add(new ItemTile(t,j++));
            }
        }

    }

    public void setFreeEdges(int x, int y){
        if(x>0) {
            //sopra
            if (!matrix[x - 1][y].getoccupied() || !matrix[x - 1][y].getoccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
        if(x<matrix.length-1) {
            //sotto
            if (!matrix[x + 1][y].getoccupied()|| !matrix[x + 1][y].getoccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
        if(y>0) {
            //sinistra
            if (!matrix[x][y - 1].getoccupied()|| !matrix[x][y-1].getoccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
        if(y<matrix.length-1) {
            //destra
            if (!matrix[x][y + 1].getoccupied()|| !matrix[x][y+1].getoccupiable())
                matrix[x][y].increasefreeEdges();
        }else matrix[x][y].increasefreeEdges();
    }

    public void increaseNear(int x, int y){
        if(x>0) {
            //sopra
            if (matrix[x-1][y].getoccupied())
                matrix[x-1][y].increasefreeEdges();
        }
        if(x<matrix.length-1) {
            //sotto
            if (matrix[x+1][y].getoccupied())
                matrix[x+1][y].increasefreeEdges();
        }
        if(y>0) {
            //sinistra
            if (matrix[x][y-1].getoccupied())
                matrix[x][y-1].increasefreeEdges();
        }
        if(y< matrix.length-1) {
            //destra
            if (matrix[x][y+1].getoccupied())
                matrix[x][y+1].increasefreeEdges();
        }
    }
    private boolean Near(BoardBox boardbox,int nTessera) {
        if
        ((selectedBoard.get(nTessera - 1).getX() - boardbox.getX() == 1 ||
                selectedBoard.get(nTessera - 1).getX() - boardbox.getX() == -1 ||
                selectedBoard.get(nTessera - 1).getY() - boardbox.getY() == 1 ||
                selectedBoard.get(nTessera - 1).getY() - boardbox.getY() == -1)
                && (selectedBoard.get(nTessera - 1).getX() == boardbox.getX() ||
                selectedBoard.get(nTessera - 1).getY() == boardbox.getY())) {
            return true;
        }
        return false;
    }
    private boolean SamerowOrSamecolumn(BoardBox boardbox,int nTessera) {
        if(
                (selectedBoard.get(nTessera-1).getX()==boardbox.getX() &&
                        selectedBoard.get(nTessera-2).getX()==boardbox.getX()) ||
                        (selectedBoard.get(nTessera-1).getY()==boardbox.getY() &&
                                selectedBoard.get(nTessera-2).getY()==boardbox.getY())) {
            return true;
        }
        return false;
    }
    public boolean isSelectable(BoardBox boardbox,int nTessera){
        switch(nTessera) {
            case 0:
                if(boardbox.getEdges()>0){
                    selectedBoard= new ArrayList<BoardBox>();
                    selectedBoard.add(boardbox);
                    return true;
                }
                break;
            case 1:
                if(boardbox.getEdges()>0 && Near(boardbox,nTessera)){
                    selectedBoard.add(boardbox);
                    return true;
                }
                break;
            case 2:
                if(boardbox.getEdges()>0 && Near(boardbox,nTessera)&& SamerowOrSamecolumn(boardbox,nTessera)){
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
        ArrayList<ItemTile>  selectedItems= new ArrayList<ItemTile>();
        for(int i=0;i<selectedBoard.size();i++ ){
            selectedItems.add(selectedBoard.get(i).getItemtile());
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setoccupied(false);
            increaseNear(selectedBoard.get(i).getX(),selectedBoard.get(i).getY());
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setTile(null);
            matrix[selectedBoard.get(i).getX()][selectedBoard.get(i).getY()].setEdges(0);
        }
        for(int i=0;i<selectedItems.size();i++){
            System.out.println(selectedItems.get(i).getType());
        }
        return selectedItems;
    }


    public boolean checkRefill(){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getoccupied()){
                    if(matrix[i][j].getEdges()!=4)
                        return false;
                }

            }
        }
        return true;

    }
    public void refill(){
        //sposta le tessere isolate nel sacchetto
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getoccupied()){
                    tiles.add(matrix[i][j].getItemtile());
                    matrix[i][j].setItemtile(null);
                    matrix[i][j].setoccupied(false);
                }
            }
        }
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getoccupiable()){
                    randomNumber = random.nextInt(tiles.size());
                    matrix[i][j].setTile(tiles.get(randomNumber));
                    tiles.remove(randomNumber);
                    matrix[i][j].setoccupied(true);
                }
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if(matrix[i][j].getoccupied()){
                    matrix[i][j].setEdges(0);
                    setFreeEdges(i,j);
                }
            }
        }
    }

    public BoardBox getBoardBox(int x,int y){
        return matrix[x][y];
    }

}

