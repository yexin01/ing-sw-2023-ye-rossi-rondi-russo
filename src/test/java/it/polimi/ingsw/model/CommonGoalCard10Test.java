package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard10Test {

    @Test
    @DisplayName("Generic true check for CommonGoal10")
    void checkGoal() {
        CommonGoalCard10 commonGoalCard10 = new CommonGoalCard10();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the center tile coordinate of the 'X'
        int x = 2; int y = 2; int tileID = 0;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y+1] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard10.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: X is on a bookshelf corner")
    void checkGoalCC1() {
        CommonGoalCard10 commonGoalCard10 = new CommonGoalCard10();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 4; int y = 1; int tileID = 0;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y+1] = new ItemTile(Type.CAT, tileID);
        assertTrue(commonGoalCard10.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: one different tile type")
    void checkGoalCC2() {
        CommonGoalCard10 commonGoalCard10 = new CommonGoalCard10();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 4; int y = 2; int tileID = 0;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y+1] = new ItemTile(Type.CAT, tileID);
        assertFalse(commonGoalCard10.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: one tile in a wrong position")
    void checkGoalCC3() {
        CommonGoalCard10 commonGoalCard10 = new CommonGoalCard10();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 4; int y = 2; int tileID = 0;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+1][y-1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x+2][y+2] = new ItemTile(Type.CAT, tileID);
        assertFalse(commonGoalCard10.checkGoal(bookshelf.getMatrix()));
    }
}