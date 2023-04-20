package it.polimi.ingsw.controller;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.HashMap;
import java.util.List;


public class GameController {

    private SendMessages sendMessages;

    //private HashMap<String, Client> playerMap;


    /*
        private SetupController setupController;

     */
    private TurnController turnController;

    private Game game;
    //private transient final PropertyChangeSupport listeners=new PropertyChangeSupport(this);

    public GameController( Game game) throws Exception {
        this.game=game;
        initializeControllers();
    }

    public TurnController getTurnController() {
        return turnController;
    }
    public Game getModel() {
        return game;
    }
    public void initializeControllers() throws Exception {
        GameRules gameRules=new GameRules();
        int maxPlayers=gameRules.getMaxPlayers();
        turnController=new TurnController(game);
        turnController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    public void setTurnController(TurnController turnController) {
        this.turnController = turnController;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public void receiveMessageFromClient(MessageFromClient message){
        String nicknamePlayer = message.getClientMessageHeader().getNicknameSender();
        MessageFromClientType messageName = message.getClientMessageHeader().getClientMessage();
        try{
            if (!nicknamePlayer.equals(turnController.getTurnPlayer().getNickname())) {
                throw new Error(ErrorType.ILLEGAL_TURN);
            }
            switch (messageName) {
                case SELECTION_BOARD -> checkAndInsertBoardBox(message);
                case RESET_BOARD_CHOICE -> resetBoardChoice();
                case FINISH_SELECTION -> associatePlayerTiles();
                case SELECT_ORDER_TILES -> permutePlayerTiles(message);
                case SELECT_COLUMN -> selectingColumn(message);
                case INSERT_BOOKSHELF -> insertBookshelf();
                default -> throw new IllegalArgumentException();
            }
        }catch(Exception e){
        }


    }

    public void checkAndInsertBoardBox( MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        int[] coordinates=((int[])message.getMessagePayload().getObject());
        int x=coordinates[0];
        int y=coordinates[1];
        checkError(game.getBoard().checkCoordinates(x,y));
        System.out.println("You selected "+x+","+y);
        BoardBox boardBox=game.getBoard().getBoardBox(x,y);
        int maxPlayerSelectableTiles=game.getTurnPlayer().getBookshelf().numSelectableTiles();
        checkError(game.getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles));
        sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.END_PHASE);
    }
    public void resetBoardChoice() throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getBoard().resetBoardChoice();
        sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.END_PHASE);
    }

    public void checkError(ErrorType possibleInvalidArgoment) throws Exception {
        if(possibleInvalidArgoment!=null){
            sendError(possibleInvalidArgoment);
            throw new Exception();
        };
    }

    public void associatePlayerTiles() throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        turnController.changePhase();
        game.getTurnPlayer().selection(game.getBoard());

       // sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.END_PHASE);
    }
    public void permutePlayerTiles(MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_ORDER_TILES);
        turnController.changePhase();
        int[] orderTiles=((int[])message.getMessagePayload().getObject());
        checkError(game.getTurnPlayer().checkPermuteSelection(orderTiles));
        game.getTurnPlayer().permuteSelection(orderTiles);
        sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.END_PHASE);
    }

    public void selectingColumn(MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_COLUMN);
        turnController.changePhase();
        int column=(int)message.getMessagePayload().getObject();
        System.out.println("You selected "+column);
        checkError(game.getTurnPlayer().getBookshelf().checkBookshelf(column,game.getTurnPlayer().getSelectedItems().size()));
        game.getTurnPlayer().getBookshelf().setColumnSelected(column);
        sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.END_PHASE);
    }

    public void insertBookshelf() throws Exception {
        illegalPhase(TurnPhase.INSERT_BOOKSHELF_AND_POINTS);
        turnController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getTurnPlayer().insertBookshelf();
        if(game.getTurnPlayer().getBookshelf().isFull()){
            game.setEndGame(true);
        }
        game.updateAllPoints();
        finishTurn();
    }
    public boolean finishTurn() {
        //TODO da finire
        if(game.isEndGame() && game.getTurnPlayer().equals(game.getLastPlayer())){
            //endGame();
            return false;
        }
        if(game.getBoard().checkRefill()){
            game.getBoard().refill();
        }
        //sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.END_TURN);
        game.setNextPlayer();
        sendMessage(game.getTurnPlayer().getNickname(),MessageFromServerType.START_TURN);
        return true;
    }

    public void endGame() {
        //TODO change END GAME
        List<Player> ranking=  game.checkWinner();
        //endGameListener.endGame(ranking);
    }

    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!turnController.getCurrentPhase().equals(phase)){
            sendError(ErrorType.ILLEGAL_PHASE);
            throw new Exception();
            //throw new Error(ErrorType.ILLEGAL_PHASE);
        }
        return;
    }
    public void sendMessage(String nickname,MessageFromServerType messageFromServerType){
        sendMessages.sendMessage(game.getTurnPlayer().getNickname(),null, messageFromServerType);
    }
    public void sendError(ErrorType errorType){
        sendMessages.sendMessage(game.getTurnPlayer().getNickname(),errorType.getErrorMessage(),MessageFromServerType.ERROR);
    }

    public void setSendMessages(SendMessages sendMessages) {
        this.sendMessages = sendMessages;
    }

    /*
    public HashMap<String, Client> getPlayerMap() {
        return playerMap;
    }

    public void setPlayerMap(HashMap<String, Client> playerMap) {
        this.playerMap = playerMap;
    }
    protected void addClient(String nickname, Client player) {
        this.playerMap.put(nickname, player);
    }

    protected void removeClient(String nickname) {
        this.playerMap.remove(nickname);
    }


    public SendMessages getSendMessages() {
        return sendMessages;
    }

    public void setSendMessages(SendMessages sendMessages) {
        this.sendMessages = sendMessages;
    }

     */
}
