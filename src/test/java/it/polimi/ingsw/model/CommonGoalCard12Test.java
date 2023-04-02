package it.polimi.ingsw.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonGoalCard12Test {

    @Test
    @DisplayName("Check for CommonGoal12")
    void checkGoal() {
        CommonGoalCard12 commonGoalCard12 = new CommonGoalCard12();
        ItemTile[][] mat = new ItemTile[6][5];
        int tileID = 0;
        //Set right to '0' if you want to start from left, to '1' if you want to start right
        //(Always set on increasing height, since increasing from left is decreasing from right and vice versa)
        int right = 0;
        if (right==0) {
            for (int i=0; i<5; i++) {
                for (int j=0; j<5; j++) {
                    if (j<=i) {
                        mat[j][i] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        } else {
            for (int i=0; i<5; i++) {
                for (int j=4; j>=0; j--) {
                    if (j>=4-i) {
                        mat[j][i] = new ItemTile(Type.CAT, tileID++);
                        tileID++;
                    }
                }
            }
        }
        assertEquals(true, commonGoalCard12.checkGoal(12, mat));
    }
}