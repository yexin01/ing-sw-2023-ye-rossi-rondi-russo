package it.polimi.ingsw.model;

public class CommonGoalCard4 extends CommonGoalCard{
    /*
      Sei gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo
      (non necessariamente come mostrato in figura).
      Le tessere di un gruppo possono essere diverse da quelle di un altro gruppo.
      Ho libreria come matrice 6x5
      ho un flag checkable sulla bookshelfBox
      -----> setto tutti i checkable a 1 PRIMA di chiamare il checkgoal!!
    */

    @Override
    boolean checkGoal(){
        int N=6, M=5;
        int goals;
        int [][] mat = {
                { 1, 1, 3, 5, 5 },
                { 2, 3, 4, 5, 6 },
                { 1, 2, 3, 4, 5 },
                { 2, 3, 3, 5, 6 },
                { 1, 3, 3, 3, 5 },
                { 2, 3, 4, 5, 5 }
        };
        int [][] checkable = { //saranno nella bookshelf box come boolean
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
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
        for(int i=0; i<N && goals<6; i++){
            for(int j=0; j<M-1 && goals<6; j++){
                while(checkable[i][j]==0 && (j<M-2)){
                    j++;
                }
                checkable[i][j]=0; //segno come uncheckable quello su cui sono
                if(i<N-1 && mat[i][j]==mat[i+1][j] && checkable[i+1][j]==1){
                    goals++;
                    checkable[i+1][j]=0;
                } else if (mat[i][j]==mat[i][j+1] && checkable[i][j+1]==1) {
                    goals++;
                    checkable[i][j+1]=0;
                }
            }
        }
        System.out.println("gruppi separati formati ciascuno da due tessere adiacenti dello stesso tipo: "+goals+" o più");
        return goals >= 6;
    }





    /*
    ScoringToken pullToken(){

        return ScoringToken;
    }
    */


}
