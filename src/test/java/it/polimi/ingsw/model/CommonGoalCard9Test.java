package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard9Test {

    @Test
    @DisplayName("Check for CommonGoal9")
    void checkGoal() {
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        ItemTile[][] mat = new ItemTile[6][5];
        int tileID = 0;
        //Set the shelves where you want to insert the tiles
        mat[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[0][1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[0][2] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[0][3] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[0][4] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[1][1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[1][2] = new ItemTile(Type.CAT, tileID);

        assertEquals(true, commonGoalCard9.checkGoal(9, mat));
    }
}