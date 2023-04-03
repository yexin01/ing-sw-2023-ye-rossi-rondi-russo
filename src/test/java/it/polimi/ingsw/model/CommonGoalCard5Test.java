package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard5Test {

    @Test
    @DisplayName("Check for CommonGoal5")
    void checkGoal() {
        CommonGoalCard_2_5_6_7 commonGoalCard2567 = new CommonGoalCard_2_5_6_7();
        ItemTile[][] mat = new ItemTile[6][5];
        //Set the columns where you want to add the tiles, and the number of different types
        int x = 0; int y = 1; int nTypes = 3; int tileID = 0;
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                mat[i][x] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                mat[i][x] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i=0; i<6; i++) {
            if (i>nTypes-1) {
                mat[i][y] = new ItemTile(Type.values()[nTypes-1], tileID);
            } else {
                mat[i][y] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        int [] settings = new int[3];
        commonGoalCard2567.settingsCase(settings);
        assertEquals(true, commonGoalCard2567.checkGoal(mat));
    }
}