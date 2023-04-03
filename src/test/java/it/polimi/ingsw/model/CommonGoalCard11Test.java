package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard11Test {

    @Test
    @DisplayName("Check for CommonGoal11")
    void checkGoal() {
        CommonGoalCard11 commonGoalCard11 = new CommonGoalCard11();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the lower left start of the diagonal
        int x = 5; int y = 0; int tileID = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x-i][y+i] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertEquals(true, commonGoalCard11.checkGoal(bookshelf.getMatrix()));
    }
}