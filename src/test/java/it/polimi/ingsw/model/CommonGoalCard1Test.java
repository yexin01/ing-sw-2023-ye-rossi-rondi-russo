package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CommonGoalCard1Test {

    @Test
    @DisplayName("Check for CommonGoal1")
    void checkGoal() {
        CommonGoalCard1 commonGoalCard1 = new CommonGoalCard1();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the lower left coordinate of matrix in which you want to create one square
        int x = 5; int y = 1; int tileID = 0;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        //Set the other lower left coordinate of matrix in which you want to create one square
        x = 1; y = 1;
        bookshelf.getMatrix()[x][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x][y+1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[x-1][y+1] = new ItemTile(Type.CAT, tileID);
        Assertions.assertEquals(true, commonGoalCard1.checkGoal(bookshelf.getMatrix()));
    }
}