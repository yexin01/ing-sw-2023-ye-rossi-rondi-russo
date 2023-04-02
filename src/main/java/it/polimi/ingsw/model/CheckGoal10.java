package it.polimi.ingsw.model;

public class CheckGoal10 implements CheckGoalStrategy {
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        return checkGoal10(mat);
    }

    private boolean checkGoal10(ItemTile[][] mat) {
        for (int i=1; i<mat.length-1; i++) {
            for (int j=1; j<mat[0].length-1; j++) {
                if ( (mat[i][j].getType().equals(mat[i-1][j-1].getType()))
                        && (mat[i][j].getType().equals(mat[i-1][j+1].getType()))
                        && (mat[i][j].getType().equals(mat[i+1][j-1].getType()))
                        && (mat[i][j].getType().equals(mat[i+1][j+1].getType())) ){
                    return true;
                }
            }
        }
        return false;
    }
}
