package it.polimi.ingsw.model;

public class CommonGoalCard11 extends CommonGoalCard{
    /*
      Cinque tessere dello stesso tipo che formano una diagonale.
    */

    @Override
    public boolean checkGoal() {
        int N=6, M=5;
        int[][] mat = {
                { 1, 2, 3, 4, 5 },
                { 6, 2, 3, 4, 5 },
                { 1, 6, 3, 4, 5 },
                { 1, 2, 6, 4, 5 },
                { 1, 2, 3, 6, 5 },
                { 1, 2, 3, 4, 6 }
        };
        boolean verified;
        // stampo matrice
        System.out.println("Matrice: ");
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        for (int i=0; i<=N-M; i++) {
            verified=true;
            for (int a=1; a<M && verified; a++) {
                if (mat[i][0]!=mat[i+a][a]) {
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
