package it.polimi.ingsw.model;

public class CommonGoalCard5 extends CommonGoalCard {

    /**
     * Goal5: "Three columns each formed by 6 tiles of maximum three different types. One column can show the same or a different combination of another column."
     * Notes: the column should be full to be counted as a goal
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        int goalsToReach=3;
        int lines=mat.length;
        int columns=mat[0].length;
        int goals=0;
        for (int j=0; j<columns && goals<goalsToReach; j++) {
            int[] seen = new int[Type.values().length];
            boolean isFull=true;
            for (int i=0; i<lines; i++) {
                Type type = mat[i][j].getType();
                int tileID = mat[i][j].getTileID();
                if(tileID!=-1) {
                    seen[type.ordinal()]++;
                } else {
                    isFull = false;
                }
            }
            int notSeen=countNotSeenTypes(seen);
            if (isFull && notSeen<=3) {
                goals++;
            }
        }
        return goals>=goalsToReach;
    }

    /**
     * Method to count the number of types that have not been seen.
     * @param seen array of counters for each Type of tile seen
     * @return counter of types not seen
     */
    private int countNotSeenTypes(int[] seen) {
        int notSeen=0;
        for (int count : seen) {
            if (count==0) {
                notSeen++;
            }
        }
        return notSeen;
    }

}