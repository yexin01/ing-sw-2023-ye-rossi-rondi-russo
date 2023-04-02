package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard4Test {

    @Test
    @DisplayName("Check for CommonGoal4")
    void checkGoal() {
        CommonGoalCard4 commonGoalCard4 = new CommonGoalCard4();
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
        mat [4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [4][2] = new ItemTile(Type.CAT, tileID); tileID++;
        mat [4][3] = new ItemTile(Type.CAT, tileID); tileID++;
        assertEquals(true, commonGoalCard4.checkGoal(4, mat));
    }
}