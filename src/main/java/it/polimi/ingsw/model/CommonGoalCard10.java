package it.polimi.ingsw.model;

public class CommonGoalCard10 extends CommonGoalCard{
    /*
      Cinque tessere dello stesso tipo che formano una X.
    */

    @Override
    boolean checkGoal(Bookshelf bookshelf) {
        BookshelfBox[][] mat = bookshelf.getMatrix();
        // stampo matrice
        System.out.println("Matrice: ");
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[i].length; j++) {
                System.out.print(mat[i][j].getItemTile().getType() + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        for (int i=1; i<mat.length-1; i++) {
            for (int j=1; j<mat[i].length-1; j++) {
                if ((mat[i][j].getItemTile().getType().equals(mat[i-1][j-1].getItemTile().getType()))
                        && (mat[i][j].getItemTile().getType().equals(mat[i-1][j+1].getItemTile().getType()))
                        && (mat[i][j].getItemTile().getType().equals(mat[i+1][j-1].getItemTile().getType()))
                        && (mat[i][j].getItemTile().getType().equals(mat[i+1][j+1].getItemTile().getType()))
                ) {
                    System.out.println("Trovate cinque tessere dello stesso tipo che formano una X.");
                    return true;
                }
            }
        }
        System.out.println("Non trovate cinque tessere dello stesso tipo che formano una X.");
        return false;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
