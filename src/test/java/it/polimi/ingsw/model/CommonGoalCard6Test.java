package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard6Test {

    @Test
    @DisplayName("Generic true check for CommonGoal6")
    void checkGoal() {
        CommonGoalCard6 commonGoalCard6 = new CommonGoalCard6();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        //Set the rows in which you want the check to be verified
        int x = 5; int y = 2; int tileId = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        assertTrue(commonGoalCard6.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 2 adjacent rows")
    void checkGoalCC1() {
        CommonGoalCard6 commonGoalCard6 = new CommonGoalCard6();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 5; int y = 4; int tileId = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        assertTrue(commonGoalCard6.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 3 rows")
    void checkGoalCC2() {
        CommonGoalCard6 commonGoalCard6 = new CommonGoalCard6();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 5; int y = 4; int z = 3; int tileId = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        assertTrue(commonGoalCard6.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: only 4 types for each row")
    void checkGoalCC3() {
        CommonGoalCard6 commonGoalCard6 = new CommonGoalCard6();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 5; int y = 4; int tileId = 0;
        for (int i=0; i<5; i++) {
            if (i == 4) {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i-1], tileId);
            } else {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileId);}
            tileId++;
        }
        for (int i=0; i<5; i++) {
            if (i == 4) {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i-1], tileId);
            } else {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileId);}
            tileId++;
        }
        assertFalse(commonGoalCard6.checkGoal(bookshelf.getMatrix()));
    }
}