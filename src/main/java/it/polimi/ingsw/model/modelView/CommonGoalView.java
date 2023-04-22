package it.polimi.ingsw.model.modelView;

public class CommonGoalView {
    private final int lastPointsLeft;
    private final String whoWonLastToken;

    public CommonGoalView(int lastPointsLeft, String whoWonLastToken) {
        this.lastPointsLeft = lastPointsLeft;
        this.whoWonLastToken = whoWonLastToken;
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
        return lastPointsLeft;
    }
/*
    public CommonGoalCard getCommonGoalCard() {
        return commonGoalCard;
    }

 */
}
