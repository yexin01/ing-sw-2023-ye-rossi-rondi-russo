package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.ModelView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class PlayerTest {

    /**
     * Creates an arrayList of BoardBox,
     * regardless of tiles' type
     * @param size : size of the arraylist
     * @return initialized arraylist
     */
    ArrayList<BoardBox> createSelectedTiles (int size) {
        ArrayList<BoardBox> selectedTiles = new ArrayList<>();
        int tileID = 0;
        for (int i = 0; i<size; i++) {
            BoardBox boardBox = new BoardBox(i, i);
            boardBox.setTile(new ItemTile(Type.CAT, tileID));
            selectedTiles.add(boardBox);
            tileID++;
        }
        return selectedTiles;
    }
    @Test
    @DisplayName("selection: generic 3 tiles check")
    void selection() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Player player = new Player("player1", modelView, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        board.setSelectedBoard(createSelectedTiles(3));
        player.setSelectedItems(board.selected());
        player.cloneTilesSelected();
        assertEquals(player.getSelectedItems().get(0).getTileID(), modelView.getSelectedItems()[0].getTileID());
        assertEquals(player.getSelectedItems().get(1).getTileID(), modelView.getSelectedItems()[1].getTileID());
        assertEquals(player.getSelectedItems().get(2).getTileID(), modelView.getSelectedItems()[2].getTileID());
        //assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("checkPermuteSelection: out of bounds order index")
    void checkPermuteSelection() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Player player = new Player("player1", modelView, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        board.setSelectedBoard(createSelectedTiles(3));
        player.setSelectedItems(board.selected());
        int [] order = new int[]{1, 2, 5};
        assertEquals(ErrorType.INVALID_ORDER_TILE_NUMBER, player.checkPermuteSelection(order));
        //assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("checkPermuteSelection: repeated order index")
    void checkPermuteSelectionCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Player player = new Player("player1", modelView, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        board.setSelectedBoard(createSelectedTiles(3));
        player.setSelectedItems(board.selected());
        int [] order = new int[]{1, 2, 1};
        assertEquals(ErrorType.INVALID_ORDER_TILE_REPETITION, player.checkPermuteSelection(order));
        //assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("permuteSelection: generic check with 3 tiles")
    void permuteSelection() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Player player = new Player("player1", modelView, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, new GameRules());
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5; int tileID=0;
        ItemTile itemTile1 = new ItemTile(Type.CAT, tileID); tileID++;
        ItemTile itemTile2 = new ItemTile(Type.TROPHY, tileID); tileID++;
        ItemTile itemTile3 = new ItemTile(Type.PLANT, tileID);
        BoardBox boardBox1 = new BoardBox(a,b); boardBox1.setTile(itemTile1);
        BoardBox boardBox2 = new BoardBox(c,d); boardBox2.setTile(itemTile2);
        BoardBox boardBox3 = new BoardBox(e,f); boardBox3.setTile(itemTile3);
        selectedBoard.add(boardBox1);
        selectedBoard.add(boardBox2);
        selectedBoard.add(boardBox3);
        board.setSelectedBoard(selectedBoard);
        player.setSelectedItems(board.selected());
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox3.getTile());
        int [] order = new int[]{1, 0, 2};
        player.permuteSelection(order);
        assertIterableEquals(selectedItems, player.getSelectedItems());
    }
}

