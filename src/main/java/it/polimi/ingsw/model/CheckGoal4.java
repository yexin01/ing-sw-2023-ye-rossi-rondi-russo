package it.polimi.ingsw.model;

public class CheckGoal4 implements CheckGoalStrategy {
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        return checkGoal4(mat);
    }

    private boolean checkGoal4(ItemTile[][] mat) {
        int goals;
        int [][] checkable = {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
        };

        // check the goal
        goals=0;
        for(int i=0; i<mat.length && goals<6; i++){
            for(int j=0; j<mat[0].length-1 && goals<6; j++){
                while(checkable[i][j]==0 && (j<mat[0].length-2)){
                    j++;
                }
                checkable[i][j]=0;
                if(i<mat.length-1 && mat[i][j].getType().equals(mat[i+1][j].getType()) && checkable[i+1][j]==1){
                    goals++;
                    checkable[i+1][j]=0;
                } else if (mat[i][j].getType().equals(mat[i][j+1].getType()) && checkable[i][j+1]==1) {
                    goals++;
                    checkable[i][j+1]=0;
                }
            }
        }
        return goals >= 6;
    }
}
