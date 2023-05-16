package it.polimi.ingsw.model;

public class CommonGoalCard6 extends CommonGoalCard {

    /**
     * Goal6: "Two lines each formed by 5 different types of tiles. One line can show the same or a different combination of the other line."
     * Notes: the line should be full to be counted as a goal
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        int goalsToReach=2;
        int lines=mat[0].length;
        int columns=mat.length;
        int goals=0;
        for (int j=0; j<columns && goals<goalsToReach; j++) {
            int[] seen = new int[Type.values().length];
            boolean isFull=true;
            for (int i=0; i<lines; i++) {
                Type type=mat[j][i].getType();
                int tileID=mat[j][i].getTileID();
                if (tileID!=-1) {
                    seen[type.ordinal()]++;
                } else {
                    isFull=false;
                }
            }
            int notSeen=countNotSeenTypes(seen);
            if (isFull && notSeen<=1) {
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