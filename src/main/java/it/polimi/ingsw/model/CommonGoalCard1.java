package it.polimi.ingsw.model;

public class CommonGoalCard1 extends CommonGoalCard{
    /*
      Due gruppi separati di 4 tessere dello stesso tipo che formano un quadrato 2x2.
      Le tessere dei due gruppi devono essere dello stesso tipo.
    */

    @Override
    boolean checkGoal(Bookshelf bookshelf){
        int goals;
        int goali, goalj;
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
        goali=-2;
        goalj=-2;
        for(int i=0; i<mat.length-1 && goals<2; i++){
            for(int j=0; j<mat[i].length-1 && goals<2; j++){
                //non controllo quelli vicini a quello del goal
                if( goals==1 && ( (i==goali+1 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) ) || (i==goali+2 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) )) ){
                    j=goalj+2;
                }
                if(mat[i][j].getItemTile()!=null
                        && mat[i][j].getItemTile().getType().equals(mat[i+1][j+1].getItemTile().getType())
                        && mat[i][j].getItemTile().getType().equals(mat[i+1][j].getItemTile().getType())
                        && mat[i][j].getItemTile().getType().equals(mat[i][j+1].getItemTile().getType())
                        ){
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
