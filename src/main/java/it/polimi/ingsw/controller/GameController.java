package it.polimi.ingsw.controller;

import it.polimi.ingsw.listeners.*;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.KeyPayload;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.network.server.ErrorType;

import it.polimi.ingsw.json.GameRules;


import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;

import java.util.ArrayList;
import java.util.List;


public class GameController {

    private ListenerManager listenerManager;


    private PhaseController<TurnPhase> turnPhaseController;
    private Game game;


    public GameController(GameLobby gameLobby, ArrayList<String> nicknames) throws Exception {
        listenerManager=new ListenerManager();
        turnPhaseController =new PhaseController<>(TurnPhase.SELECT_FROM_BOARD);
        listenerManager.addListener(EventType.ERROR,new ErrorListener(gameLobby));
        listenerManager.addListener(EventType.START_GAME,new StartAndEndGameListener(gameLobby));
        GameRules gameRules=new GameRules();
        ModelView modelView=new ModelView(nicknames.size(), gameRules,listenerManager);

        game=new Game(gameRules, nicknames.size(),modelView);
        game.addPlayers(nicknames);

        game.getBoard().fillBag(gameRules);
        game.getBoard().firstFillBoard(nicknames.size(), gameRules);

        game.createCommonGoalCard(gameRules);
        game.createPersonalGoalCard(gameRules);

        modelView.addListener(EventType.BOARD_SELECTION,new FinishSelectionListener(gameLobby));
        modelView.addListener(EventType.END_TURN,new EndTurnListener(gameLobby));
        listenerManager.fireEvent(EventType.START_GAME,null,game.getModelView());
    }

    public Game getModel() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public void receiveMessageFromClient(MessageFromClient message){

        String nicknamePlayer= message.getNicknameSender();
        try{
            if (!nicknamePlayer.equals(game.getTurnPlayer().getNickname())) {
                listenerManager.fireEvent(EventType.ERROR,nicknamePlayer,ErrorType.ILLEGAL_TURN);
                //serverView.sendError(ErrorType.ILLEGAL_TURN,nicknamePlayer);
                //throw new Error(ErrorType.ILLEGAL_TURN);
            }

            switch (message.getEvent()) {
                case BOARD_SELECTION ->  checkAndInsertBoardBox(message);
                //case FINISH_SELECTION->associatePlayerTiles();
                //case RESET_BOARD_CHOICE -> resetBoardChoice();
                case ORDER_TILES-> permutePlayerTiles(message);
                case COLUMN->selectingColumn(message);
                //case INSERT_TILE_AND_POINTS -> insertBookshelf();
                case ABANDON_GAME -> removePlayer(message.getNicknameSender());
                default -> throw new IllegalArgumentException();
            }
        }catch(Exception e){
        }
    }

    public void removePlayer(String nicknameSender) {
        int index=game.deletePlayer(nicknameSender);
        if(index==game.getPlayers().size()){
            game.setTurnPlayer(0);
            if(game.isEndGame()){
                endGame();
                return;
            }
        }
        //TODO cancellare dalle connessioni
        //serverView.removeClient(nicknameSender);

        //TODO inviare mes
        //serverView.sendMessage(null, MessageFromServerType.START_TURN,getTurnNickname());
    }

    public void checkAndInsertBoardBox( MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        int[] coordinates=message.getValue();
        int x=coordinates[0];
        int y=coordinates[1];
        checkError(game.getBoard().checkCoordinates(x,y));
        System.out.println("You selected "+x+","+y);
        BoardBox boardBox=game.getBoard().getBoardBox(x,y);
        int maxPlayerSelectableTiles=game.getTurnPlayer().getBookshelf().numSelectableTiles();
        checkError(game.getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles));
        //serverView.sendMessage(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }
    public void resetBoardChoice() throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getBoard().resetBoardChoice();
        //TODO inviare mes
        //serverView.sendMessage(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }

    public void checkError(ErrorType possibleInvalidArgoment) throws Exception {
        if(possibleInvalidArgoment!=null){
            listenerManager.fireEvent(EventType.ERROR,getTurnNickname(),possibleInvalidArgoment);
            //serverView.sendError(possibleInvalidArgoment,getTurnNickname());
            throw new Exception();
        };
    }

    public void associatePlayerTiles() throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        checkError(game.getBoard().checkFinishChoice());
        turnPhaseController.changePhase();
        game.getTurnPlayer().selection(game.getBoard());
    }
    public void permutePlayerTiles(MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_ORDER_TILES);
        int[] orderTiles=message.getValue();
        checkError(game.getTurnPlayer().checkPermuteSelection(orderTiles));
        turnPhaseController.changePhase();
        game.getTurnPlayer().permuteSelection(orderTiles);
        //serverView.sendMessage(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }

    public void selectingColumn(MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_COLUMN);
        int column=message.getValue()[0];
        System.out.println("You selected "+column);
        checkError(game.getTurnPlayer().getBookshelf().checkBookshelf(column,game.getTurnPlayer().getSelectedItems().size()));
        turnPhaseController.changePhase();
        game.getTurnPlayer().getBookshelf().setColumnSelected(column);
        //serverView.sendMessage(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }

    public void insertBookshelf() throws Exception {
        illegalPhase(TurnPhase.END_TURN);
        turnPhaseController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
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
            return ;
        }
        if(game.getBoard().checkRefill()){
            game.getBoard().refill();
        }
        game.setNextPlayer();
        //serverView.sendMessage(null,MessageFromServerType.START_TURN,getTurnNickname());
    }

    public void endGame() {
        //TODO change END GAME
        List<String> ranking=  game.checkWinner();
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.PLAYERS,ranking);
        //serverView.sendAllMessage(payload,MessageFromServerType.END_GAME);
        //TODO set GamePhase a END_GAME
        //sendMessages.sendAll(payload,MessageFromServerType.END_GAME);
        //sendMessages.sendMessage(game.getTurnPlayer().getNickname(),null,MessageFromServerType.END_GAME);
        //endGameListener.endGame(ranking);
    }

    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!turnPhaseController.getCurrentPhase().equals(phase)){
            listenerManager.fireEvent(EventType.ERROR,getTurnNickname(),ErrorType.ILLEGAL_PHASE);
            //serverView.sendError(ErrorType.ILLEGAL_PHASE,getTurnNickname());
            throw new Exception();
            //throw new Error(ErrorType.ILLEGAL_PHASE);
        }
        return;
    }
    public String getTurnNickname() {
        return game.getTurnPlayer().getNickname();
    }
/*
    public ServerView getServerView() {
        return serverView;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
    }

 */

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }


    /*
    public void sendMessage(String nickname,MessageFromServerType messageFromServerType){
        sendMessages.sendMessage(game.getTurnPlayer().getNickname(),null, messageFromServerType);
    }

     */


    /*
    public void addListener(EventType eventType, EventListener listener) {
        this.listenerManager.addListener(eventType,listener);
    }

    public void removeListener(EventType eventType, EventListener listener) {
        this.listenerManager.removeListener(eventType, listener);
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
