package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard3Test {

    @Test
    @DisplayName("Check for CommonGoal3")
    void checkGoal() {
        CommonGoalCard3 commonGoalCard3 = new CommonGoalCard3();
        /*Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Insert the tiles you want inside the matrix
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][3] = new ItemTile(Type.CAT, tileID);
        bookshelf.printBookshelf();*/
        ItemTile[][] matrix1={
                {new ItemTile(null,-1),new ItemTile(null,-1),new ItemTile(null,-1),new ItemTile(null,-1),new ItemTile(null,-1)},
                {new ItemTile(null,-1),new ItemTile(null,-1),new ItemTile(null,-1),new ItemTile(null,-1),new ItemTile(null,-1)},
                {new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(null,-1)},
                {new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(null,-1)},
                {new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(null,-1)},
                {new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(Type.CAT,44),new ItemTile(null,-1)}};
        assertEquals(true, commonGoalCard3.checkGoal(matrix1));
    }
}