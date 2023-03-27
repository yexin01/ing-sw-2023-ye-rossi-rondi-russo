package it.polimi.ingsw.model;

public class CommonGoalCard10 extends CommonGoalCard{
    /*
      Cinque tessere dello stesso tipo che formano una X.
    */

    @Override
    public boolean checkGoal(Bookshelf bookshelf) {
        int N=6, M=5;
        int[][] mat = {
                { 1, 2, 3, 4, 5 },
                { 1, 6, 3, 6, 5 },
                { 1, 2, 6, 4, 5 },
                { 1, 2, 3, 6, 5 },
                { 1, 2, 3, 4, 5 },
                { 1, 2, 3, 4, 5 }
        };
        // stampo matrice
        System.out.println("Matrice: ");
        for (int i=0; i<N; i++) {
            for (int j=0; j<M; j++) {
                System.out.print(mat[i][j] + " ");
            }
            System.out.println(" ");
        }
        //controllo il goal
        for (int i=1; i<N-1; i++) {
            for (int j=1; j<M-1; j++) {
                if ((mat[i][j]==mat[i-1][j-1]) && (mat[i][j]==mat[i-1][j+1]) && (mat[i][j]==mat[i+1][j-1]) && (mat[i][j]==mat[i+1][j+1])) {
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
