package it.polimi.ingsw.controller;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.listeners.TurnListener;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessageFromClientType;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.HashMap;
import java.util.List;


public class GameController implements Controller{


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


    @Override
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
/*
    public void turnPhase(MessageFromClient message) throws Exception {
        MessageFromClientType messageName = message.getClientMessageHeader().getMessageName();
        MessagePayload payload = message.getMessagePayload();
        switch (messageName) {
            case SELECTION_BOARD -> checkAndInsertBoardBox(message);
            case RESET_BOARD_CHOICE -> resetBoardChoice();
            case FINISH_SELECTION -> associatePlayerTiles();
            case SELECT_ORDER_TILES -> permutePlayerTiles(message);
            case SELECT_COLUMN -> selectingColumn(message);
            case INSERT_BOOKSHELF -> insertBookshelf();
            default -> throw new IllegalArgumentException();
        }

    }

 */


    public void checkAndInsertBoardBox( MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        int[] coordinates=((int[])message.getMessagePayload().getObject());
        int x=coordinates[0];
        int y=coordinates[1];
        game.getBoard().checkCoordinates(x,y);
        System.out.println("You selected "+x+","+y);
        BoardBox boardBox=game.getBoard().getBoardBox(x,y);
        int maxPlayerSelectableTiles=game.getTurnPlayer().getBookshelf().numSelectableTiles();
        game.getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles);

    }
    public void resetBoardChoice() throws Error {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getBoard().resetBoardChoice();

    }

    public void associatePlayerTiles() throws Error {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getTurnPlayer().selection(game.getBoard());
        finishPhase();
        turnController.changePhase();
    }
    public void permutePlayerTiles(MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_ORDER_TILES);
        int[] orderTiles=((int[])message.getMessagePayload().getObject());
        game.getTurnPlayer().checkPermuteSelection(orderTiles);
        game.getTurnPlayer().permuteSelection(orderTiles);
        finishPhase();
        turnController.changePhase();

    }


    public void selectingColumn(MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_COLUMN);
        int column=(int)message.getMessagePayload().getObject();
        System.out.println("You selected "+column);
        game.getTurnPlayer().getBookshelf().checkBookshelf(column,game.getTurnPlayer().getSelectedItems().size());
        game.getTurnPlayer().getBookshelf().setColumnSelected(column);
        finishPhase();
        turnController.changePhase();
    }

    public void insertBookshelf() throws Exception {
        illegalPhase(TurnPhase.INSERT_BOOKSHELF_AND_POINTS);
        game.getTurnPlayer().insertBookshelf();
        if(game.getTurnPlayer().getBookshelf().isFull()){
            game.setEndGame(true);
        }
        game.updateAllPoints();
        finishTurn();

    }
    public void finishTurn() {
        //TODO da finire
        if(game.isEndGame() && game.getTurnPlayer().equals(game.getLastPlayer())){
            endGame();
            return;
        }
        if(game.getBoard().checkRefill()){
            game.getBoard().refill();
        }
        game.setNextPlayer();
        endTurn();
        turnController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    public void endGame() {
        //TODO change
        List<Player> ranking=  game.checkWinner();
        TurnListener endGameListener=new TurnListener();
        endGameListener.endGame(ranking);
    }

    public void illegalPhase(TurnPhase phase) throws Error {
        if(!turnController.getCurrentPhase().equals(phase)){
            throw new Error(ErrorType.ILLEGAL_PHASE);
        }
        return;
    }
    public void finishPhase(){
        TurnListener turnListener=new TurnListener();
        turnListener.endPhase(game.getTurnPlayer().getNickname(),turnController.getCurrentPhase());
    }

    public void endTurn(){
        TurnListener turnListener=new TurnListener();
        turnListener.endTurnMessage(game.getTurnPlayer().getNickname());
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

     */
}
