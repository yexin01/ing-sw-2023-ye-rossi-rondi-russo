package it.polimi.ingsw.model;

public class CheckGoal8 implements CheckGoalStrategy {
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        return checkGoal8(mat);
    }

    private boolean checkGoal8(ItemTile[][] mat) {
        // check the goal
        return mat[0][0].getType().equals(mat[mat.length - 1][0].getType())
                && mat[0][0].getType().equals(mat[mat.length - 1][mat[0].length - 1].getType())
                && mat[0][0].getType().equals(mat[0][mat[0].length - 1].getType());
    }
}
