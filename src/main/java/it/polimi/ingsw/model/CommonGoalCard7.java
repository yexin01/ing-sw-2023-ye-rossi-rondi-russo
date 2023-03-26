package it.polimi.ingsw.model;

public class CommonGoalCard7 extends CommonGoalCard{
    /*
      Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti.
      Righe diverse possono avere combinazioni diverse di tipi di tessere.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf){
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
        for(int i=0; i<mat.length && goals<4; i++){
            // per ogni colonna inizializzo a zeri la matrice
            for(int a=0; a<dimType; a++){
                seen[a]=0;
            }
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
            }
            notseen=0;
            for(int a=0; a<dimType; a++){
                if(seen[a]==0){
                    notseen++;
                }
            }
            if(notseen>=3){
                goals++;
            }
        }
        System.out.println("righe formate ciascuna da 5 tessere di max tre tipi differenti: "+goals+" o più");
        return goals >= 4;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
