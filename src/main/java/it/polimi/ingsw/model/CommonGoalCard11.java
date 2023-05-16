package it.polimi.ingsw.model;

public class CommonGoalCard11 extends CommonGoalCard {

    /**
     * Goal11: "Five tiles of the same type forming a diagonal."
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        int numRows=mat.length;
        int numCols=mat[0].length;
        //checks diagonal from top-left to bottom-right
        for (int row=0; row<=numRows-5; row++) {
            for (int col=0; col<=numCols-5; col++) {
                if (checkDiagonal(mat,row,col,1,1)) {
                    return true;
                }
            }
        }
        //checks diagonal from top-right to bottom-left
        for (int row=0; row<=numRows-5; row++) {
            for (int col=numCols-1; col>=4; col--) {
                if (checkDiagonal(mat,row,col,1,-1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method that checks if the diagonal starting at the specified position forms a diagonal pattern with five tiles of the same type.
     * @param mat matrix of ItemTile[][]
     * @param startRow starting row index
     * @param startCol starting column index
     * @param rowIncrement row increment value (1 for bottom-right direction, -1 for bottom-left direction)
     * @param colIncrement column increment value (1 for bottom-right direction, -1 for top-right direction)
     * @return boolean if a diagonal pattern is formed
     */
    private boolean checkDiagonal(ItemTile[][] mat, int startRow, int startCol, int rowIncrement, int colIncrement) {
        Type type=mat[startRow][startCol].getType();
        for (int i=1; i<5; i++) {
            int row=startRow+i*rowIncrement;
            int col=startCol+i*colIncrement;
            if (mat[row][col].getTileID()==-1 || !mat[row][col].getType().equals(type)) {
                return false;
            }
        }
        return true;
    }

}
