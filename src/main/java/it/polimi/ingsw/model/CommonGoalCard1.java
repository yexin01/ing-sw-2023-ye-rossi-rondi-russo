package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

public class CommonGoalCard1 extends CommonGoalCard {

    /**
     * Goal1: "Two groups each containing 4 tiles of the same type in a 2x2 square.
     * The tiles of one square can be different from those of the other square."
     * Notes: - The implementation follows the Italian rules where "gruppi separati" refers to groups separated by at least 1 box in the matrix.
     * - The square cannot be contained within larger squares; it must be exactly 2x2.
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
        return goals>=2 && !containsLargerSquare(mat);
    }

    /**
     * Method to check right, down, diagonal directions if it can be counted as a valid group for the goal and if it is a 2x2 square.
     * @param mat matrix of ItemTile[][]
     * @param checkable matrix of checkable elements
     * @param x of the position to check
     * @param y of the position to check
     * @return 0 if not valid group, 1 if valid group
     */
    private int processGroup(ItemTile[][] mat, int[][] checkable, int x, int y) {
        if (mat[x][y].getTileID() == -1) {
            return 0; // Skip empty ItemTile
        }

        Type type = mat[x][y].getType();
        List<Integer> squarePositions = new ArrayList<>();
        squarePositions.add(x);
        squarePositions.add(y);

        int[][] directions = {{0,1},{1,0},{1,1}}; // Right, down, and diagonal directions
        for (int[] direction : directions) {
            int dx=direction[0];
            int dy=direction[1];
            int newX=x+dx;
            int newY=y+dy;
            if (isValidPosition(mat,newX,newY) && mat[newX][newY].getTileID()!=-1 && mat[newX][newY].getType().equals(type)) {
                squarePositions.add(newX);
                squarePositions.add(newY);
            }
        }
        if (squarePositions.size()>=8 && is2x2Square(squarePositions)) {
            markGroup(checkable,squarePositions);
            return 1;
        }
        return 0;
    }

    /**
     * Method to check if the coordinates are valid to check within the bookshelf dimensions.
     * @param mat matrix of ItemTile[][]
     * @param x of the position to check
     * @param y of the position to check
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

    /**
     * Method to check that there are any larger squares, if there are then the check will not count it as a goal.
     * @param mat matrix of ItemTile[][]
     * @return true if found larger squares, false otherwise
     */
    private boolean containsLargerSquare(ItemTile[][] mat) {
        for (int i=0; i<mat.length-1; i++) {
            for (int j=0; j<mat[0].length-1; j++) {
                if (mat[i][j].getType().equals(mat[i][j+1].getType()) &&
                        mat[i+1][j].getType().equals(mat[i+1][j+1].getType()) &&
                        mat[i][j].getType().equals(mat[i+1][j].getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to check if it exactly a 2x2 square.
     * @param positions list of the coordinates of the positions of the group found
     * @return true if it is a 2x2 square, false otherwise
     */
    private boolean is2x2Square(List<Integer> positions) {
        int minX=Integer.MAX_VALUE;
        int minY=Integer.MAX_VALUE;
        int maxX=Integer.MIN_VALUE;
        int maxY=Integer.MIN_VALUE;
        for (int i=0; i<positions.size(); i+=2) {
            int x=positions.get(i);
            int y=positions.get(i+1);
            minX=Math.min(minX,x);
            minY=Math.min(minY,y);
            maxX=Math.max(maxX,x);
            maxY=Math.max(maxY,y);
        }
        return (maxX-minX==1 && maxY-minY==1);
    }

}