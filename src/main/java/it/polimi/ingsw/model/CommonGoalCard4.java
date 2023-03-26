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
    public boolean checkGoal(Bookshelf bookshelf){
        int goals;
        BookshelfBox[][] mat = bookshelf.getMatrix();
        int [][] checkable = {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
        };
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
        for(int i=0; i<mat.length && goals<6; i++){
            for(int j=0; j<mat[i].length-1 && goals<6; j++){
                while(checkable[i][j]==0 && (j<mat[i].length-2)){
                    j++;
                }
                checkable[i][j]=0; //segno come uncheckable quello su cui sono
                if(i<mat.length-1 && mat[i][j].getItemTile().getType().equals(mat[i+1][j].getItemTile().getType()) && checkable[i+1][j]==1){
                    goals++;
                    checkable[i+1][j]=0;
                } else if (mat[i][j].getItemTile().getType().equals(mat[i][j+1].getItemTile().getType()) && checkable[i][j+1]==1) {
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
