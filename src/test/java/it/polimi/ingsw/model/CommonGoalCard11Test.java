package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard11Test {

    @Test
    @DisplayName("Generic true check for CommonGoal11")
    void checkGoal() {
        CommonGoalCard11 commonGoalCard11 = new CommonGoalCard11();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the lower left start of the diagonal
        int x = 5; int y = 0; int tileID = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x-i][y+i] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertTrue(commonGoalCard11.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: starting from lower right tile")
    void checkGoalCC1() {
        CommonGoalCard11 commonGoalCard11 = new CommonGoalCard11();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 5; int y = 4; int tileID = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x-i][y-i] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertTrue(commonGoalCard11.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: only 4 tiles diagonal")
    void checkGoalCC2() {
        CommonGoalCard11 commonGoalCard11 = new CommonGoalCard11();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 5; int y = 1; int tileID = 0;
        for (int i=0; i<4; i++) {
            bookshelf.getMatrix()[x-i][y+i] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertFalse(commonGoalCard11.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: one different tile type")
    void checkGoalCC3() {
        CommonGoalCard11 commonGoalCard11 = new CommonGoalCard11();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 5; int y = 1; int tileID = 0;
        for (int i=0; i<4; i++) {
            if (i == 4) {
                bookshelf.getMatrix()[x-i][y+i] = new ItemTile(Type.TROPHY, tileID);
            } else {
            bookshelf.getMatrix()[x-i][y+i] = new ItemTile(Type.CAT, tileID);}
            tileID++;
        }
        assertFalse(commonGoalCard11.checkGoal(bookshelf.getMatrix()));
    }
}