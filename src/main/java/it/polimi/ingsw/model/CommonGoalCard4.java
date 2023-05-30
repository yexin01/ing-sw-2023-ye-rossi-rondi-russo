package it.polimi.ingsw.model;

public class CommonGoalCard4 extends CommonGoalCard{

    /**
     * Goal4: "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).
     *         The tiles of one group can be different from those of another group."
     * Notes: the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     *        (as requested by professor Cugola in Slack.channel-requirements)
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    @Override
    public boolean checkGoal(ItemTile[][] mat){
        int goals=0;
        int[][] checkable = new int[mat.length][mat[0].length];
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[0].length; j++) {
                checkable[i][j]=1;
            }
        }
        // check the goal
        for(int i=0; i<mat.length && goals<6; i++){
            for(int j=0; j<mat[0].length-1 && goals<6; j++){
                while ((checkable[i][j]==0 || mat[i][j].getTileID()==-1) && (j<mat[0].length-2)) { j++; }
                if( i+1<mat.length &&
                        mat[i][j].getTileID()!=-1 && mat[i+1][j].getTileID()!=-1
                        && checkable[i+1][j]==1
                        && mat[i][j].getType().equals(mat[i+1][j].getType()) ){
                    goals++;
                    checkable = allNearUncheckable(checkable,i,j);
                    checkable = allNearUncheckable(checkable,i+1,j);
                } else if ( j+1<mat[0].length &&
                        mat[i][j].getTileID()!=-1 && mat[i][j+1].getTileID()!=-1
                        && checkable[i][j+1]==1
                        && mat[i][j].getType().equals(mat[i][j+1].getType()) ) {
                    goals++;
                    checkable = allNearUncheckable(checkable,i,j);
                    checkable = allNearUncheckable(checkable,i,j+1);
                }
                checkable[i][j]=0;
            }
        }
        return goals>=6;
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