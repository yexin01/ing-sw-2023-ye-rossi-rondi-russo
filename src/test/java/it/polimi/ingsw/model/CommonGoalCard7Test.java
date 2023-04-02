package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard7Test {

    @Test
    @DisplayName("Check for CommonGoal7")
    void checkGoal() {
        CommonGoalCard2567 commonGoalCard2567 = new CommonGoalCard2567();
        ItemTile[][] mat = new ItemTile[6][5];
        //Set the rows where you want to add the tiles, and the number of different types
        int x = 0;
        int y = 1;
        int z = 2;
        int w = 3;
        int nTypes = 3;
        int tileID = 0;
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                mat[x][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                mat[x][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                mat[y][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                mat[y][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                mat[z][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                mat[z][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                mat[w][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                mat[w][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        int [] settings = new int[3];
        commonGoalCard2567.settingsCase(settings);
        assertEquals(true, commonGoalCard2567.checkGoal(mat));
    }
}