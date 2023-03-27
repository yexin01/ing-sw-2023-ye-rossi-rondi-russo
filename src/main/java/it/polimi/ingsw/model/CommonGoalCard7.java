package it.polimi.ingsw.model;

public class CommonGoalCard7 extends CommonGoalCard{
    /*
      Quattro righe formate ciascuna da 5 tessere di uno, due o tre tipi differenti.
      Righe diverse possono avere combinazioni diverse di tipi di tessere.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf){
        int N=6, M=5;
        int dimType=6;
        int goals;
        int [] seen = new int [dimType]; // matrice di contatori per tipo
        int notseen; // contatore
        int [][] mat = { //dovrei averne 4
                { 1, 1, 1, 3, 1},
                { 2, 1, 1, 5, 6},
                { 1, 1, 1, 4, 5},
                { 4, 4, 3, 3, 5},
                { 5, 5, 5, 3, 6},
                { 6, 4, 3, 3, 4}
        };
        // stampo matrice
        System.out.println("Matrice: ");
        for(int i=0; i<N; i++){
            for(int j=0; j<M; j++){
                System.out.print(mat[i][j] + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        goals=0;
        for(int i=0; i<N && goals<4; i++){
            // per ogni colonna inizializzo a zeri la matrice
            for(int a=0; a<dimType; a++){
                seen[a]=0;
            }
            for(int j=0; j<M; j++){
                switch (mat[i][j]) {
                    case 1 -> seen[0]++;
                    case 2 -> seen[1]++;
                    case 3 -> seen[2]++;
                    case 4 -> seen[3]++;
                    case 5 -> seen[4]++;
                    case 6 -> seen[5]++;
                    default -> System.out.println("type_tile non valido!");
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
