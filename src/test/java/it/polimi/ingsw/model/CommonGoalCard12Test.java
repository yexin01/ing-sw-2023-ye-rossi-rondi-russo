package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard12Test {

    @Test
    @DisplayName("Generic true check for CommonGoal12")
    void checkGoal() {
        CommonGoalCard12 commonGoalCard12 = new CommonGoalCard12();
        Bookshelf bookshelf = new Bookshelf(6, 5, 3);
        int tileID = 0;
        //Set right to '0' if you want to start from left, to '1' if you want to start from right
        //(Always set on increasing height, since increasing from left is decreasing from right and vice versa)
        int right = 0;
        if (right == 0) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (j <= i) {
                        bookshelf.getMatrix()[4-j][4-i] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 4; j >= 0; j--) {
                    if (j >= 4 - i) {
                        bookshelf.getMatrix()[i][j] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        }
        assertTrue(commonGoalCard12.checkGoal(bookshelf.getMatrix()));
        }

    @Test
    @DisplayName("Corner case: start from right")
    void checkGoalCC1() {
        CommonGoalCard12 commonGoalCard12 = new CommonGoalCard12();
        Bookshelf bookshelf = new Bookshelf(6, 5, 3);
        int tileID = 0;
        int right = 1;
        if (right == 0) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (j <= i) {
                        bookshelf.getMatrix()[4-j][4-i] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 4; j >= 0; j--) {
                    if (j >= 4 - i) {
                        bookshelf.getMatrix()[i][j] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        }
        assertTrue(commonGoalCard12.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: upper right tile missing")
    void checkGoalCC2() {
        CommonGoalCard12 commonGoalCard12 = new CommonGoalCard12();
        Bookshelf bookshelf = new Bookshelf(6, 5, 3);
        int tileID = 0;
        int right = 0;
        if (right == 0) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (j <= i) {
                        bookshelf.getMatrix()[4-j][4-i] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 4; j >= 0; j--) {
                    if (j >= 4 - i) {
                        bookshelf.getMatrix()[i][j] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        }
        bookshelf.getMatrix()[1][4].setTileID(-1);
        assertFalse(commonGoalCard12.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: starting lower left tile missing")
    void checkGoalCC3() {
        CommonGoalCard12 commonGoalCard12 = new CommonGoalCard12();
        Bookshelf bookshelf = new Bookshelf(6, 5, 3);
        int tileID = 0;
        int right = 0;
        if (right == 0) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (j <= i) {
                        bookshelf.getMatrix()[4-j][4-i] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                for (int j = 4; j >= 0; j--) {
                    if (j >= 4 - i) {
                        bookshelf.getMatrix()[i][j] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        }
        bookshelf.getMatrix()[5][0].setTileID(-1);
        assertFalse(commonGoalCard12.checkGoal(bookshelf.getMatrix()));
    }
}
