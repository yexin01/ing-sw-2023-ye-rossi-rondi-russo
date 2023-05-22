package it.polimi.ingsw.controller;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.InfoAndEndGameListener;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.client.ClientRMI;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import it.polimi.ingsw.network.server.RMIConnection;
import it.polimi.ingsw.network.server.Server;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

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

     */





    /*@Test
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

     */

    @Test
    void checkAndInsertBoardBox() throws Exception {
        GameRules gameRules = new GameRules();
        Server server = new Server(1099, 1100, "127.0.0.1");
        ModelView modelView = new ModelView(2, gameRules);
        GlobalLobby globalLobby = new GlobalLobby();
        GameLobby gameLobby = new GameLobby(0, 2, globalLobby);
        gameLobby.addPlayerToGame("player1", new RMIConnection(server, new ClientRMI("player1", "127.0.0.1", 1100)));
        gameLobby.addPlayerToGame("player2", new RMIConnection(server, new ClientRMI("player2", "127.0.0.1", 1100)));
        InfoAndEndGameListener infoAndEndGameListener = new InfoAndEndGameListener(gameLobby, globalLobby);
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("player1"); nicknames.add("player2");
        GameController gameController = new GameController(gameLobby, nicknames, infoAndEndGameListener);
        Game game = new Game(modelView);
        game.addPlayers(nicknames);
        game.setTurnPlayer(0);
        gameController.setGame(game);
        game.getTurnPlayerOfTheGame().setBookshelf(new Bookshelf(6,5,3));
        game.getBoard().fillBag(gameRules);
        game.getBoard().firstFillBoard(2, gameRules);
        int[] value = new int[] {1, 3};
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, "player1"), messagePayload);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        selectedBoard.add(game.getBoard().getBoardBox(1, 3));
        game.getBoard().setSelectedBoard(selectedBoard);
        gameController.checkAndInsertBoardBox(message);
        assertEquals(game.getBoard().getBoardBox(1,3).getTile().getTileID(), game.getModelView().getSelectedItems()[0].getTileID());
    }

    @Test
    void permutePlayerTiles() throws Exception {
        GameRules gameRules = new GameRules();
        Server server = new Server(1099, 1100, "127.0.0.1");
        ModelView modelView = new ModelView(2, gameRules);
        GlobalLobby globalLobby = new GlobalLobby();
        GameLobby gameLobby = new GameLobby(0, 2, globalLobby);
        gameLobby.addPlayerToGame("player1", new RMIConnection(server, new ClientRMI("player1", "127.0.0.1", 1100)));
        gameLobby.addPlayerToGame("player2", new RMIConnection(server, new ClientRMI("player2", "127.0.0.1", 1100)));
        InfoAndEndGameListener infoAndEndGameListener = new InfoAndEndGameListener(gameLobby, globalLobby);
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("player1"); nicknames.add("player2");
        GameController gameController = new GameController(gameLobby, nicknames, infoAndEndGameListener);
        Game game = new Game(modelView);
        game.addPlayers(nicknames);
        game.setTurnPlayer(0);
        gameController.setGame(game);
        game.getTurnPlayerOfTheGame().setBookshelf(new Bookshelf(6,5,3));
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
        game.getBoard().setSelectedBoard(selectedBoard);
        game.getTurnPlayerOfTheGame().selection(game.getBoard());
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox3.getTile());
        int [] value = new int[]{1, 0, 2};
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, "player1"), messagePayload);
        gameController.permutePlayerTiles(message);
        assertIterableEquals(selectedItems, game.getTurnPlayerOfTheGame().getSelectedItems());
    }

    @Test
    void selectingColumn() throws Exception {
        GameRules gameRules = new GameRules();
        Server server = new Server(1099, 1100, "127.0.0.1");
        ModelView modelView = new ModelView(2, gameRules);
        GlobalLobby globalLobby = new GlobalLobby();
        GameLobby gameLobby = new GameLobby(0, 2, globalLobby);
        gameLobby.addPlayerToGame("player1", new RMIConnection(server, new ClientRMI("player1", "127.0.0.1", 1100)));
        gameLobby.addPlayerToGame("player2", new RMIConnection(server, new ClientRMI("player2", "127.0.0.1", 1100)));
        InfoAndEndGameListener infoAndEndGameListener = new InfoAndEndGameListener(gameLobby, globalLobby);
        ArrayList<String> nicknames = new ArrayList<>();
        nicknames.add("player1"); nicknames.add("player2");
        GameController gameController = new GameController(gameLobby, nicknames, infoAndEndGameListener);
        Game game = new Game(modelView);
        game.addPlayers(nicknames);
        game.setTurnPlayer(0);
        game.createPersonalGoalCard(gameRules);
        game.createCommonGoalCard(gameRules);
        game.setTurnPlayer(0);
        gameController.setGame(game);
        Bookshelf bookshelf = new Bookshelf(6,5,3);
        bookshelf.getMatrix()[5][0] = new ItemTile(Type.CAT, 0);
        game.getTurnPlayerOfTheGame().setBookshelf(bookshelf);
        game.getTurnPlayerOfTheGame().getBookshelf().computeFreeShelves();
        game.getBoard().fillBag(gameRules);
        game.getBoard().firstFillBoard(2, gameRules);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3;
        selectedBoard.add(game.getBoard().getBoardBox(a, b));
        game.getBoard().setSelectedBoard(selectedBoard);
        game.getTurnPlayerOfTheGame().selection(game.getBoard());
        int value = 0;
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, game.getTurnPlayerOfTheGame().getNickname()), messagePayload);
        Type type = game.getBoard().getBoardBox(a, b).getTile().getType();
        gameController.selectingColumn(message);
        modelView.setNextPlayer();
        assertEquals(type, game.getTurnPlayerOfTheGame().getBookshelf().getMatrix()[4][0].getType());
    }
}
