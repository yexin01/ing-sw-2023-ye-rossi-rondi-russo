package it.polimi.ingsw.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {

    @Test
    void computeFreeShelves() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        // Insert the number of tiles you want for every column
        int column0 = 1; int column1 = 1; int column2 = 1; int column3 = 1; int column4 = 1; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        int[] array = new int[5];
        array[0] = 6-column0; array[1] = 6-column1; array[2] = 6-column2; array[3] = 6-column3; array[4] = 6-column4;
        bookshelf.computeFreeShelves();
        assertArrayEquals(array, bookshelf.getFreeShelves());
    }

    @Test
    void maxFreeShelves() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        // Insert the number of tiles you want for every column
        int column0 = 1; int column1 = 1; int column2 = 1; int column3 = 1; int column4 = 1; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        //Set inside expected the expected value
        assertEquals(4, bookshelf.maxFreeShelves());
    }

    @Test
    void numSelectableTiles() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        // Insert the number of tiles you want for every column
        int column0 = 1; int column1 = 1; int column2 = 1; int column3 = 1; int column4 = 1; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        //Set inside expected the expected value
        assertEquals(3, bookshelf.numSelectableTiles());
    }

    @Test
    void isFull() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        //Full bookshelf declared
        for (int j=0; j<6; j++){
            for (int i=0; i<5; i++){
                bookshelf.getMatrix()[j][i] = new ItemTile(Type.CAT, tileID);
                tileID++;
            }
        }
        assertTrue(bookshelf.isFull());
    }

    @Test
    void findAdjacentTilesGroups() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        //Fill the bookshelf as you want
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[0][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[0][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.TROPHY, tileID);
        List<Integer> groupsSizes = new ArrayList<>();
        //Insert inside element the expected number of adjacent tiles for every type of tile
        groupsSizes.add(0, 1);
        groupsSizes.add(1, 1);
        groupsSizes.add(2, 1);
        groupsSizes.add(3, 1);
        groupsSizes.add(4, 1);
        groupsSizes.add(5, 0);
        assertIterableEquals(groupsSizes, bookshelf.findAdjacentTilesGroups());
    }
}