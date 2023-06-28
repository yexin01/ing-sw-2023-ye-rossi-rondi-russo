package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard2Test {

    @Test
    @DisplayName("Generic true check for CommonGoal2")
    void checkGoal() {
        CommonGoalCard2 commonGoalCard2 = new CommonGoalCard2();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        //Set the two columns with six different types of tiles
        int x = 0; int y = 2; int tileID = 0;
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        assertTrue(commonGoalCard2.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 2 adjacent columns")
    void checkGoalCC1() {
        CommonGoalCard2 commonGoalCard2 = new CommonGoalCard2();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 0; int y = 1; int tileID = 0;
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        assertTrue(commonGoalCard2.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 3 columns")
    void checkGoalCC2() {
        CommonGoalCard2 commonGoalCard2 = new CommonGoalCard2();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 0; int y = 1; int z = 4; int tileID = 0;
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][z] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        assertTrue(commonGoalCard2.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: one of the two columns only has 5 types")
    void checkGoalCC3() {
        CommonGoalCard2 commonGoalCard2 = new CommonGoalCard2();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int x = 0; int y = 1; int tileID = 0;
        for (int i = 0; i < 6; i++) {
            bookshelf.getMatrix()[i][x] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            bookshelf.getMatrix()[i][y] = new ItemTile(Type.values()[i], tileID);
            tileID++;
        }
        bookshelf.getMatrix()[5][y] = new ItemTile(Type.CAT, tileID);
        assertFalse(commonGoalCard2.checkGoal(bookshelf.getMatrix()));
    }
}