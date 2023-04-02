package it.polimi.ingsw.model;

public class CheckGoal11 implements CheckGoalStrategy {
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        return checkGoal11(mat);
    }

    private boolean checkGoal11(ItemTile[][] mat) {
        boolean verified;
        // check the goal
        for (int i=0; i<= mat.length-mat[0].length; i++) {
            verified=true;
            for (int a=1; a<mat[0].length && verified; a++) {
                if (!mat[i][0].getType().equals(mat[i+a][a].getType())) {
                    verified=false;
                }
            }
            if(verified){
                return true;
            }
        }
        return false;
    }
}
