package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard2Test {

    @Test
    @DisplayName("Check for CommonGoal2")
    void checkGoal() {
        CommonGoalCard2567 commonGoalCard2567 = new CommonGoalCard2567();
        ItemTile[][] mat = new ItemTile[6][5];
        //Set the two columns with six different types of tiles
        int x = 0; int y = 2; int tileID = 0;
        for (int i = 0; i < 6; i++) {
            mat[i][x] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        for (int i = 0; i < 6; i++) {
            mat[i][y] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        int [] settings = new int[3];
        commonGoalCard2567.settingsCase(settings);
        assertEquals(true, commonGoalCard2567.checkGoal(mat));
    }
}