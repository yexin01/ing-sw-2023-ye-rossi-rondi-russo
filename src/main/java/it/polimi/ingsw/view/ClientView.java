package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.handlers.MessageToserverHandlerTurn;

import java.util.ArrayList;


public class ClientView {


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


    public String getNickname(){
        return nickname;
    }


    public BoardBoxView[][] getBoardView() {
        return boardView;
    }


    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }

    public ItemTileView[][] getBookshelfView() {
        return bookshelfView;
    }

    public void setBookshelfView(ItemTileView[][] bookshelfView) {
        this.bookshelfView = bookshelfView;
    }

    public ItemTileView[] getTilesSelected() {
        return tilesSelected;
    }

    public void setTilesSelected(ItemTileView[] tilesSelected) {
        this.tilesSelected = tilesSelected;
    }



    public PersonalGoalCard getPlayerPersonalGoal() {
        return playerPersonalGoal;
    }

    public void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal) {
        this.playerPersonalGoal = playerPersonalGoal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<Integer> getCoordinatesSelected() {
        return coordinatesSelected;
    }

    public void setCoordinatesSelected(ArrayList<Integer> coordinatesSelected) throws Exception {
        this.coordinatesSelected = coordinatesSelected;

        messageToserverHandlerTurn.handleMessageToServer(coordinatesSelected.stream().mapToInt(Integer::intValue).toArray(),TurnPhase.SELECT_FROM_BOARD,nickname, MessageType.DATA);
    }

    public int[] getOrderTiles() {
        return orderTiles;
    }

    public void setOrderTiles(int[] orderTiles) throws Exception {
        this.orderTiles = orderTiles;

        messageToserverHandlerTurn.handleMessageToServer(orderTiles,TurnPhase.SELECT_ORDER_TILES,nickname,MessageType.DATA);
    }



    public int getColumn() {
        return column;
    }

    public void setColumn(int column) throws Exception {
        this.column = column;

        messageToserverHandlerTurn.handleMessageToServer(column,TurnPhase.SELECT_COLUMN,nickname,MessageType.DATA);
    }


    public MessageToserverHandlerTurn getMessageToserverHandler(ClientInterface clientInterface, Client client) {
        return messageToserverHandlerTurn;
    }

    public void setMessageToserverHandler(MessageToserverHandlerTurn messageToserverHandlerTurn) {
        this.messageToserverHandlerTurn = messageToserverHandlerTurn;
    }


    public void endGame(int choice) throws Exception {
        if(choice==0){
            //quit game
            messageToserverHandlerTurn.handleMessageToServer(1,KeyLobbyPayload.QUIT_SERVER,nickname,MessageType.LOBBY);
            //join new game
        }else messageToserverHandlerTurn.handleMessageToServer(0,KeyLobbyPayload.GLOBAL_LOBBY_DECISION,nickname,MessageType.LOBBY);
    }

    public void lobby(KeyLobbyPayload keyLobbyPayload,int value) throws Exception {
        messageToserverHandlerTurn.handleMessageToServer(value,keyLobbyPayload,nickname,MessageType.LOBBY);
    }
    public void somethingWrong() throws Exception {
        messageToserverHandlerTurn.handleMessageToServer(null, KeyErrorPayload.ERROR_DATA,nickname,MessageType.ERROR);
    }
    public void receiveEndGame() throws Exception {
        messageToserverHandlerTurn.handleMessageToServer(null, TurnPhase.END_GAME,nickname,MessageType.DATA);
    }


    public int[][] getCommonGoalView() {
        return commonGoalView;
    }

    public void setCommonGoalView(int[][] commonGoalView) {
        this.commonGoalView = commonGoalView;
    }

    public PlayerPointsView[] getPlayerPointsViews() {
        return playerPointsViews;
    }

    public void setPlayerPointsViews(PlayerPointsView[] playerPointsViews) {
        this.playerPointsViews = playerPointsViews;
    }

    public int getPersonalPoints() {
        return personalPoints;
    }

    public void setPersonalPoints(int personalPoints) {
        this.personalPoints = personalPoints;
    }
}
