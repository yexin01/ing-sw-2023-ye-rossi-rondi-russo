package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.handlers.MessageToserverHandlerTurn;

import java.util.ArrayList;

/**
 * The ClientView class represents the client's view of the game. It holds the current state of the client's game session
 * and provides methods to interact with the game and handle updates.
 */
public class ClientView {
    private String turnPlayer;


    private MessageToserverHandlerTurn messageToserverHandlerTurn;
    private BoardBoxView[][] boardView;
    private int[] orderTiles;
    private ArrayList<Integer> coordinatesSelected;
    private int personalPoints;


    private int[][] commonGoalView;

    private ItemTileView[][] bookshelfView;
    private ItemTileView[] tilesSelected;
    private PlayerPointsView[] playerPointsViews;
    private PersonalGoalCard playerPersonalGoal;
    private String nickname;
    private int column;

    /**
     * Returns the nickname of the client.
     *
     * @return The nickname of the client.
     */
    public String getNickname(){
        return nickname;
    }


    /**
     * Returns the current board view.
     *
     * @return The current board view.
     */
    public BoardBoxView[][] getBoardView() {
        return boardView;
    }

    /**
     * Sets the board view.
     *
     * @param boardView The board view to set.
     */
    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }
    /**
     * Returns the current bookshelf view.
     *
     * @return The current bookshelf view.
     */
    public ItemTileView[][] getBookshelfView() {
        return bookshelfView;
    }
    /**
     * Sets the bookshelf view.
     *
     * @param bookshelfView The bookshelf view to set.
     */
    public void setBookshelfView(ItemTileView[][] bookshelfView) {
        this.bookshelfView = bookshelfView;
    }
    /**
     * Returns the currently selected tiles.
     *
     * @return The selected tiles.
     */
    public ItemTileView[] getTilesSelected() {
        return tilesSelected;
    }
    /**
     * Sets the selected tiles.
     *
     * @param tilesSelected The tiles to set as selected.
     */
    public void setTilesSelected(ItemTileView[] tilesSelected) {
        this.tilesSelected = tilesSelected;
    }

    /**
     * Returns the player's personal goal card.
     *
     * @return The personal goal card.
     */

    public PersonalGoalCard getPlayerPersonalGoal() {
        return playerPersonalGoal;
    }
    /**
     * Sets the player's personal goal card.
     *
     * @param playerPersonalGoal The personal goal card to set.
     */
    public void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal) {
        this.playerPersonalGoal = playerPersonalGoal;
    }
    /**
     * Sets the nickname of the client.
     *
     * @param nickname The nickname to set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Sets the selected coordinates and notifies the messageToserverHandlerTurn.
     *
     * @param coordinatesSelected The coordinates to set as selected.
     */
    public void setCoordinatesSelected(ArrayList<Integer> coordinatesSelected)  {
        this.coordinatesSelected = coordinatesSelected;

        messageToserverHandlerTurn.handleMessageToServer(coordinatesSelected.stream().mapToInt(Integer::intValue).toArray(),TurnPhase.SELECT_FROM_BOARD,nickname, MessageType.DATA);
    }
    /**
     * Returns the current order of tiles.
     *
     * @return The order of tiles.
     */
    public int[] getOrderTiles() {
        return orderTiles;
    }

    /**
     * Sets the order of tiles and notifies the messageToserverHandlerTurn.
     *
     * @param orderTiles The order of tiles to set.
     */
    public void setOrderTiles(int[] orderTiles){
        this.orderTiles = orderTiles;

        messageToserverHandlerTurn.handleMessageToServer(orderTiles,TurnPhase.SELECT_ORDER_TILES,nickname,MessageType.DATA);
    }

    /**
     * Returns the selected column.
     *
     * @return The selected column.
     */

    public int getColumn() {
        return column;
    }
    /**
     * Sets the selected column and notifies the messageToserverHandlerTurn.
     *
     * @param column The column to set as selected.
     */
    public void setColumn(int column) {
        this.column = column;

        messageToserverHandlerTurn.handleMessageToServer(column,TurnPhase.SELECT_COLUMN,nickname,MessageType.DATA);
    }

    /**
     * Sets the messageToserverHandlerTurn.
     * @param messageToserverHandlerTurn The messageToserverHandlerTurn to set.
     */
    public void setMessageToserverHandler(MessageToserverHandlerTurn messageToserverHandlerTurn) {
        this.messageToserverHandlerTurn = messageToserverHandlerTurn;
    }


    /**
     * Sends the lobby message and notifing the messageToserverHandlerTurn.
     * @param keyLobbyPayload The keyLobbyPayload to send.
     * @param value The value associated with the lobby message.
     */

    public void lobby(KeyLobbyPayload keyLobbyPayload,int value) {
        messageToserverHandlerTurn.handleMessageToServer(value,keyLobbyPayload,nickname,MessageType.LOBBY);
    }
    /**
     * Sends a somethingWrong message to the messageToserverHandlerTurn.
     */
    public void somethingWrong(){
        messageToserverHandlerTurn.handleMessageToServer(null, KeyErrorPayload.ERROR_DATA,nickname,MessageType.ERROR);
    }
    /**
     * Sends a receiveEndGame message to the messageToserverHandlerTurn.
     */
    public void receiveEndGame() {
        messageToserverHandlerTurn.handleMessageToServer(null, TurnPhase.END_GAME,nickname,MessageType.DATA);
    }

    /**
     * Gets the common goal view.
     * @return The common goal view.
     */

    public int[][] getCommonGoalView() {
        return commonGoalView;
    }
    /**
     * Sets the common goal view.
     * @param commonGoalView The common goal view to set.
     */
    public void setCommonGoalView(int[][] commonGoalView) {
        this.commonGoalView = commonGoalView;
    }
    /**
     * Gets the player points views.
     * @return The player points views.
     */
    public PlayerPointsView[] getPlayerPointsViews() {
        return playerPointsViews;
    }
    /**
     * Sets the player points views.
     * @param playerPointsViews The player points views to set.
     */
    public void setPlayerPointsViews(PlayerPointsView[] playerPointsViews) {
        this.playerPointsViews = playerPointsViews;
    }
    /**
     * Gets the personal points.
     * @return The personal points.
     */
    public int getPersonalPoints() {
        return personalPoints;
    }
    /**
     * Sets the personal points.
     * @param personalPoints The personal points to set.
     */
    public void setPersonalPoints(int personalPoints) {
        this.personalPoints = personalPoints;
    }

    /**
     * Gets the turn player.
     * @return The turn player.
     */
    public String getTurnPlayer() {
        return turnPlayer;
    }
    /**
     * Sets the turn player.
     * @param turnPlayer The turn player to set.
     */
    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }
}
