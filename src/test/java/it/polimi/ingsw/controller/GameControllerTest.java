package it.polimi.ingsw.controller;
/*

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromClient2;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.ServerView;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    /*@Test
    void startGame() throws Exception {
        GameController gameController = new GameController();
        HashMap<String, Connection> playerMap = new HashMap<>();
        HashMap<String, Integer> playersId = new HashMap<>();
        String[] playerNames = {"player1", "player2", "player3"};
        for (int i = 0; i < playerNames.length; i++) {
            String playerName = playerNames[i];
            ClientView clientView = new ClientView();
            playerMap.put(playerName, clientView);
            playersId.put(playerName, i);
        }
        ServerView serverView = new ServerView();
        gameController.startGame(playerMap, playersId, serverView);
        assertEquals(2, gameController.getModel().getPlayers().size());
    }





    @Test
    void removePlayer() throws Exception {
        GameRules gameRules = new GameRules();
        HashMap<String, Integer> playersId = new HashMap<>();
        playersId.put("player1", 0);
        playersId.put("player2", 1);
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        GameController gameController = new GameController();
        gameController.setGame(game);
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        players.add(new Player("player2", modelView));
        game.setPlayers(players);
        gameController.removePlayer("player2");
        assertEquals(1, game.getPlayers().size());
    }

    @Test
    void checkAndInsertBoardBox() throws Exception {
        GameRules gameRules = new GameRules();
        HashMap<String, Integer> playersId = new HashMap<>();
        playersId.put("player1", 0);
        playersId.put("player2", 1);
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        GameController gameController = new GameController();
        gameController.setGame(game);
        ServerView serverView = new ServerView();
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("player1", modelView));
        players.add(new Player("player2", modelView));
        game.setPlayers(players);
        game.getTurnPlayer().setBookshelf(new Bookshelf(6,5,3));
        game.getBoard().fillBag(gameRules);
        game.getBoard().firstFillBoard(2, gameRules);
        int[] value = new int[] {1, 3};
        MessageFromClient2 message = new MessageFromClient2(EventType.BOARD_SELECTION, "player1", value);
        //Wait for serverView sendMessage
    }


    @Test
    void associatePlayerTiles() {
        //selection already tested inside Player in model
    }

    @Test
    void permutePlayerTiles() throws Exception {
        /*
        ServerView serverView = new ServerView();
        GameController gameController = new GameController();
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        gameController.setGame(game);
        ArrayList<Player> players = new ArrayList<>();
        Player player = new Player("player1", modelView);
        players.add(player);
        game.setPlayers(players);
        Board board = game.getBoard();
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
        player.selection(board);
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox3.getTile());
        int [] value = new int[]{1, 0, 2};
        MessageFromClient2 message = new MessageFromClient2(EventType.ORDER_TILES, "player1", value);
        gameController.permutePlayerTiles(message);
        assertIterableEquals(selectedItems, player.getSelectedItems());


    }

    @Test
    void selectingColumn() throws Exception {
        GameController gameController = new GameController();
        HashMap<String, Integer> playersId = new HashMap<String, Integer>();
        GameRules gameRules = new GameRules();
        ModelView modelView = new ModelView(playersId, gameRules);
        Game game = new Game(gameRules, modelView);
        gameController.setGame(game);
        Player player = new Player("player1", modelView);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        game.setPlayers(players);
        game.getTurnPlayer().setBookshelf(new Bookshelf(6, 5 , 3));
        int [] value = new int[]{0};
        MessageFromClient2 message = new MessageFromClient2(EventType.COLUMN, "player1", value);
        gameController.selectingColumn(message);
        assertEquals(0, game.getTurnPlayer().getBookshelf().getColumnSelected());
    }

    @Test
    void insertBookshelf() {
        //insertTiles and points update already tested inside Bookshelf and Game in model
    }
}

 */