package it.polimi.ingsw.model.modelView;

import java.io.Serializable;

public class CommonGoalView implements Serializable {

    private final String whoWonLastToken;
    private final int[] points;

    public CommonGoalView(String whoWonLastToken, int[] points) {
        this.whoWonLastToken = whoWonLastToken;
        this.points = points;
    }

    /*
    public int getLastPointLeft() {
        return pointsLeft[pointsLeft.length-1];
    }
     */
    public String getWhoWonLastToken() {
        return whoWonLastToken;
    }

    public int getLastPointsLeft() {
        return points[points.length-1];
    }
/*
    public CommonGoalCard getCommonGoalCard() {
        return commonGoalCard;
    }

 */
}
