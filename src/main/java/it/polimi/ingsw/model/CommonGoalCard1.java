package it.polimi.ingsw.model;

public class CommonGoalCard1 extends CommonGoalCard{
    /**
     * Goal1: "Two groups each containing 4 tiles of the same type in a 2x2 square. The tiles of one square can be different from those of the other square."
     * Notes: the implementation of this function follows the Italian rules where it says "gruppi separati" as groups separated by at least 1 box in the matrix
     *        (as requested by professor Cugola in Slack.channel-requirements)
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat){
        int goals;
        int goali, goalj;

        // check the goal
        goals=0;
        goali=-2;
        goalj=-2;
        for(int i=0; i<mat.length-1 && goals<2; i++){
            for(int j=0; j<mat[0].length-1 && goals<2; j++){
                // it skips the positions near the goals groups already found
                if( goals==1 && ( (i==goali+1 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) ) || (i==goali+2 && ((j==goalj-2)||(j==goalj-1)||(j==goalj)) )) ){
                    j=goalj+2;
                }
                if(mat[i][j].getTileID()!=-1
                        && mat[i][j].getType().equals(mat[i+1][j+1].getType())
                        && mat[i][j].getType().equals(mat[i+1][j].getType())
                        && mat[i][j].getType().equals(mat[i][j+1].getType()) ){
                    goals++;
                    goali=i;
                    goalj=j;
                    j=j+2; // because at the end of the for it does automatically j++
                }
            }
        }
        return goals >= 2;
    }
}
