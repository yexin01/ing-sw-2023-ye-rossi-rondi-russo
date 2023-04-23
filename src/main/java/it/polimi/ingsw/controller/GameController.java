package it.polimi.ingsw.controller;

import it.polimi.ingsw.messages.ErrorType;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.ServerView;

import java.util.List;


public class GameController {

    private ServerView serverView;

    //private HashMap<String, Client> playerMap;
    //private ListenerManager listenerManager;

    /*
        private SetupController setupController;

     */
    private PhaseController phaseController;

    private Game game;
    //private transient final PropertyChangeSupport listeners=new PropertyChangeSupport(this);

    public GameController( Game game) throws Exception {
        //this.listenerManager=new ListenerManager(serverView);
        this.game=game;
        initializeControllers();
    }

    public PhaseController getTurnController() {
        return phaseController;
    }
    public Game getModel() {
        return game;
    }
    public void initializeControllers() throws Exception {
        GameRules gameRules=new GameRules();
        int maxPlayers=gameRules.getMaxPlayers();
        phaseController =new PhaseController(game);
        phaseController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    public void setTurnController(PhaseController phaseController) {
        this.phaseController = phaseController;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public void receiveMessageFromClient(MessageFromClient message){
        String nicknamePlayer= message.getNicknameSender();
        try{
            if (!nicknamePlayer.equals(phaseController.getTurnPlayer().getNickname())) {
                serverView.sendError(ErrorType.ILLEGAL_TURN,nicknamePlayer);
                //throw new Error(ErrorType.ILLEGAL_TURN);
            }
            switch (message.getClientMessage()) {
                case COORDINATES ->  checkAndInsertBoardBox(message);
                case FINISH_SELECTION->associatePlayerTiles();
                case RESET_BOARD_CHOICE -> resetBoardChoice();
                case ORDER_TILE -> permutePlayerTiles(message);
                case COLUMN->selectingColumn(message);
                case INSERT_TILE_AND_POINTS -> insertBookshelf();
                default -> throw new IllegalArgumentException();
            }
        }catch(Exception e){
        }
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
        serverView.firePlayer(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }
    public void resetBoardChoice() throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getBoard().resetBoardChoice();
        serverView.firePlayer(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }

    public void checkError(ErrorType possibleInvalidArgoment) throws Exception {
        if(possibleInvalidArgoment!=null){
            serverView.sendError(possibleInvalidArgoment,getTurnNickname());
            throw new Exception();
        };
    }

    public void associatePlayerTiles() throws Exception {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        checkError(game.getBoard().checkFinishChoice());
        phaseController.changePhase();
        game.getTurnPlayer().selection(game.getBoard());
        serverView.firePlayer(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }
    public void permutePlayerTiles(MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_ORDER_TILES);
        int[] orderTiles=message.getValue();
        checkError(game.getTurnPlayer().checkPermuteSelection(orderTiles));
        phaseController.changePhase();
        game.getTurnPlayer().permuteSelection(orderTiles);
        serverView.firePlayer(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }

    public void selectingColumn(MessageFromClient message) throws Exception {
        illegalPhase(TurnPhase.SELECT_COLUMN);
        int column=message.getValue()[0];
        System.out.println("You selected "+column);
        checkError(game.getTurnPlayer().getBookshelf().checkBookshelf(column,game.getTurnPlayer().getSelectedItems().size()));
        phaseController.changePhase();
        game.getTurnPlayer().getBookshelf().setColumnSelected(column);
        serverView.firePlayer(null,MessageFromServerType.RECEIVE,getTurnNickname());
    }

    public void insertBookshelf() throws Exception {
        illegalPhase(TurnPhase.INSERT_BOOKSHELF_AND_POINTS);
        phaseController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
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
        serverView.firePlayer(null,MessageFromServerType.START_TURN,getTurnNickname());
        return true;
    }

    public void endGame() {
        //TODO change END GAME
        List<Player> ranking=  game.checkWinner();
        MessagePayload payload=new MessagePayload(null);
        payload.put(PayloadKeyServer.RANKING,ranking);
        //TODO
        //sendMessages.sendAll(payload,MessageFromServerType.END_GAME);
        //sendMessages.sendMessage(game.getTurnPlayer().getNickname(),null,MessageFromServerType.END_GAME);
        //endGameListener.endGame(ranking);
    }

    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!phaseController.getCurrentPhase().equals(phase)){
            serverView.sendError(ErrorType.ILLEGAL_PHASE,getTurnNickname());
            throw new Exception();
            //throw new Error(ErrorType.ILLEGAL_PHASE);
        }
        return;
    }
    public String getTurnNickname() {
        return game.getTurnPlayer().getNickname();
    }

    public ServerView getServerView() {
        return serverView;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
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
