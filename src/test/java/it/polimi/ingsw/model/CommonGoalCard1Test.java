package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonGoalCard1Test {

    @Test
    @DisplayName("Generic true check for CommonGoal1")
    void checkGoal() {
        CommonGoalCard1 commonGoalCard1 = new CommonGoalCard1();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        //Set the lower left coordinate of matrix in which you want to create one square
        int x = 5; int y = 0; int tileID = 0;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        //Set the other lower left coordinate of matrix in which you want to create one square
        x = 5; y = 3;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID);
        Assertions.assertEquals(true, commonGoalCard1.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 2 adjacent squares, one above the other one")
    void checkGoalCC1() {
        CommonGoalCard1 commonGoalCard1 = new CommonGoalCard1();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.BOOK, tileID);
        Assertions.assertFalse(commonGoalCard1.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 2 adjacent squares, one next to the other one")
    void checkGoalCC2() {
        CommonGoalCard1 commonGoalCard1 = new CommonGoalCard1();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.CAT, tileID);
        Assertions.assertFalse(commonGoalCard1.checkGoal(bookshelf.getMatrix()));
    }

    @Test
    @DisplayName("Corner case: 2 squares in the two opposite corners")
    void checkGoalCC3() {
        CommonGoalCard1 commonGoalCard1 = new CommonGoalCard1();
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.matrix(6,5);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][3] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][3] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[1][4] = new ItemTile(Type.TROPHY, tileID);
        bookshelf.getMatrix()[2][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[2][4] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[3][4] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][4] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.BOOK, tileID); tileID++;

        Assertions.assertTrue(commonGoalCard1.checkGoal(bookshelf.getMatrix()));
    }
}