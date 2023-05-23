package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCard3 extends CommonGoalCard {

    /**
     * Goal3: "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
     * The tiles of one group can be different from those of another group."
     * Notes: the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     * (as requested by professor Cugola in Slack.channel-requirements)
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        int goals=0;
        int[][] checkable = new int[mat.length][mat[0].length];
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[0].length; j++) {
                checkable[i][j]=1;
            }
        }
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[0].length; j++) {
                if (mat[i][j].getTileID()!=-1 && checkable[i][j]!=0) {
                    goals += processGroup(mat,checkable,i,j);
                }
            }
        }
        return goals>=4;
    }

    /**
     * Method to check right, down, diagonal directions if it can be counted as a valid group for the goal.
     * @param mat matrix of ItemTile[][]
     * @param checkable matrix of checkable elements
     * @param x of the position to check
     * @param y of the position to check
     * @return 0 if not valid group, 1 if valid group
     */
    private int processGroup(ItemTile[][] mat, int[][] checkable, int x, int y) {
        if (mat[x][y].getTileID()==-1) {
            return 0;
        }
        Type type = mat[x][y].getType();
        List<Integer> groupPositions = new ArrayList<>();
        groupPositions.add(x);
        groupPositions.add(y);

        int[][] directions = {{0,1},{1,0}}; // Right and down directions
        for (int[] direction : directions) {
            int dx=direction[0];
            int dy=direction[1];
            int newX=x+dx;
            int newY=y+dy;
            if (isValidPosition(mat,newX,newY) && mat[newX][newY].getTileID()!=-1 && mat[newX][newY].getType().equals(type)) {
                groupPositions.add(newX);
                groupPositions.add(newY);
            }
        }
        if (groupPositions.size()>=8) {
            markGroup(checkable,groupPositions);
            return 1;
        }
        return 0;
    }

    /**
     * Method to check if the coordinates are valid to check within the bookshelf dimensions.
     * @param mat matrix of ItemTile[][]
     * @param x   of the position to check
     * @param y   of the position to check
     * @return true if valid, false if not valid
     */
    private boolean isValidPosition(ItemTile[][] mat, int x, int y) {
        return x>=0 && x<mat.length && y>=0 && y<mat[0].length;
    }

    /**
     * Method to put 0 on checkable matrix of all tiles near a valid group found. To be sure all groups counted for the goal,
     * are in fact separated by at least one element.
     * @param checkable matrix of checkable elements
     * @param positions list of the coordinates of the positions of the group found
     */
    private void markGroup(int[][] checkable, List<Integer> positions) {
        for (int i=0; i<positions.size(); i+=2) {
            int x=positions.get(i);
            int y=positions.get(i+1);
            checkable[x][y]=0;
        }
    }
}