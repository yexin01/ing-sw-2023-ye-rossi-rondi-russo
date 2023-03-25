package it.polimi.ingsw.model;

public class Bookshelf {
    private BookshelfBox[][] matrix;
    private int[] freeShelves; //freeShelves[i] = # celle libere nell'i-esima colonna

   /*
    public Bookshelf (int x, int y){ //dimension may vary in future game versions, taken from Json
        for (int j=0; j<y; j++){
            for (int i=0; i<x; i++){
                this.matrix[i][j] = null;
                freeShelves[j]=0;
            }
        }
    }
    */

    public BookshelfBox[][] getMatrix() {
        return matrix;
    }

    public int[] getFreeShelves() {
        return freeShelves;
    }

    public void computeFreeShelves(){
        for (int j=0; j<matrix[0].length; j++){
            for (int i=0; i<matrix.length && matrix[i][j]==null; i++){
                freeShelves[j]++;
            }
        }
    }

    public int maxFreeShelves(){
        int max = 0;
        for (int i=0; i<freeShelves.length; i++){
            if (freeShelves[i]>max){
                max = freeShelves[i];
            }
        }
        return max;
    }

    public boolean isFull (){
        return true;
    }

    public int computeAdjacent(){

        return 1;
    }

    public void resetCheckable(){

    }



}
