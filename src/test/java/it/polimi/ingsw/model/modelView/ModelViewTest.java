package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.json.GameRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ModelViewTest {

    @Test
    void winnerEndGame() throws Exception {
        ModelView modelView = new ModelView(2, new GameRules());
        modelView.setPlayerPoints(new PlayerPointsView(new int[] {4, 16}, 2, "player1"), 0);
        modelView.setPlayerPoints(new PlayerPointsView(new int[] {4, 4}, 2, "player2"), 1);
        modelView.setPersonalPoints(new int[] {0, 12});
        modelView.setBookshelfFullPoints("player2");
        modelView.winnerEndGame();
        assertEquals("player2", modelView.getPlayerPoints()[1].getNickname());
        assertEquals(12, modelView.getPersonalPoints()[1]);
    }

    @Test
    void checkWinner() throws Exception {
        ModelView modelView = new ModelView(2, new GameRules());
        modelView.setPlayerPoints(new PlayerPointsView(new int[] {4, 8}, 2, "player1"), 0);
        modelView.setPlayerPoints(new PlayerPointsView(new int[] {4, 4}, 2, "player2"), 1);
        assertEquals("player1", modelView.checkWinner()[1].getNickname());
    }
}