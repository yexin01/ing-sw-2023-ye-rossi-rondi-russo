package it.polimi.ingsw.controller;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.ItemTile;
import it.polimi.ingsw.model.Type;
import it.polimi.ingsw.network.client.ClientRMI;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;
import it.polimi.ingsw.network.server.RMIConnection;
import it.polimi.ingsw.network.server.Server;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class GameControllerTest {

    /**
     * Initializes a game between 2 players
     * @return initialized game's gameController
     */
    public GameController initializeGame () throws Exception {
        GameRules gameRules = new GameRules();
        Server server = new Server(1099, 1100, "127.0.0.1");
        GlobalLobby globalLobby = new GlobalLobby();
        GameLobby gameLobby = new GameLobby(0, 2, globalLobby);
        gameLobby.addPlayerToGame("player1", new RMIConnection(server, new ClientRMI("player1", "127.0.0.1", 1100)));
        gameLobby.addPlayerToGame("player2", new RMIConnection(server, new ClientRMI("player2", "127.0.0.1", 1100)));
        GameController gameController = new GameController();
        gameController.createGame(gameLobby);
        gameController.getModel().getTurnPlayerOfTheGame().setBookshelf(new Bookshelf());
        gameController.getModel().getBoard().fillBag(gameRules);
        gameController.getModel().getBoard().firstFillBoard(2, gameRules);
        return gameController;
    }



    @Test
    @DisplayName("createGame: check for the right turn phase")
    void createGame() throws Exception {
        GameController gameController = initializeGame();
        assertEquals(TurnPhase.SELECT_FROM_BOARD, gameController.getModel().getModelView().getTurnPhase());
    }

    @Test
    @DisplayName("checkAndInsertBoardBox: check for the right selection from board")
    void checkAndInsertBoardBox() throws Exception {
        GameController gameController = initializeGame();
        gameController.getModel().getModelView().setTurnPlayer(1);
        int[] value = new int[] {1, 3};
        MessagePayload messagePayload = new MessagePayload(TurnPhase.SELECT_FROM_BOARD);
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, gameController.getTurnNickname()), messagePayload);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        selectedBoard.add(gameController.getModel().getBoard().getBoardBox(1, 3));
        gameController.getModel().getBoard().setSelectedBoard(selectedBoard);
        gameController.getModel().getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
        gameController.receiveMessageFromClient(message);
        assertEquals(gameController.getModel().getBoard().getBoardBox(1,3).getTile().getTileID(), gameController.getModel().getModelView().getSelectedItems()[0].getTileID());
    }

    @Test
    @DisplayName("checkAndInsertBoardBox: wrong value inside message's payload")
    void checkAndInsertBoardBoxCC1() throws Exception {
        GameController gameController = initializeGame();
        int[] value = new int[] {1, 3, 3, 3};
        MessagePayload messagePayload = new MessagePayload(TurnPhase.SELECT_FROM_BOARD);
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, gameController.getTurnNickname()), messagePayload);
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        selectedBoard.add(gameController.getModel().getBoard().getBoardBox(1, 3));
        gameController.getModel().getBoard().setSelectedBoard(selectedBoard);
        gameController.getModel().getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
        gameController.receiveMessageFromClient(message);
        assertEquals(TurnPhase.SELECT_FROM_BOARD, gameController.getModel().getModelView().getTurnPhase());
    }

    @Test
    @DisplayName("permutePlayerTiles: checks the tiles' order after a permutation")
    void permutePlayerTiles() throws Exception {
        GameController gameController = initializeGame();
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
        gameController.getModel().getBoard().setSelectedBoard(selectedBoard);
        gameController.getModel().getTurnPlayerOfTheGame().setSelectedItems(gameController.getModel().getBoard().selected());
        ArrayList<ItemTile> selectedItems = new ArrayList<>();
        selectedItems.add(boardBox2.getTile());
        selectedItems.add(boardBox1.getTile());
        selectedItems.add(boardBox3.getTile());
        int [] value = new int[]{1, 0, 2};
        gameController.getModel().getModelView().setTurnPhase(TurnPhase.SELECT_ORDER_TILES);
        MessagePayload messagePayload = new MessagePayload(TurnPhase.SELECT_ORDER_TILES);
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, gameController.getModel().getTurnPlayerOfTheGame().getNickname()), messagePayload);
        gameController.receiveMessageFromClient(message);
        assertIterableEquals(selectedItems, gameController.getModel().getTurnPlayerOfTheGame().getSelectedItems());
    }

    @Test
    @DisplayName("selectingColumn: checks the right insertion inside the bookshelf")
    void selectingColumn() throws Exception {
        GameController gameController = initializeGame();
        gameController.getModel().getModelView().setTurnPlayer(1);
        gameController.getModel().getTurnPlayerOfTheGame().getBookshelf().computeFreeShelves();
        ArrayList<BoardBox> selectedBoard = new ArrayList<>();
        int a=1; int b=3;
        selectedBoard.add(gameController.getModel().getBoard().getBoardBox(a, b));
        gameController.getModel().getBoard().setSelectedBoard(selectedBoard);
        gameController.getModel().getTurnPlayerOfTheGame().setSelectedItems(gameController.getModel().getBoard().selected());
        int value = 0;
        gameController.getModel().getModelView().setTurnPhase(TurnPhase.SELECT_COLUMN);
        MessagePayload messagePayload = new MessagePayload(TurnPhase.SELECT_COLUMN);
        messagePayload.put(Data.VALUE_CLIENT, value);
        Message message = new Message(new MessageHeader(MessageType.DATA, gameController.getModel().getTurnPlayerOfTheGame().getNickname()), messagePayload);
        Type type = gameController.getModel().getBoard().getBoardBox(a, b).getTile().getType();
        gameController.receiveMessageFromClient(message);
        gameController.getModel().getModelView().setNextPlayer();
        assertEquals(type, gameController.getModel().getTurnPlayerOfTheGame().getBookshelf().getMatrix()[5][0].getType());
    }
}
