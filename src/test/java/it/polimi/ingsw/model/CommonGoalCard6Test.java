package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard6Test {

    @Test
    @DisplayName("Check for CommonGoal6")
    void checkGoal() {
        CommonGoalCard_2_5_6_7 commonGoalCard2567 = new CommonGoalCard_2_5_6_7();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the rows in which you want the check to be verified
        int x = 5; int y = 2; int tileId = 0;
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        for (int i=0; i<5; i++) {
            bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileId);
            tileId++;
        }
        int [] settings = new int[3];
        commonGoalCard2567.settingsCase(settings);
        assertEquals(true, commonGoalCard2567.checkGoal(bookshelf.getMatrix()));
    }
}