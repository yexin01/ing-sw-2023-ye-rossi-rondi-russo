package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard10Test {

    @Test
    @DisplayName("Check for CommonGoal10")
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
        assertEquals(true, commonGoalCard10.checkGoal(bookshelf.getMatrix()));
    }
}