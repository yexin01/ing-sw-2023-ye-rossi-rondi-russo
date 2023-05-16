package it.polimi.ingsw.model;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard2 extends CommonGoalCard {

    /**
     * Goal2: "Two columns each formed by 6 different types of tiles."
     * Notes: the column should be full to be counted as a goal
     *
     * @param mat matrix of ItemTile[][]
     * @return boolean if the goal is reached or not
     */
    @Override
    public boolean checkGoal(ItemTile[][] mat) {
        int goalsToReach=2;
        int columnCount=mat[0].length;
        int goals=0;
        for (int j=0; j<columnCount && goals<goalsToReach; j++) {
            Set<Type> types = new HashSet<>();
            boolean isFull=true;
            for (int i=0; i<mat.length; i++) {
                ItemTile tile = mat[i][j];
                if (tile.getTileID()!=-1) {
                    types.add(tile.getType());
                } else {
                    isFull = false;
                }
            }
            if (isFull && types.size()==6) {
                goals++;
            }
        }
        return goals>=goalsToReach;
    }

}