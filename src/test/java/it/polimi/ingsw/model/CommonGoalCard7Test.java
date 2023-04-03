package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard7Test {

    @Test
    @DisplayName("Check for CommonGoal7")
    void checkGoal() {
        CommonGoalCard_2_5_6_7 commonGoalCard2567 = new CommonGoalCard_2_5_6_7();
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Set the rows where you want to add the tiles, and the number of different types
        int x = 5;
        int y = 4;
        int z = 3;
        int w = 2;
        int nTypes = 3;
        int tileID = 0;
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[x][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[y][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[z][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        for (int i = 0; i < 5; i++) {
            if (i > nTypes - 1) {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[nTypes - 1], tileID);
            } else {
                bookshelf.getMatrix()[w][i] = new ItemTile(Type.values()[i], tileID);
            }
            tileID++;
        }
        int [] settings = new int[3];
        commonGoalCard2567.settingsCase(settings);
        assertEquals(true, commonGoalCard2567.checkGoal(bookshelf.getMatrix()));
    }
}