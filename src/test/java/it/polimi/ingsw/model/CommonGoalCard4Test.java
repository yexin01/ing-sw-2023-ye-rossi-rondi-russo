package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard4Test {

    @Test
    @DisplayName("Generic true check for CommonGoal4")
    void checkGoal() {
        CommonGoalCard4 commonGoalCard4 = new CommonGoalCard4();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Insert the tiles you want inside the matrix
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[3][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][4] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[1][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][4] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard4.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: some adjacent groups")
    void checkGoalCC1() {
        CommonGoalCard4 commonGoalCard4 = new CommonGoalCard4();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[3][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.CAT, tileID); tileID++;

        bookshelf.getMatrix()[1][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][3] = new ItemTile(Type.CAT, tileID);
        assertFalse(commonGoalCard4.checkGoal(bookshelf.getMatrix()));
    }
}