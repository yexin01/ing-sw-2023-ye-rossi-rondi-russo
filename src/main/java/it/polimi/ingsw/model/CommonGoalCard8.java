package it.polimi.ingsw.model;

public class CommonGoalCard8 extends CommonGoalCard{
    /**
     * Goal8: Four tiles of the same type in the four corners of the bookshelf.
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat){
        // check the goal
        return mat[0][0].getType().equals(mat[mat.length - 1][0].getType())
                && mat[0][0].getType().equals(mat[mat.length - 1][mat[0].length - 1].getType())
                && mat[0][0].getType().equals(mat[0][mat[0].length - 1].getType());
    }
}
