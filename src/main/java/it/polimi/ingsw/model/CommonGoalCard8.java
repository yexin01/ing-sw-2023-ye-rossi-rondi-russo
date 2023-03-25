package it.polimi.ingsw.model;

public class CommonGoalCard8 extends CommonGoalCard{
    /*
      Quattro tessere dello stesso tipo ai quattro angoli della Libreria.
    */

    @Override
    boolean checkGoal(){
        int N=6, M=5;
        int [][] mat = {
                { 1, 1, 1, 3, 1 },
                { 2, 1, 1, 5, 6 },
                { 1, 1, 1, 4, 5 },
                { 4, 4, 3, 3, 5 },
                { 5, 5, 5, 3, 6 },
                { 1, 4, 3, 3, 1 }
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
        if(mat[0][0]==mat[N-1][0] && mat[0][0]==mat[N-1][M-1] && mat[0][0]==mat[0][M-1]){
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
