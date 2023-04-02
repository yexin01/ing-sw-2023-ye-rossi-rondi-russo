package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard8Test {

    @Test
    @DisplayName("Check for CommonGoal8")
    void checkGoal() {
        CommonGoalCard8 commonGoalCard8 = new CommonGoalCard8();
        ItemTile[][] mat = new ItemTile[6][5];
        int tileID = 0;
        mat[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[0][4] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[5][4] = new ItemTile(Type.CAT, tileID);
        assertEquals(true, commonGoalCard8.checkGoal(8, mat));
    }
}