package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard5Test {

    @Test
    @DisplayName("Generic true check for CommonGoal5")
    void checkGoal() {
        CommonGoalCard5 commonGoalCard5 = new CommonGoalCard5();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        //Set the columns where you want to add the tiles, and the number of different types
        int x = 0; int y = 1; int z = 2; int nTypes = 3; int tileID = 0;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 3;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 3;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][z] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][z] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertTrue(commonGoalCard5.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: only one type for each column")
    void checkGoalCC1() {
        CommonGoalCard5 commonGoalCard5 = new CommonGoalCard5();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 0; int y = 1; int z = 2; int nTypes = 1; int tileID = 0;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 1;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 1;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][z] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][z] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertTrue(commonGoalCard5.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: only two columns")
    void checkGoalCC2() {
        CommonGoalCard5 commonGoalCard5 = new CommonGoalCard5();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 0; int y = 1; int nTypes = 2; int tileID = 0;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 2;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertFalse(commonGoalCard5.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 4 types columns")
    void checkGoalCC3() {
        CommonGoalCard5 commonGoalCard5 = new CommonGoalCard5();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 0; int y = 1; int nTypes = 4; int tileID = 0;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 4;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertFalse(commonGoalCard5.checkGoal(bookshelf.getMatrix()));
    }
}