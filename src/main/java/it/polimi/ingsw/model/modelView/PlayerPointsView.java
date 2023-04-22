package it.polimi.ingsw.model.modelView;

//TODO based on how the graphic part is implemented, decide to keep it or not
public class PlayerPointsView {
    private final int points;
    private final int[] commonGoalPoints;
    private final int personalGoalPoints;
    private final int adjacentPoints;

    public PlayerPointsView(int points, int[] commonGoalPoints, int personalGoalPoints, int adjacentPoints) {
        this.points = points;
        this.commonGoalPoints = commonGoalPoints;
        this.personalGoalPoints = personalGoalPoints;
        this.adjacentPoints = adjacentPoints;
    }

    public int getHowManyTokenYouHave(){
        int num=0;
        for(Integer points:commonGoalPoints){
            if(points!=0){
                num++;
            }
        }
        return num;
    }
    public int getPointsToken(int token){
        return commonGoalPoints[token];
    }

    public int getPoints() {
        return points;
    }

    public int getPersonalGoalPoints() {
        return personalGoalPoints;
    }

    public int getAdjacentPoints() {
        return adjacentPoints;
    }
}
