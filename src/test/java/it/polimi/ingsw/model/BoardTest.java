package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.ModelView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    @DisplayName("cloneBoard: generic check")
    void cloneBoard () throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, gameRules);
        assertEquals(board.getMatrix()[4][4].getTile().getType(), board.cloneBoard()[4][4].getType());
    }

    @Test
    @DisplayName("checkCoordinates: check for not occupiable tile")
    void checkCoordinates() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int x = 0; int y = 0;
        assertNotEquals(null, board.checkCoordinates(x,y));
    }

    @Test
    @DisplayName("checkCoordinates: check for out of bound tile")
    void checkCoordinatesCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int x = 0; int y = 9;
        assertNotEquals(null, board.checkCoordinates(x,y));
    }

    @Test
    @DisplayName("firstFill: check for starting fill box 0,0 (2P)")
    void firstFillBoard() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        //Set the coordinates you want to check
        int x = 0; int y = 0;
        //Set inside "expected" true or false
        assertFalse(board.getBoardBox(x, y).isOccupiable());
    }

    @Test
    @DisplayName("setFreeEdges: check for tile 7,3 (2P)")
    void setFreeEdges() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        //Set the coordinates of the tile you want to check the free edges
        int x = 7; int y = 3;
        //Set inside "expected" the expected number of free edges
        Assertions.assertEquals(2, board.getMatrix()[x][y].getFreeEdges());
    }

    @Test
    @DisplayName("setFreeEdges: check for tile 2,3 (2P)")
    void setFreeEdgesCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int x = 2; int y = 3;
        Assertions.assertEquals(1, board.getMatrix()[x][y].getFreeEdges());
    }

    @Test
    @DisplayName("setFreeEdges: check for tile 3,3 (2P)")
    void setFreeEdgesCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int x = 3; int y = 3;
        Assertions.assertEquals(0, board.getMatrix()[x][y].getFreeEdges());
    }

    @Test
    @DisplayName("increaseNear: removing tile 7,3 (Based on 2P matrix)")
    void increaseNear() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        //Set the coordinates of the tile you want to remove
        int x = 7; int y = 3;
        board.increaseNear(x,y);
        //Set inside "expected" the new number of free edges
        Assertions.assertEquals(2, board.getBoardBox(x,y+1).getFreeEdges());
        Assertions.assertEquals(2, board.getBoardBox(x-1,y).getFreeEdges());
    }

    @Test
    @DisplayName("increaseNear: removing tile 7,4 (Based on 2P matrix)")
    void increaseNearCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int x = 7; int y = 4;
        board.increaseNear(x,y);
        Assertions.assertEquals(3, board.getBoardBox(x,y+1).getFreeEdges());
        Assertions.assertEquals(1, board.getBoardBox(x-1,y).getFreeEdges());
        Assertions.assertEquals(3, board.getBoardBox(x,y-1).getFreeEdges());
    }

    @Test
    @DisplayName("increaseNear: removing tile 2,3 ; 2,4 (Based on 2P matrix)")
    void increaseNearCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        board.increaseNear(2,3);
        board.increaseNear(2,4);
        Assertions.assertEquals(3, board.getBoardBox(1,3).getFreeEdges());
        Assertions.assertEquals(3, board.getBoardBox(1,4).getFreeEdges());
        Assertions.assertEquals(1, board.getBoardBox(3,3).getFreeEdges());
        Assertions.assertEquals(1, board.getBoardBox(3,4).getFreeEdges());
    }

    @Test
    @DisplayName("allAdjacent: selected tiles on the same row")
    void allAdjacent() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the coordinates of the 3 selected tiles
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertTrue(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allAdjacent: selected tiles on the same column")
    void allAdjacentCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int a=1; int b=3;  int c=2; int d=3;  int e=3; int f=3;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertTrue(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allAdjacent: L-shape pattern selected tiles")
    void allAdjacentCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int a=1; int b=3;  int c=2; int d=3;  int e=2; int f=4;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertTrue(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allAdjacent: selected tiles on the same row but not adjacent")
    void allAdjacentCC3() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int a=1; int b=2;  int c=1; int d=3;  int e=1; int f=5;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertFalse(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allSameRowOrSameColumn: tiles on the same row")
    void allSameRowOrSameColumn() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the coordinates of the 3 selected tiles
        int a=1; int b=3;  int c=1; int d=4;  int e=1; int f=5;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertTrue(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allSameRowOrSameColumn: tiles on the same column")
    void allSameRowOrSameColumnCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int a=1; int b=3;  int c=2; int d=3;  int e=3; int f=3;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertTrue(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allSameRowOrSameColumn: L-shape pattern tiles")
    void allSameRowOrSameColumnCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int a=2; int b=3;  int c=3; int d=3;  int e=3; int f=4;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertTrue(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("allSameRowOrSameColumn: tiles on the same column but not adjacent")
    void allSameRowOrSameColumnCC3() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int a=2; int b=3;  int c=3; int d=3;  int e=5; int f=3;
        int[] coordinatesSelected = new int[] {a,b,c,d,e,f};
        assertFalse(board.allAdjacent(coordinatesSelected));
    }

    @Test
    @DisplayName("checkSelectable: generic check")
    void checkSelectable() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int[] selection = new int[] {7,3,7,4,7,5};
        assertEquals(null, board.checkSelectable((selection), 3));
    }

    @Test
    @DisplayName("checkSelectable: only 1 coordinate")
    void checkSelectableCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int[] selection = new int[] {7};
        assertEquals(ErrorType.INVALID_INPUT, board.checkSelectable((selection), 3));
    }

    @Test
    @DisplayName("checkSelectable: 8 coordinate")
    void checkSelectableCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        int[] selection = new int[] {7,3,7,4,7,5,7,6};
        assertEquals(ErrorType.TOO_MANY_TILES, board.checkSelectable((selection), 3));
    }

    @Test
    @DisplayName("selected: 3 different tiles")
    void selected() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, gameRules);
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
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox3.getTile());
        assertIterableEquals(selectedItems, board.selected());
    }

    @Test
    @DisplayName("resetBoard: generic check")
    void resetBoard() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        board.fillBag(gameRules);
        board.firstFillBoard(2, gameRules);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3; int tileID=0;
        ItemTile itemTile1 = new ItemTile(Type.CAT, tileID);
        BoardBox boardBox1 = new BoardBox(a,b); boardBox1.setTile(itemTile1);
        selectedBoard.add(boardBox1);
        board.setSelectedBoard(selectedBoard);
        board.resetBoard();
        assertEquals(0, board.getMatrix()[a][b].getFreeEdges());
    }

    @Test
    @DisplayName("checkRefill: generic false check matrix")
    void checkRefill() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
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
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    board.getMatrix()[i][j].setTile(new ItemTile(Type.CAT, 1));
                    board.getMatrix()[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].getTile()!=null){
                    board.setFreeEdges(i,j);
                }
            }
        }
        //Set true or false for the check
        assertEquals(false, board.checkRefill());
    }

    @Test
    @DisplayName("checkRefill: generic true check matrix")
    void checkRefillCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 1, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 1, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int dimension = 9;
        board.setMatrix(new BoardBox[dimension][dimension]);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    board.getMatrix()[i][j].setTile(new ItemTile(Type.CAT, 1));
                    board.getMatrix()[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].getTile()!=null){
                    board.setFreeEdges(i,j);
                }
            }
        }
        assertTrue(board.checkRefill());
    }

    @Test
    @DisplayName("checkRefill: 1 row of tiles remained")
    void checkRefillCC2() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int dimension = 9;
        board.setMatrix(new BoardBox[dimension][dimension]);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    board.getMatrix()[i][j].setTile(new ItemTile(Type.CAT, 1));
                    board.getMatrix()[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].getTile()!=null){
                    board.setFreeEdges(i,j);
                }
            }
        }
        assertFalse(board.checkRefill());
    }

    @Test
    @DisplayName("checkRefill: 1 tile remained")
    void checkRefillCC3() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int dimension = 9;
        board.setMatrix(new BoardBox[dimension][dimension]);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    board.getMatrix()[i][j].setTile(new ItemTile(Type.CAT, 1));
                    board.getMatrix()[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].getTile()!=null){
                    board.setFreeEdges(i,j);
                }
            }
        }
        assertTrue(board.checkRefill());
    }

    @Test
    @DisplayName("checkRefill: blank board")
    void checkRefillCC4() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        int[][] matrix = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int dimension = 9;
        board.setMatrix(new BoardBox[dimension][dimension]);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                board.getMatrix()[i][j] = new BoardBox(i,j);
                if(matrix[i][j]==1){
                    board.getMatrix()[i][j].setTile(new ItemTile(Type.CAT, 1));
                    board.getMatrix()[i][j].setOccupiable(true);
                }
            }
        }
        for (int i = 0; i < board.getMatrix().length; i++) {
            for (int j = 0; j < board.getMatrix()[i].length; j++) {
                if(board.getMatrix()[i][j].getTile()!=null){
                    board.setFreeEdges(i,j);
                }
            }
        }
        assertTrue(board.checkRefill());
    }

    @Test
    @DisplayName("refill: check for box 0,0 (2P)")
    void refill() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        board.refill();
        //Set the coordinate of the tile you want to check
        int x=0; int y=0;
        assertEquals(null, board.getBoardBox(x,y).getTile());
    }

    @Test
    @DisplayName("refill: check for box 6,2 (2P)")
    void refillCC1() throws Exception {
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(2, gameRules);
        Board board = new Board(modelView);
        //Set the matrix of the number of players you want to test
        int numPlayers = 2;
        board.fillBag(gameRules);
        board.firstFillBoard(numPlayers, gameRules);
        board.refill();
        //Set the coordinate of the tile you want to check
        int x=6; int y=2;
        assertEquals(null, board.getBoardBox(x,y).getTile());
    }
}

