package it.polimi.ingsw.model;

public class CommonGoalCard8 extends CommonGoalCard{
    /*
      Quattro tessere dello stesso tipo ai quattro angoli della Libreria.
    */

    @Override
    boolean checkGoal(Bookshelf bookshelf){
        BookshelfBox[][] mat = bookshelf.getMatrix();
        // stampo matrice
        System.out.println("Matrice: ");
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat[i].length; j++){
                System.out.print(mat[i][j].getItemTile().getType() + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        if(mat[0][0].getItemTile().getType().equals(mat[mat.length-1][0].getItemTile().getType())
                && mat[0][0].getItemTile().getType().equals(mat[mat.length-1][mat[0].length-1].getItemTile().getType())
                && mat[0][0].getItemTile().getType().equals(mat[0][mat[0].length-1].getItemTile().getType())){
            System.out.println("quattro angoli uguali.");
            return true;
        }
        System.out.println("quattro angoli non uguali.");
        return false;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
