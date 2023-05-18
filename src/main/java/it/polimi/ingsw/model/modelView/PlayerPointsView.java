package it.polimi.ingsw.model.modelView;

import java.io.Serializable;

//TODO based on how the graphic part is implemented, decide to keep it or not
public class PlayerPointsView implements Serializable {

    private final int[] commonGoalPoints;
    private final int adjacentPoints;
    private final String nickname;


    public PlayerPointsView( int[] commonGoalPoints,int adjacentPoints, String nickname) {
        this.commonGoalPoints = commonGoalPoints;
        this.adjacentPoints = adjacentPoints;
        this.nickname = nickname;
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
    public int getPoints(){
        int sum=0;
        for(int n:commonGoalPoints){
            sum+=n;
        }
        int un=adjacentPoints+sum;
        return adjacentPoints+sum;
    }
    public int getPointsToken(int token){
        return commonGoalPoints[token];
    }


    public int getAdjacentPoints() {
        return adjacentPoints;
    }

    public String getNickname() {
        return nickname;
    }
}
