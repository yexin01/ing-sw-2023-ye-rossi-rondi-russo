package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard3Test {

    @Test
    @DisplayName("Check for CommonGoal3")
    void checkGoal() {
        CommonGoalCard3 commonGoalCard3 = new CommonGoalCard3();
        ItemTile[][] mat = new ItemTile[6][5];
        //Insert the tiles you want inside the matrix
        int tileID = 0;
        mat [0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [0][1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [0][2] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [0][3] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [2][1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [2][2] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [2][3] = new ItemTile(Type.CAT, tileID); tileID++;
        assertEquals(true, commonGoalCard3.checkGoal(3, mat));
    }
}