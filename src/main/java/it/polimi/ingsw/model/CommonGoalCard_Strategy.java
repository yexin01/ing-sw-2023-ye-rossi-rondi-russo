package it.polimi.ingsw.model;

import java.util.ArrayList;

public class CommonGoalCard_Strategy {
    private ArrayList<Integer> points;
    private CheckGoalStrategy checkGoalStrategy;

    public void setCheckGoalStrategy(CheckGoalStrategy checkGoalStrategy) {
        this.checkGoalStrategy = checkGoalStrategy;
    }

    public boolean checkGoal(ItemTile[][] mat){
        return checkGoalStrategy.checkGoal(mat);
    }

    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }
}
