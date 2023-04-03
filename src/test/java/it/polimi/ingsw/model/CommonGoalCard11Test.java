package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard11Test {

    @Test
    @DisplayName("Check for CommonGoal11")
    void checkGoal() {
        CommonGoalCard11 commonGoalCard11 = new CommonGoalCard11();
        ItemTile[][] mat = new ItemTile[6][5];
        //Set the lower left start of the diagonal
        int x = 0; int y = 0; int tileID = 0;
        for (int i=0; i<5; i++) {
            mat[x+i][y+i] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertEquals(true, commonGoalCard11.checkGoal(mat));
    }
}