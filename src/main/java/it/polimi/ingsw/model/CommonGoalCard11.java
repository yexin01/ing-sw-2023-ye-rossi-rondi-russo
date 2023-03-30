package it.polimi.ingsw.model;

public class CommonGoalCard11 extends CommonGoalCard{
    /**
     * Goal11: "Five tiles of the same type forming a diagonal."
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(int numCommonGoalCard, ItemTile[][] mat) {
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
