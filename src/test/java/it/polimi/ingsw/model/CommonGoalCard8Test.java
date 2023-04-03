package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard8Test {

    @Test
    @DisplayName("Check for CommonGoal8")
    void checkGoal() {
        CommonGoalCard8 commonGoalCard8 = new CommonGoalCard8();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID);
        assertEquals(true, commonGoalCard8.checkGoal(bookshelf.getMatrix()));
    }
}