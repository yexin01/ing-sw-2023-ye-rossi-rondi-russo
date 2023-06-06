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
     * Constructs a PlayerPointsView.
     * @param commonGoalPoints An array with the scores of common goals, where the position indicates the score relative to that common goal.
     * @param adjacentPoints The score of adjacent tiles.
     * @param nickname The nickname of the player.
     */
    public PlayerPointsView(int[] commonGoalPoints, int adjacentPoints, String nickname) {
        this.commonGoalPoints = commonGoalPoints;
        this.adjacentPoints = adjacentPoints;
        this.nickname = nickname;
    }

    /**
     * Gets the sum of the common goal points and adjacent points.
     * @return The total points of the player(without personal points).
     */
    public int getPoints() {
        int sum = 0;
        for (int n : commonGoalPoints) {
            sum += n;
        }
        return adjacentPoints + sum;
    }

    /**
     * Gets the array of common goal points.
     * @return The array of common goal points.
     */
    public int[] getPointsToken() {
        return commonGoalPoints;
    }

    /**
     * Gets the score of adjacent tiles.
     * @return The score of adjacent tiles.
     */
    public int getAdjacentPoints() {
        return adjacentPoints;
    }

    /**
     * Gets the nickname of the player.
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }
}
