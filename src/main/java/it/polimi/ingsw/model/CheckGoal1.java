package it.polimi.ingsw.model;

public class CheckGoal1 implements CheckGoalStrategy {
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        return checkGoal1(mat);
    }

    private boolean checkGoal1(ItemTile[][] mat) {
        int goals;
        int goali, goalj;

        // check the goal
        goals=0;
        goali=-2;
        goalj=-2;
        for(int i=0; i<mat.length-1 && goals<2; i++){
            for(int j=0; j<mat[0].length-1 && goals<2; j++){
                // it skips the positions near the goals groups already found
                if( goals==1 && ( (i==goali+1 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) ) || (i==goali+2 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) )) ){
                    j=goalj+2;
                }
                if(mat[i][j].getTileID()!=-1
                        && mat[i][j].getType().equals(mat[i+1][j+1].getType())
                        && mat[i][j].getType().equals(mat[i+1][j].getType())
                        && mat[i][j].getType().equals(mat[i][j+1].getType()) ){
                    goals++;
                    goali=i;
                    goalj=j;
                    j=j+2; // because at the end of the for it does automatically j++
                }
            }
        }
        return goals >= 2;
    }
}
