package it.polimi.ingsw.model;

public class CommonGoalCard10 extends CommonGoalCard{

    /**
     * Goal10: "Five tiles of the same type forming an X."
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        for (int i=1; i<mat.length-1; i++) {
            for (int j=1; j<mat[0].length-1; j++) {
                if ( mat[i][j].getTileID()!=-1 && mat[i-1][j-1].getTileID()!=-1 && mat[i][j].getType().equals(mat[i-1][j-1].getType())
                        && mat[i-1][j+1].getTileID()!=-1 && mat[i][j].getType().equals(mat[i-1][j+1].getType())
                        && mat[i+1][j-1].getTileID()!=-1 && mat[i][j].getType().equals(mat[i+1][j-1].getType())
                        && mat[i+1][j+1].getTileID()!=-1 && mat[i][j].getType().equals(mat[i+1][j+1].getType())
                ){
                    return true;
                }
            }
        }
        return false;
    }

}
