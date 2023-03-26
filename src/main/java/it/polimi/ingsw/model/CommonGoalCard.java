package it.polimi.ingsw.model;

import java.util.ArrayList;

public abstract class CommonGoalCard {

    private ArrayList<Integer> points;

    public abstract boolean checkGoal();


    public ArrayList<Integer> getPoints() {
        return points;
    }

    public void setPoints(ArrayList<Integer> points) {
        this.points = points;
    }
}