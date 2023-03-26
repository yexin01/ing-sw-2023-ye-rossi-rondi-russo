package it.polimi.ingsw.model;

public class CommonGoalCard9 extends CommonGoalCard{
    /*
      Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere.
    */

    @Override
    boolean checkGoal(Bookshelf bookshelf){
        int dimType=6;
        int [] seen = new int [dimType]; // matrice di contatori per tipo
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
        //inizializzo a zeri la matrice dei contatori
        for(int a=0; a<dimType; a++){
            seen[a]=0;
        }
        for(int i=0; i<mat.length; i++){
            for(int j=0; j<mat[i].length; j++){
                switch (mat[i][j].getItemTile().getType()) {
                    case CAT -> seen[0]++;
                    case BOOK -> seen[1]++;
                    case GAME -> seen[2]++;
                    case FRAME -> seen[3]++;
                    case TROPHY -> seen[4]++;
                    case PLANT -> seen[5]++;
                    default -> System.out.println("type_tile not valid!");
                }
                for(int a=0; a<dimType; a++){
                    if(seen[a]>=8){
                        System.out.println("trovate almeno otto tessere dello stesso tipo.");
                        return true;
                    }
                }
            }
        }
        System.out.println("non sono state trovate almeno otto tessere dello stesso tipo.");
        return false;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
