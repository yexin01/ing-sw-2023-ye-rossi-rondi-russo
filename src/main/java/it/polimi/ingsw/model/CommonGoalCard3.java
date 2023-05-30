package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CommonGoalCard3 extends CommonGoalCard{

    /**
     * Goal3: "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).
     *         The tiles of one group can be different from those of another group."
     * Notes: the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     *        (as requested by professor Cugola in Slack.channel-requirements)
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */

    @Override
    public boolean checkGoal(ItemTile[][] mat){
        int goals,near,oldnear;
        int newi,newj;
        int[][] checkable = new int[mat.length][mat[0].length];
        for (int i=0; i<mat.length; i++) {
            for (int j=0; j<mat[0].length; j++) {
                checkable[i][j]=1;
            }
        }
        ArrayList<Integer> posgoal = new ArrayList<>(); // ArrayList of positions (x,y,x,y,etc) of the goals found and will be used to make uncheckable those near that
        int x; // will be used to move in the ArrayList posgoal

        // check the goal
        goals=0;
        for(int i=0; i<mat.length-1 && goals<4; i++){
            for(int j=0; j<mat[0].length-1 && goals<4; j++){
                while ((checkable[i][j]==0 || mat[i][j].getTileID()==-1) && (j<mat[0].length-2)) { j++; }
                posgoal.add(i);
                posgoal.add(j);
                newi=i;
                newj=j;
                x=2;
                near=2;
                do{
                    oldnear=near;
                    posgoal= checkNear(posgoal,mat,newi,newj);
                    near=posgoal.size();
                    if(near>oldnear){
                        newi=posgoal.get(x);
                        newj=posgoal.get(x+1);
                        x=x+2;
                    }
                } while ((near<8 && (i<mat.length-1) && (j<mat[0].length-1)) && ((near>oldnear || x<posgoal.size()) && x<posgoal.size()));

                if(near>=8){
                    goals++;
                    //make uncheckable those of the group found and those near
                    x=0;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1));
                    x=x+2;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1));
                    x=x+2;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1));
                    x=x+2;
                    checkable = allNearUncheckable (checkable, posgoal.get(x), posgoal.get(x+1));
                }
                if (near>0) { posgoal.subList(0, near).clear(); }
                checkable[i][j]=0;
            }
        }
        return goals>=4;
    }

    /**
     * checkNear() will check the position on right, left and bottom to see if there are any other tiles with the same type, if it finds any, its positions will be added to the ArrayList posgoal
     * Notes: checkNear is only used by checkGoal3()
     * @param posgoal that will be return with the new finds
     * @param mat matrix of ItemTile[][] to check
     * @param a as the 'i' of the position to check
     * @param b as the 'j' of the position to check
     * @return ArrayList posgoal updated with new positions if any matches found
     */
    public ArrayList<Integer> checkNear (ArrayList<Integer> posgoal, ItemTile [][] mat, int a, int b){
        if(a+1<mat.length && mat[a+1][b].getTileID()!=-1 && mat[a][b].getTileID()!=-1 && mat[a][b].getType().equals(mat[a+1][b].getType())){
            posgoal.add(a+1);
            posgoal.add(b);
        }
        if(b-1>=0 && mat[a][b-1].getTileID()!=-1 && mat[a][b].getTileID()!=-1 && mat[a][b].getType().equals(mat[a][b-1].getType())){
            posgoal.add(a);
            posgoal.add(b-1);
        }
        if(b+1<mat[0].length && mat[a][b+1].getTileID()!=-1 && mat[a][b].getTileID()!=-1 && mat[a][b].getType().equals(mat[a][b+1].getType())){
            posgoal.add(a);
            posgoal.add(b+1);
        }
        return posgoal;
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