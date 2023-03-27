package it.polimi.ingsw.model;

public class CommonGoalCard9 extends CommonGoalCard{
    /*
      Otto tessere dello stesso tipo. Non ci sono restrizioni sulla posizione di queste tessere.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf){
        int N=6, M=5;
        int dimType=6;
        int [] seen = new int [dimType]; // matrice di contatori per tipo
        int [][] mat = {
                { 1, 2, 3, 4, 5 },
                { 1, 1, 1, 4, 5 },
                { 1, 2, 3, 4, 5 },
                { 1, 2, 3, 4, 5 },
                { 1, 2, 3, 4, 5 },
                { 1, 2, 3, 4, 5 }
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
        //inizializzo a zeri la matrice dei contatori
        for(int a=0; a<dimType; a++){
            seen[a]=0;
        }
        for(int i=0; i<N; i++){
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
