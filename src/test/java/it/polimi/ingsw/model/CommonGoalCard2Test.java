package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard2Test {

    @Test
    @DisplayName("Check for CommonGoal2")
    void checkGoal() {
        CommonGoalCard_2_5_6_7 commonGoalCard2567 = new CommonGoalCard_2_5_6_7();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
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
        int [] settings = new int[3];
        commonGoalCard2567.settingsCase(settings);
        assertEquals(true, commonGoalCard2567.checkGoal(bookshelf.getMatrix()));
    }
}