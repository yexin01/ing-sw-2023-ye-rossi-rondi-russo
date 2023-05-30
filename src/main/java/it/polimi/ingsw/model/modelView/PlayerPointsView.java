package it.polimi.ingsw.model.modelView;

import java.io.Serial;
import java.io.Serializable;

/**
 *Immutable class that represents the single player with his scores
 */
public class PlayerPointsView implements Serializable {

    private final int[] commonGoalPoints;
    private final int adjacentPoints;
    private final String nickname;

    @Serial
    private static final long serialVersionUID = -550443840165524261L;

    /**
     * Constructor PlayerPointsView
     * @param commonGoalPoints:array with common points scores, the position of each common
     *                        indicates the score relative to that common;
     * @param adjacentPoints:scores of adjacent tiles;
     * @param nickname: nickname of the player;
     */
    public PlayerPointsView( int[] commonGoalPoints,int adjacentPoints, String nickname) {
        this.commonGoalPoints = commonGoalPoints;
        this.adjacentPoints = adjacentPoints;
        this.nickname = nickname;
    }

    /**
     *
     * @return: sum of common goal points;
     */

    public int getPoints(){
        int sum=0;
        for(int n:commonGoalPoints){
            sum+=n;
        }
        int un=adjacentPoints+sum;
        return adjacentPoints+sum;
    }

    /**
     *
     * @return: array of common goal points
     */
    public int[] getPointsToken(){
        return commonGoalPoints;
    }


    public int getAdjacentPoints() {
        return adjacentPoints;
    }

    public String getNickname() {
        return nickname;
    }
}
