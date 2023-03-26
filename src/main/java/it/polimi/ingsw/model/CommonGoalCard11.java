package it.polimi.ingsw.model;

public class CommonGoalCard11 extends CommonGoalCard{
    /*
      Cinque tessere dello stesso tipo che formano una diagonale.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf) {
        BookshelfBox[][] mat = bookshelf.getMatrix();
        boolean verified;
        // stampo matrice
        System.out.println("Matrice: ");
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[i].length; j++) {
                System.out.print(mat[i][j].getItemTile().getType() + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        for (int i=0; i<= mat.length-mat[i].length; i++) {
            verified=true;
            for (int a=1; a<mat[i].length && verified; a++) {
                if (!mat[i][0].getItemTile().getType().equals(mat[i+a][a].getItemTile().getType())) {
                    verified=false;
                }
            }
            if(verified){
                System.out.println("Trovate cinque tessere dello stesso tipo che formano una diagonale.");
                return true;
            }
        }
        System.out.println("Non trovate cinque tessere dello stesso tipo che formano una diagonale.");
        return false;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
