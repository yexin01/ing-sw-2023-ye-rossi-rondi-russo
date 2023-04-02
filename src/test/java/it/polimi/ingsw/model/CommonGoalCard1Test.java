package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonGoalCard1Test {

    @Test
    @DisplayName("Check for CommonGoal1")
    void checkGoal() {
        CommonGoalCard1 commonGoalCard1 = new CommonGoalCard1();
        ItemTile[][] mat = new ItemTile[6][5];
        //Set the lower left coordinate of matrix in which you want to create one square
        int x = 0; int y = 0; int tileID = 0;
        mat[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[x+1][y] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[x][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[x+1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        //Set the other lower left coordinate of matrix in which you want to create one square
        x = 3; y = 3;
        mat[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[x+1][y] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[x][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        mat[x+1][y+1] = new ItemTile(Type.CAT, tileID);

        Assertions.assertEquals(true, commonGoalCard1.checkGoal(1,mat));
    }
}