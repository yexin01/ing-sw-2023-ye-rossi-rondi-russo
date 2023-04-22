package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.json.GameRules;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @Test
    @DisplayName("checkPermuteSelection: out of bounds order index")
    void checkPermuteSelection() throws Exception {
        Player player = new Player("player1");
        Board board = new Board();
        ArrayList<BoardBox> selectedTiles = new ArrayList<>();
        int size = 3; int tileID = 0;
        for (int i = 0; i<size; i++) {
            BoardBox boardBox = new BoardBox(i, i);
            boardBox.setTile(new ItemTile(Type.CAT, tileID));
            selectedTiles.add(boardBox);
            tileID++;
        }
        board.setSelectedBoard(selectedTiles);
        player.selection(board);
        int [] order = new int[]{1, 2, 5};
        assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("checkPermuteSelection: repeated order index")
    void checkPermuteSelectionCC1() throws Exception {
        Player player = new Player("player1");
        Board board = new Board();
        ArrayList<BoardBox> selectedTiles = new ArrayList<>();
        int size = 3; int tileID = 0;
        for (int i = 0; i<size; i++) {
            BoardBox boardBox = new BoardBox(i, i);
            boardBox.setTile(new ItemTile(Type.CAT, tileID));
            selectedTiles.add(boardBox);
            tileID++;
        }
        board.setSelectedBoard(selectedTiles);
        player.selection(board);
        int [] order = new int[]{1, 2, 1};
        assertThrows(Error.class, ()->player.checkPermuteSelection(order));
    }

    @Test
    @DisplayName("permuteSelection: generic check with 3 tiles")
    void permuteSelection() throws Exception {
        Player player = new Player("player1");
        Board board = new Board();
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5; int tileID=0;
        ItemTile itemTile1 = new ItemTile(Type.CAT, tileID); tileID++;
        ItemTile itemTile2 = new ItemTile(Type.TROPHY, tileID); tileID++;
        ItemTile itemTile3 = new ItemTile(Type.PLANT, tileID);
        BoardBox boardBox1 = new BoardBox(a,b); boardBox1.setTile(itemTile1);
        BoardBox boardBox2 = new BoardBox(c,d); boardBox1.setTile(itemTile2);
        BoardBox boardBox3 = new BoardBox(e,f); boardBox1.setTile(itemTile3);
        selectedBoard.add(boardBox1);
        selectedBoard.add(boardBox2);
        selectedBoard.add(boardBox3);
        board.setSelectedBoard(selectedBoard);
        player.selection(board);
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox3.getTile());
        int [] order = new int[]{2, 1, 3};
        player.permuteSelection(order);
        assertIterableEquals(selectedItems, player.getSelectedItems());
    }
}