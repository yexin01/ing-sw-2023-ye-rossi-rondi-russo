package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.Error;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {

    @Test
    @DisplayName("computeFreeShelves: generic check with one tile for every column")
    void computeFreeShelves() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        // Insert the number of tiles you want for every column
        int column0 = 1; int column1 = 1; int column2 = 1; int column3 = 1; int column4 = 1; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        int[] array = new int[5];
        array[0] = 6-column0; array[1] = 6-column1; array[2] = 6-column2; array[3] = 6-column3; array[4] = 6-column4;
        bookshelf.computeFreeShelves();
        assertArrayEquals(array, bookshelf.getFreeShelves());
    }

    @Test
    @DisplayName("computeFreeShelves: blank bookshelf")
    void computeFreeShelvesCC1() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 0; int column1 = 0; int column2 = 0; int column3 = 0; int column4 = 0; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        int[] array = new int[5];
        array[0] = 6-column0; array[1] = 6-column1; array[2] = 6-column2; array[3] = 6-column3; array[4] = 6-column4;
        bookshelf.computeFreeShelves();
        assertArrayEquals(array, bookshelf.getFreeShelves());
    }

    @Test
    @DisplayName("computeFreeShelves: full bookshelf")
    void computeFreeShelvesCC2() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 6; int column1 = 6; int column2 = 6; int column3 = 6; int column4 = 6; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        int[] array = new int[5];
        array[0] = 6-column0; array[1] = 6-column1; array[2] = 6-column2; array[3] = 6-column3; array[4] = 6-column4;
        bookshelf.computeFreeShelves();
        assertArrayEquals(array, bookshelf.getFreeShelves());
    }

    @Test
    @DisplayName("maxFreeShelves: generic check with one tile for every column")
    void maxFreeShelves() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        // Insert the number of tiles you want for every column
        int column0 = 1; int column1 = 1; int column2 = 1; int column3 = 1; int column4 = 1; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        //Set inside expected the expected value
        //assertEquals(5, bookshelf.maxFreeShelves());
    }

    @Test
    @DisplayName("numSelectableTiles: generic check with one tile for every column")
    void numSelectableTiles() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        // Insert the number of tiles you want for every column
        int column0 = 1; int column1 = 1; int column2 = 1; int column3 = 1; int column4 = 1; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        //Set inside expected the expected value
        assertEquals(3, bookshelf.numSelectableTiles());
    }

    @Test
    @DisplayName("numSelectableTiles: blank bookshelf")
    void numSelectableTilesCC1() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 0; int column1 = 0; int column2 = 0; int column3 = 0; int column4 = 0; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        assertEquals(3, bookshelf.numSelectableTiles());
    }

    @Test
    @DisplayName("numSelectableTiles: 1 free column")
    void numSelectableTilesCC2() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 0; int column1 = 6; int column2 = 6; int column3 = 6; int column4 = 6; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        assertEquals(3, bookshelf.numSelectableTiles());
    }

    @Test
    @DisplayName("numSelectableTiles: only one free box")
    void numSelectableTilesCC3() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 5; int column1 = 6; int column2 = 6; int column3 = 6; int column4 = 6; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        bookshelf.computeFreeShelves();
        assertEquals(1, bookshelf.numSelectableTiles());
    }

    @Test
    @DisplayName("isFull: generic true check")
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
    @DisplayName("isFull: 1 free box")
    void isFullCC1() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 5; int column1 = 6; int column2 = 6; int column3 = 6; int column4 = 6; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertFalse(bookshelf.isFull());
    }

    @Test
    @DisplayName("isFull: 1 free column")
    void isFullCC2() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 0; int column1 = 6; int column2 = 6; int column3 = 6; int column4 = 6; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertFalse(bookshelf.isFull());
    }

    @Test
    @DisplayName("isFull: blank bookshelf")
    void isFullCC3() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int column0 = 0; int column1 = 0; int column2 = 0; int column3 = 0; int column4 = 0; int tileID = 0;
        for (int j = 0; j<column0; j++) {
            bookshelf.getMatrix()[5-j][0] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column1; j++) {
            bookshelf.getMatrix()[5-j][1] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column2; j++) {
            bookshelf.getMatrix()[5-j][2] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column3; j++) {
            bookshelf.getMatrix()[5-j][3] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        for (int j = 0; j<column4; j++) {
            bookshelf.getMatrix()[5-j][4] = new ItemTile(Type.CAT, tileID);
            tileID++;
        }
        assertFalse(bookshelf.isFull());
    }

    @Test
    @DisplayName("insertAsSelected: Generic check for blank bookshelf")
    void insertAsSelected() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Blank bookshelf set, create and insert tiles as you want
        bookshelf.computeFreeShelves();
        //Set the size of selectedTiles
        int size = 3; int tileID = 0;
        //Set the column you want to insert the tiles
        bookshelf.setColumnSelected(0);
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        for (int i = 0; i<size; i++) {
            selectedTiles.add(new ItemTile(Type.CAT, tileID));
            tileID++;
        }
        bookshelf.insertAsSelected(selectedTiles);
        assertEquals(0, bookshelf.getMatrix()[5][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[5][0].getType());
        assertEquals(1, bookshelf.getMatrix()[4][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[4][0].getType());
        assertEquals(2, bookshelf.getMatrix()[3][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[3][0].getType());
    }

    @Test
    @DisplayName("insertAsSelected: Checking insert of 1 tile in a blank bookshelf")
    void insertAsSelectedCC1() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        bookshelf.computeFreeShelves();
        bookshelf.setColumnSelected(0);
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        int size = 1; int tileID = 0;
        for (int i = 0; i<size; i++) {
            selectedTiles.add(new ItemTile(Type.CAT, tileID));
            tileID++;
        }
        bookshelf.insertAsSelected(selectedTiles);
        assertEquals(0, bookshelf.getMatrix()[5][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[5][0].getType());
    }

    @Test
    @DisplayName("insertAsSelected: Checking insert of 3 tiles in a bookshelf column with 3 free shelves")
    void insertAsSelectedCC2() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, 0);
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, 1);
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, 2);
        bookshelf.computeFreeShelves();
        bookshelf.setColumnSelected(0);
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        int size = 3; int tileID = 3;
        for (int i = 0; i<size; i++) {
            selectedTiles.add(new ItemTile(Type.CAT, tileID));
            tileID++;
        }
        bookshelf.insertAsSelected(selectedTiles);
        assertEquals(3, bookshelf.getMatrix()[2][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[2][0].getType());
        assertEquals(4, bookshelf.getMatrix()[1][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[1][0].getType());
        assertEquals(5, bookshelf.getMatrix()[0][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[0][0].getType());
    }

    @Test
    @DisplayName("insertAsSelected: Checking insert of 1 tile in a bookshelf column with 1 free shelve")
    void insertAsSelectedCC3() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, 0);
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, 1);
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, 2);
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, 3);
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, 4);
        bookshelf.computeFreeShelves();
        bookshelf.setColumnSelected(0);
        ArrayList<ItemTile> selectedTiles = new ArrayList<>();
        int size = 1; int tileID = 5;
        for (int i = 0; i<size; i++) {
            selectedTiles.add(new ItemTile(Type.CAT, tileID));
            tileID++;
        }
        bookshelf.insertAsSelected(selectedTiles);
        assertEquals(5, bookshelf.getMatrix()[0][0].getTileID()); assertEquals(Type.CAT, bookshelf.getMatrix()[0][0].getType());
    }

    @Test
    @DisplayName("checkBookshelf: column < 0")
    void checkBookshelf() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        //Blank bookshelf set, create and insert tiles as you want
        bookshelf.computeFreeShelves();
        //Set the size of selectedTiles
        int size = 1;
        //Set the column you want to check
        int column = -1;
        assertThrows(Error.class, () -> bookshelf.checkBookshelf(column, size));
    }

    @Test
    @DisplayName("checkBookshelf: column > 4")
    void checkBookshelfCC1() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        bookshelf.computeFreeShelves();
        int size = 3;
        int column = 5;
        assertThrows(Error.class, ()->bookshelf.checkBookshelf(column, size));
    }

    @Test
    @DisplayName("checkBookshelf: numSelectedTiles > getMaxTilesColumn(column)")
    void checkBookshelfCC2() throws Error {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID);
        bookshelf.computeFreeShelves();
        int size = 2;
        int column = 0;
        assertThrows(Error.class, ()->bookshelf.checkBookshelf(column, size));
    }

    @Test
    @DisplayName("findAdjacentTilesGroups: one type of every tile")
    void findAdjacentTilesGroups() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.FRAME, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.TROPHY, tileID); tileID++;
        bookshelf.getMatrix()[4][4] = new ItemTile(Type.PLANT, tileID);
        List<Integer> groupsSizes = new ArrayList<>();
        groupsSizes.add(0, 1);
        groupsSizes.add(1, 1);
        groupsSizes.add(2, 1);
        groupsSizes.add(3, 1);
        groupsSizes.add(4, 1);
        groupsSizes.add(5, 1);
        assertIterableEquals(groupsSizes, bookshelf.findAdjacentTilesGroups());
    }

    @Test
    @DisplayName("findAdjacentTilesGroups: 5 CAT tiles in the same column")
    void findAdjacentTilesGroupsCC1() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.CAT, tileID);
        List<Integer> groupsSizes = new ArrayList<>();
        groupsSizes.add(0, 5);
        assertIterableEquals(groupsSizes, bookshelf.findAdjacentTilesGroups());
    }

    @Test
    @DisplayName("findAdjacentTilesGroups: 5 CAT tiles in the same row")
    void findAdjacentTilesGroupsCC2() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID);
        List<Integer> groupsSizes = new ArrayList<>();
        groupsSizes.add(0, 5);
        assertIterableEquals(groupsSizes, bookshelf.findAdjacentTilesGroups());
    }

    @Test
    @DisplayName("findAdjacentTilesGroups: chessboard pattern")
    void findAdjacentTilesGroupsCC3() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.CAT, tileID);
        List<Integer> groupsSizes = new ArrayList<>();
        groupsSizes.add(0, 1);
        groupsSizes.add(1, 1);
        groupsSizes.add(2, 1);
        groupsSizes.add(3, 1);
        assertIterableEquals(groupsSizes, bookshelf.findAdjacentTilesGroups());
    }

    @Test
    @DisplayName("findAdjacentTilesGroups: full bookshelf with casual pattern")
    void findAdjacentTilesGroupsCC4() {
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        int tileID = 0;
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[5][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][2] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[5][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[4][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][1] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[4][2] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[4][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[4][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][0] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[3][1] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[3][2] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[3][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[3][4] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][0] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[2][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[2][2] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[2][3] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[2][4] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[1][0] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[1][1] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[1][2] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[1][3] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[1][4] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[0][0] = new ItemTile(Type.CAT, tileID); tileID++;
        bookshelf.getMatrix()[0][1] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[0][2] = new ItemTile(Type.BOOK, tileID); tileID++;
        bookshelf.getMatrix()[0][3] = new ItemTile(Type.GAME, tileID); tileID++;
        bookshelf.getMatrix()[0][4] = new ItemTile(Type.CAT, tileID);
        List<Integer> groupsSizes = new ArrayList<>();
        groupsSizes.add(0, 1);
        groupsSizes.add(1, 1);
        groupsSizes.add(2, 1);
        groupsSizes.add(3, 8);
        groupsSizes.add(4, 1);
        groupsSizes.add(5, 1);
        groupsSizes.add(6, 5);
        groupsSizes.add(7, 1);
        groupsSizes.add(8, 1);
        groupsSizes.add(9, 1);
        groupsSizes.add(10, 5);
        groupsSizes.add(11, 3);
        groupsSizes.add(12, 1);
        assertIterableEquals(groupsSizes, bookshelf.findAdjacentTilesGroups());
    }
}