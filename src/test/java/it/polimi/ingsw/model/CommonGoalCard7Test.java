package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard7Test {

    @Test
    @DisplayName("Generic true check for CommonGoal7")
    void checkGoal() {
        CommonGoalCard7 commonGoalCard7 = new CommonGoalCard7();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the rows where you want to add the tiles, and the number of different types
        int x = 5;
        int y = 4;
        int z = 3;
        int w = 2;
        int nTypes = 3;
        int tileID = 0;
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertTrue(commonGoalCard7.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: only one type for each line")
    void checkGoalCC1() {
        CommonGoalCard7 commonGoalCard7 = new CommonGoalCard7();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 5;
        int y = 4;
        int z = 3;
        int w = 1;
        int nTypes = 1;
        int tileID = 0;
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertTrue(commonGoalCard7.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 4 types for 2 lines")
    void checkGoalCC2() {
        CommonGoalCard7 commonGoalCard7 = new CommonGoalCard7();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int x = 5;
        int y = 4;
        int z = 3;
        int w = 1;
        int nTypes = 4;
        int tileID = 0;
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        nTypes = 3;
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        assertFalse(commonGoalCard7.checkGoal(bookshelf.getMatrix()));
    }
}