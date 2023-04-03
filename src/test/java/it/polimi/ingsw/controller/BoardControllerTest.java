package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class BoardControllerTest {

    @Test
    @DisplayName("Check of the correctness of the fill at the start of the game")
    void firstFill() throws Exception {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        GameRules gameRules = new GameRules();
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        boardController.firstFill(numPlayers, gameRules);
        //Set the coordinates you want to check
        int x = 0; int y = 0;
        //Set inside "expected" true or false
        assertFalse(boardController.getBoard().getBoardBox(x, y).isOccupiable());
        assertFalse(boardController.getBoard().getBoardBox(x, y).isOccupied());
    }

    @Test
    @DisplayName("Check for the correctness of a tile's free edges")
    void setFreeEdges() throws Exception {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        GameRules gameRules = new GameRules();
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        boardController.firstFill(numPlayers, gameRules);
        //Set the coordinates of the tile you want to check the free edges
        int x = 1; int y = 3;
        boardController.setFreeEdges(x,y);
        //Set inside "expected" the expected number of free edges
        Assertions.assertEquals(2, boardController.getBoard().getBoardBox(x,y).getFreeEdges());
    }

    @Test
    @DisplayName("Checks if the free edges are correctly updated after removing one tile")
    void increaseNear() throws Exception {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        GameRules gameRules = new GameRules();
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        boardController.firstFill(numPlayers, gameRules);
        //Set the coordinates of the tile you want to remove
        int x = 1; int y = 3;
        boardController.setFreeEdges(x+1,y);
        boardController.setFreeEdges(x-1,y);
        boardController.setFreeEdges(x,y-1);
        boardController.setFreeEdges(x,y+1);
        boardController.increaseNear(x,y);
        //Set inside "expected" the new number of free edges
        Assertions.assertEquals(2, boardController.getBoard().getBoardBox(x+1,y).getFreeEdges());
        Assertions.assertEquals(2, boardController.getBoard().getBoardBox(x,y+1).getFreeEdges());
        Assertions.assertEquals(0, boardController.getBoard().getBoardBox(x-1,y).getFreeEdges());
        Assertions.assertEquals(0, boardController.getBoard().getBoardBox(x,y-1).getFreeEdges());
    }

    @Test
    @DisplayName("Checks if the tiles are correctly signed as all adjacent")
    void allAdjacent() throws Exception {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        GameRules gameRules = new GameRules();
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        boardController.firstFill(numPlayers, gameRules);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        //Set the coordinates of the 3 selected tiles
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5;
        selectedBoard.add(new BoardBox(a,b));
        selectedBoard.add(new BoardBox(c,d));
        selectedBoard.add(new BoardBox(e,f));
        board.setSelectedBoard(selectedBoard);
        assertTrue(boardController.allAdjacent());
    }

    @Test
    @DisplayName("Checks if the tiles are correctly signed as all on the same row or column")
    void allSameRowOrSameColumn() throws Exception {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        GameRules gameRules = new GameRules();
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        boardController.firstFill(numPlayers, gameRules);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        //Set the coordinates of the 3 selected tiles
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5;
        selectedBoard.add(new BoardBox(a,b));
        selectedBoard.add(new BoardBox(c,d));
        selectedBoard.add(new BoardBox(e,f));
        board.setSelectedBoard(selectedBoard);
        assertTrue(boardController.allSameRowOrSameColumn());
    }

    @Test
    @DisplayName("Checks if the method returns the correct array list of ItemTile")
    void selected() {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5; int tileID=0;
        //Set in ItemTile the type of the boxes
        ItemTile itemTile1 = new ItemTile(Type.CAT, tileID); tileID++;
        ItemTile itemTile2 = new ItemTile(Type.TROPHY, tileID); tileID++;
        ItemTile itemTile3 = new ItemTile(Type.PLANT, tileID);
        BoardBox boardBox1 = new BoardBox(a,b); boardBox1.setTile(itemTile1);
        BoardBox boardBox2 = new BoardBox(c,d); boardBox1.setTile(itemTile2);
        BoardBox boardBox3 = new BoardBox(e,f); boardBox1.setTile(itemTile3);
        selectedBoard.add(0, boardBox1);
        selectedBoard.add(1, boardBox2);
        selectedBoard.add(2, boardBox3);
        board.setSelectedBoard(selectedBoard);
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(0, itemTile1);
        selectedItems.add(1, itemTile2);
        selectedItems.add(2, itemTile3);
        assertIterableEquals(selectedItems, boardController.selected());
    }

    @Test
    @DisplayName("Check for the correctness of the checkRefill")
    void checkRefill() {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        //Initialize the matrix as you want
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int dimension = 9;
        board.setMatrix(new BoardBox[dimension][dimension]);
        Random random=new Random();
        int randomNumber;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    randomNumber = random.nextInt(board.getTiles().size());
                    board.getMatrix()[i][j].setTile(board.getTiles().get(randomNumber));
                    board.getTiles().remove(randomNumber);
                    board.getMatrix()[i][j].setOccupiable(true);
                    board.getMatrix()[i][j].setOccupied(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].isOccupied()){
                    boardController.setFreeEdges(i,j);
                }
            }
        }
        //Set true or false for the check
        assertEquals(false, boardController.checkRefill());
    }

    @Test
    @DisplayName("Checks if the refill is correctly done")
    void refill() throws Exception {
        Board board = new Board();
        BoardController boardController = new BoardController(board);
        GameRules gameRules = new GameRules();
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        boardController.firstFill(numPlayers, gameRules);
        boardController.refill();
        //Set the coordinate of the tile you want to check
        int x=0; int y=0;
        assertEquals(false, boardController.getBoard().getBoardBox(x,y).isOccupied());
    }
}