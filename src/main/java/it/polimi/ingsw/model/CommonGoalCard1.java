package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CommonGoalCard1 extends CommonGoalCard {

    /**
     * Goal1: "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square."
     * Notes: - the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     * - also the square cannot be contained into larger squares, but it has to be exactly 2x2 (as requested by professor Cugola in Slack.channel-requirements)
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
        ArrayList<Integer> posgoal = new ArrayList<>(); // ArrayList of positions (x,y,x,y,etc) of the goals found and will be used to make uncheckable those near that

        // check the goal
        for (int i=0; i<mat.length-1 && goals<2; i++) {
            for (int j=0; j<mat[0].length-1 && goals<2; j++) {
                while ((checkable[i][j]==0 || mat[i][j].getTileID()==-1) && (j<mat[0].length-2)) { j++; }
                if (mat[i][j].getTileID()!=-1 && mat[i+1][j+1].getTileID()!=-1 && mat[i+1][j].getTileID()!=-1 && mat[i][j+1].getTileID()!=-1
                        && mat[i][j].getType().equals(mat[i+1][j+1].getType()) && mat[i][j].getType().equals(mat[i+1][j].getType()) && mat[i][j].getType().equals(mat[i][j+1].getType())) {
                    posgoal.add(i);
                    posgoal.add(j);
                    posgoal.add(i);
                    posgoal.add(j+1);
                    posgoal.add(i+1);
                    posgoal.add(j);
                    posgoal.add(i+1);
                    posgoal.add(j+1);
                    if (checkNoLargerSquares(mat, i+1, j + 1)) { goals++; }
                    for (int x=0; x<posgoal.size(); x=x+2) { checkable = allNearUncheckable(checkable, posgoal.get(x), posgoal.get(x+1)); }
                    if (posgoal.size()>0) { posgoal.subList(0, posgoal.size()).clear(); }
                }
            }
        }
        return goals>=2;
    }

    /**
     * checkNoLargerSquares is used to check that the square found is exactly of 2x2 and it is not contained into larger squares
     * Notes: checkNear is only used by checkGoal1()
     * @param mat matrix of ItemTile[][]
     * @param x as the 'i' of the position to check (of the tile in the right down corner)
     * @param y as the 'j' of the position to check (of the tile in the right down corner)
     * @return boolean if it is not contained in a larger square
     */
    private boolean checkNoLargerSquares (ItemTile[][] mat, int x, int y){
        if(mat[x][y].getTileID()!=-1 && (( y+1<mat[0].length && mat[x][y+1].getTileID()!=-1 && !mat[x][y].getType().equals(mat[x][y+1].getType()) ) ||
                ( x+1<mat.length && mat[x+1][y].getTileID()!=-1 && !mat[x][y].getType().equals(mat[x+1][y].getType()) ) ||
                ( x+1<mat.length && y+1<mat[0].length && mat[x+1][y+1].getTileID()!=-1 && !mat[x][y].getType().equals(mat[x+1][y+1].getType()) ) ||
                ( x-1>=0 && y+1<mat[0].length && mat[x-1][y+1].getTileID()!=-1 && !mat[x][y].getType().equals(mat[x-1][y+1].getType()) ) ||
                ( x+1<mat.length && y-1>=0 && mat[x+1][y-1].getTileID()!=-1 && !mat[x][y].getType().equals(mat[x+1][y-1].getType()) ) )) {
            return true;
        }
        // if all those (in the dimensions) are null then it does not have larger squares
        if( ( y+1<mat[0].length && mat[x][y+1].getTileID()==-1 ) ||
                ( x+1<mat.length && mat[x+1][y].getTileID()==-1 ) ||
                ( x+1<mat.length && y+1<mat[0].length && mat[x+1][y+1].getTileID()==-1 ) ||
                ( x-1>=0 && y+1<mat[0].length && mat[x-1][y+1].getTileID()==-1 ) ||
                ( x+1<mat.length && y-1>=0 && mat[x+1][y-1].getTileID()==-1 ) ){
            return true;
        }
        return y+1==mat[0].length && x+1==mat.length; // corner case
    }

    /**
     * allNearUncheckable will put at '0' the positions of the matching tiles found
     * Notes: allNearUncheckable is used by checkGoal1(), checkGoal3() and checkGoal4()
     * @param checkable matrix used to skips those near, that cannot be part of other groups to count for the goal
     * @param x as the 'i' of the position to check
     * @param y as the 'j' of the position to check
     * @return the matrix of checkable updated
     */
    public int [][] allNearUncheckable (int [][] checkable, int x, int y){
        checkable[x][y]=0;
        if(x-1>=0){ checkable[x-1][y]=0; }
        if(x+1<checkable.length){ checkable[x+1][y]=0; }
        if(x-1>=0 && y-1>=0){ checkable[x-1][y-1]=0; }
        if(x-1>=0 && y+1<checkable[0].length){ checkable[x-1][y+1]=0; }
        if(y-1>=0){ checkable[x][y-1]=0; }
        if(y+1<checkable[0].length){ checkable[x][y+1]=0; }
        if(x+1<checkable.length && y-1>=0){ checkable[x+1][y-1]=0; }
        if(x+1<checkable.length && y+1<checkable[0].length){ checkable[x+1][y+1]=0; }
        return checkable;
    }

}