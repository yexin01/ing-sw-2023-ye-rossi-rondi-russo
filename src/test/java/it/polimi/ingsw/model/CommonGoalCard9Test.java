package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard9Test {

    @Test
    @DisplayName("Generic true check for CommonGoal9")
    void checkGoal() {
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        //Set the shelves where you want to insert the tiles
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard9.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 8 adjacent tiles")
    void checkGoalCC1() {
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard9.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 8 tiles separated from each other")
    void checkGoalCC2() {
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][4] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard9.checkGoal(bookshelf.getMatrix()));
    }
}