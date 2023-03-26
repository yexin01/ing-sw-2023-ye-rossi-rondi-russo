package it.polimi.ingsw.model;

public class CommonGoalCard1 extends CommonGoalCard{
    /*
      Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
      Le tessere dei due gruppi devono essere dello stesso tipo.
    */

    @Override
    public boolean checkGoal(){
        int N=6, M=5;
        int goals;
        int [][] mat = {
                { 1, 1, 2, 3, 1 },
                { 1, 1, 3, 2, 3 },
                { 1, 1, 4, 3, 3 },
                { 2, 3, 2, 4, 5 },
                { 3, 3, 4, 5, 6 },
                { 1, 2, 3, 4, 5 }
        };
        int goali, goalj;
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
        goali=-2;
        goalj=-2;
        for(int i=0; i<N-1 && goals<2; i++){
            for(int j=0; j<M-1 && goals<2; j++){
                //non controllo quelli vicini a quello del goal
                if( goals==1 && (
                        (i==goali+1 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) ) || (i==goali+2 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) ))
                ){
                    j=goalj+2;
                }
                if(mat[i][j]!=0 /*da sostituire con occupied*/
                        && mat[i][j]==mat[i+1][j+1] && mat[i][j]==mat[i+1][j] && mat[i][j]==mat[i][j+1]){
                    goals++;
                    goali=i;
                    goalj=j;
                    j=j+2; //alla fine del for fa j++
                }
            }
        }
        System.out.println("quadrati trovati: "+goals+" o più");
        return goals >= 2;
    }


    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
