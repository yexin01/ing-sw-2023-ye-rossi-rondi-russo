package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard3Test {

    @Test
    @DisplayName("Generic false check for CommonGoal3")
    void checkGoal() {
        CommonGoalCard3 commonGoalCard3 = new CommonGoalCard3();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
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
        assertFalse(commonGoalCard3.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: L-shape groups")
    void checkGoalCC1() {
        CommonGoalCard3 commonGoalCard3 = new CommonGoalCard3();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[0][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[0][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.TROPHY, tileID); tileID++;

        bookshelf.getMatrix()[0][3] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[1][3] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[2][3] = new ItemTile(Type.PLANT, tileID); tileID++;

        bookshelf.getMatrix()[3][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][4] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard3.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: S-shape groups")
    void checkGoalCC2() {
        CommonGoalCard3 commonGoalCard3 = new CommonGoalCard3();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[3][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.TROPHY, tileID); tileID++;

        bookshelf.getMatrix()[0][2] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[0][3] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[1][3] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[1][4] = new ItemTile(Type.PLANT, tileID); tileID++;

        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard3.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: only 3 groups")
    void checkGoalCC3() {
        CommonGoalCard3 commonGoalCard3 = new CommonGoalCard3();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[3][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.TROPHY, tileID); tileID++;

        bookshelf.getMatrix()[5][4] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[4][4] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[3][4] = new ItemTile(Type.PLANT, tileID); tileID++;
        bookshelf.getMatrix()[2][4] = new ItemTile(Type.PLANT, tileID);
        assertFalse(commonGoalCard3.checkGoal(bookshelf.getMatrix()));
    }
}