package it.polimi.ingsw.model;

public class CommonGoalCard4 extends CommonGoalCard{
    /**
     * Goal4: "Six groups each containing at least 2 tiles of the same type (not necessarily in the depicted shape).
     *         The tiles of one group can be different from those of another group."
     * Notes: the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     *        (as requested by professor Cugola in Slack.channel-requirements)
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat){
        int goals;
        int [][] checkable = {
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 },
                { 1, 1, 1, 1, 1 }
        };

        // check the goal
        goals=0;
        for(int i=0; i<mat.length && goals<6; i++){
            for(int j=0; j<mat[0].length-1 && goals<6; j++){
                while((checkable[i][j]==0 || mat[i][j].getTileID()==-1) && j<mat[0].length-2){
                    j++;
                }
                checkable[i][j]=0;
                if(mat[i][j].getType().equals(mat[i+1][j].getType()) && mat[i+1][j].getTileID()!=-1 && checkable[i+1][j]==1 && i<mat.length-1){
                    goals++;
                    checkable[i+1][j]=0;
                } else if (mat[i][j].getType().equals(mat[i][j+1].getType()) && mat[i][j+1].getTileID()!=-1 && checkable[i][j+1]==1) {
                    goals++;
                    checkable[i][j+1]=0;
                }
            }
        }
        return goals >= 6;
    }
}
