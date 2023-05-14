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
    private CommonGoalView[] commonGoalViews;
    private int[] commonGoalPoints;

    private TurnPhase turnPhase;
    private String[] players;
    private String turnPlayer;
    private ItemTileView[][] bookshelfView;
    private ItemTileView[] tilesSelected;
    private PlayerPointsView playerPoints;
    private PersonalGoalCard playerPersonalGoal;
    private int indexPersonal;
    private String nickname;
    private int column;

    public ClientView(){
        turnPhase=TurnPhase.END_TURN;

    }

    public String getNickname(){

        return nickname;
    }


    public BoardBoxView[][] getBoardView() {
        return boardView;
    }


    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }

    public CommonGoalView[] getCommonGoalViews() {
        return commonGoalViews;
    }

    public void setCommonGoalViews(CommonGoalView[] commonGoalViews) {
        this.commonGoalViews = commonGoalViews;
    }

    public int[] getCommonGoalPoints() {
        return commonGoalPoints;
    }

    public void setCommonGoalPoints(int[] commonGoalPoints) {
        this.commonGoalPoints = commonGoalPoints;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
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

    public PlayerPointsView getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(PlayerPointsView playerPoints) {
        this.playerPoints = playerPoints;
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

    public int getIndexPersonal() {
        return indexPersonal;
    }

    public void setIndexPersonal(int indexPersonal) throws Exception {
        GameRules gameRules=new GameRules();
        playerPersonalGoal=gameRules.getPersonalGoalCard(indexPersonal);
        this.indexPersonal = indexPersonal;
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

    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }


    public void lobby(KeyLobbyPayload keyLobbyPayload,int value) throws Exception {
        messageToserverHandlerTurn.handleMessageToServer(value,keyLobbyPayload,nickname,MessageType.LOBBY);
    }
    public void somethingWrong() throws Exception {
        messageToserverHandlerTurn.handleMessageToServer(null, KeyErrorPayload.ERROR_DATA,nickname,MessageType.ERROR);
    }

    public String getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(String turnPlayer) {
        this.turnPlayer = turnPlayer;
    }
}
