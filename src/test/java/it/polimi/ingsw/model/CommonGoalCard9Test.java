package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard9Test {

    @Test
    @DisplayName("Check for CommonGoal9")
    void checkGoal() {
        CommonGoalCard9 commonGoalCard9 = new CommonGoalCard9();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        //Set the shelves where you want to insert the tiles
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.CAT, tileID);
        assertEquals(true, commonGoalCard9.checkGoal(bookshelf.getMatrix()));
    }
}