package it.polimi.ingsw.model;

public class CommonGoalCard2 extends CommonGoalCard{
    /*
      Due colonne formate ciascuna da 6 diversi tipi di tessere.
    */

    @Override
    boolean checkGoal(Bookshelf bookshelf){
        int dimType=6;
        int goals;
        int [] seen = new int [dimType]; // matrice di contatori per tipo
        int notseen; // contatore
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
        goals=0;
        for(int j=0; j<mat[0].length && goals<2; j++){
            // per ogni colonna inizializzo a zeri la matrice
            for(int a=0; a<dimType; a++){
                seen[a]=0;
            }
            for(int i=0; i<mat.length; i++){
                switch (mat[i][j].getItemTile().getType()) {
                    case CAT -> seen[0]++;
                    case BOOK -> seen[1]++;
                    case GAME -> seen[2]++;
                    case FRAME -> seen[3]++;
                    case TROPHY -> seen[4]++;
                    case PLANT -> seen[5]++;
                    default -> System.out.println("type_tile not valid!");
                }
            }
            notseen=0;
            for(int a=0; a<dimType; a++){
                if(seen[a]==0){
                    notseen++;
                }
            }
            if(notseen==0){
                goals++;
            }
        }
        System.out.println("colonne formate ciascuna da 6 diversi tipi di tessere: "+goals+" o più");
        return goals >= 2;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
