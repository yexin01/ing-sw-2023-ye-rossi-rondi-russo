package it.polimi.ingsw.controller;

import it.polimi.ingsw.listeners.*;


import it.polimi.ingsw.json.GameRules;


import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * GameController: handles shift phase messages.
 */

public class GameController {

    private ListenerManager listenerManager;
    //private PhaseController<TurnPhase> turnPhaseController;

    private Game game;

    /**
     *Create the game and bind all listeners.
     * @param gameLobby
     * @param nicknames:names of players of the current game;
     * @throws Exception
     */
    public void createGame(GameLobby gameLobby, ArrayList<String> nicknames) throws Exception {
        addListeners(gameLobby);
        GameRules gameRules=new GameRules();
        ModelView modelView=new ModelView(nicknames.size(), gameRules);
        modelView.setTurnPhase(TurnPhase.ALL_INFO);
        game=new Game(modelView);
        game.addPlayers(nicknames);

        game.getBoard().fillBag(gameRules);
        game.getBoard().firstFillBoard(nicknames.size(), gameRules);
        int numOfCommonGoals = gameRules.getNumOfCommonGoals();
        int numOfPossibleCommonGoalsCards = gameRules.getCommonGoalCardsSize();
        ArrayList<Integer> numbers = game.generateRandomNumber(numOfPossibleCommonGoalsCards, numOfCommonGoals);
        game.createCommonGoalCard(gameRules,numbers);

        numbers = game.generateRandomNumber(gameRules.getPossiblePersonalGoalsSize(), game.getPlayers().size());
        game.createPersonalGoalCard(gameRules,numbers);
        gameLobby.setModelView(modelView);
        listenerManager.fireEvent(TurnPhase.ALL_INFO,null,game.getModelView());
        modelView.setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    /**
     * Associate listeners.
     * @param gameLobby
     */
    public void addListeners(GameLobby gameLobby){
        listenerManager=new ListenerManager();
        listenerManager.addListener(KeyErrorPayload.ERROR_DATA,new ErrorListener(gameLobby));
        listenerManager.addListener(TurnPhase.ALL_INFO, gameLobby.getStartAndEndGameListener());
        listenerManager.addListener(TurnPhase.END_GAME, gameLobby.getStartAndEndGameListener());
        listenerManager.addListener(TurnPhase.END_TURN,new EndTurnListener(gameLobby));
        listenerManager.addListener(Data.PHASE,new PhaseListener(gameLobby));
    }

    public Game getModel() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Check that the received message comes from the current player and that the phase is the correct one,
     * then call the functions related to the phase.
     * If it doesn't pass the checks it awakens the ErrorListener.
     * @param message: message from the client;
     */
    public void receiveMessageFromClient(Message message){
        String nicknamePlayer= message.getHeader().getNickname();
        try{
            if (!nicknamePlayer.equals(game.getModelView().getTurnNickname())) {
                listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,nicknamePlayer, ErrorType.ILLEGAL_TURN);

            }else{
                illegalPhase((TurnPhase) message.getPayload().getKey());
                switch (getModel().getModelView().getTurnPhase()) {
                    case SELECT_FROM_BOARD ->  checkAndInsertBoardBox(message);
                    case SELECT_ORDER_TILES-> permutePlayerTiles(message);
                    case SELECT_COLUMN->selectingColumn(message);
                }
            }
        }catch(Exception e){
        }
    }


    /**
     * Tiles selection phase, if it passes all the checks it sets the turnPhase of the modelView=TurnPhase.SELECT_ORDER_TILES
     * and the tiles selected by the client are updated in the modelView;
     * @param message: message from the client;
     * @throws Exception
     */
    public void checkAndInsertBoardBox(Message message) throws Exception {
        int[] coordinates=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        int maxPlayerSelectableTiles=game.getTurnPlayerOfTheGame().getBookshelf().numSelectableTiles();
        if(!checkError(game.getBoard().checkSelectable(coordinates,maxPlayerSelectableTiles))){
            game.getTurnPlayerOfTheGame().selection(game.getBoard());
            game.getModelView().setTurnPhase(TurnPhase.SELECT_ORDER_TILES);
            System.out.println("CAMBIA FASE CONTROLLER");
            send(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_ORDER_TILES);
        }
    }

    /**
     *in case of error it awakens the errorListener;
     * @param possibleInvalidArgoment: ErrorType if it is null it means that it has passed the checks;
     * @return: true if it has not passed the checks, false otherwise;
     * @throws Exception
     */
    public boolean checkError(ErrorType possibleInvalidArgoment) throws Exception {
        if(possibleInvalidArgoment!=null){
            System.out.println(possibleInvalidArgoment.getErrorMessage());
            listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,getTurnNickname(),possibleInvalidArgoment);
            return true;
        }
        return false;

    }

    /**
     * Order phase,changes the order of the selected tiles, if it passes all the checks it sets the turnPhase
     * of the modelView=TurnPhase.SELECT_COLUMN.
     * and the tiles order is updated in the modelView;
     * @param message: message from the client;
     * @throws Exception
     */

    public void permutePlayerTiles(Message message) throws Exception {
        int[] orderTiles=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        ErrorType errorType=game.getTurnPlayerOfTheGame().checkPermuteSelection(orderTiles);
        if(errorType!=null){
            checkError(errorType);
        }else {
            game.getTurnPlayerOfTheGame().permuteSelection(orderTiles);
            //turnPhaseController.setCurrentPhase(TurnPhase.SELECT_COLUMN);
            game.getModelView().setTurnPhase(TurnPhase.SELECT_COLUMN);
            //System.out.println("CAMBIA FASE");
            send(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_COLUMN);
            //listenerManager.fireEvent();
        }
    }

    /**
     * if it passes all the checks, it updates the players' scores and sets the turnPhase
     * of the modelView=TurnPhase.SELECT_FROM_BOARD.
     * @param message: message from the client;
     * @throws Exception
     */
    public void selectingColumn(Message message) throws Exception {
        int column=(int) message.getPayload().getContent(Data.VALUE_CLIENT);
        ErrorType errorType=game.getTurnPlayerOfTheGame().getBookshelf().checkBookshelf(column,game.getTurnPlayerOfTheGame().getSelectedItems().size());
        if(errorType!=null){
            checkError(errorType);
        }else{
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getTurnPlayerOfTheGame().insertBookshelf(column);
            if(game.getTurnPlayerOfTheGame().getBookshelf().isFull()){
                game.setEndGame(true);
            }
            game.updateAllPoints();
            finishTurn();
        }
    }

    /**
     *end of turn: if pass all checks sets the next player and wakes up the EndTurnListener;
     */
    public void finishTurn() {
        game.getBoard().resetBoard();
        Boolean[] activePlayers=game.getModelView().getActivePlayers();
        //System.out.println("ULTIMO GIOCATORE CONNESSO ATTIVO è:"+game.getLastPlayer(activePlayers));
        if(game.isEndGame() && getTurnNickname().equals(game.getLastPlayer(activePlayers))){
            endGame();
        }else{
            if(game.getBoard().checkRefill()){
                game.getBoard().refill();
            }
            game.getModelView().setNextPlayer();
            //System.out.println("Il prossimo giocatore é "+game.getModelView().getTurnNickname());
            send(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
           // listenerManager.fireEvent();
        }
    }

    /**
     *Awakens InfoAndEndGameListener,then the endGame message will be sent;
     */
    public void endGame(){
        game.getModelView().winnerEndGame();
        Arrays.fill(game.getModelView().getActivePlayers(), true);
        listenerManager.fireEvent(TurnPhase.END_GAME,getTurnNickname(),game.getModelView());
    }

    /**
     * if it fails the phase check it calls the errorListener.
     * @param phase:phase of the client message
     * @throws Exception
     */
    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!getModel().getModelView().getTurnPhase().equals(phase)){
            listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,getTurnNickname(),ErrorType.ILLEGAL_PHASE);
            throw new Exception();
        }
        return;
    }

    /**
     *If the number of active players is greater than 1, it awakens the relative listener,
     *otherwise it means that: -no player is connected or -only one player is connected, in this case
     * the data has been updated to the last phase received  but the message for the next phase is not sent.
     * @param event:error, end of phase or end of turn;
     * @param playerNickname: current player or next player,it depends on the phase;
     * @param newValue: modelView or current phase;
     */
    public void send(KeyAbstractPayload event, String playerNickname, Object newValue){
        if(Arrays.stream(getActivePlayers()).filter(element -> element).count()>1){
            listenerManager.fireEvent(event,playerNickname,newValue);
        }
    }

    /**
     *Called from the gameLobby after a player disconnects: if connected players are >1 and the disconnected player
     * is the turnPlayer,set next player and wakes up EndTurnLstener.
     * @param nickname:player disconnected;
     */
    public void disconnectionPlayer(String nickname){
        game.getModelView().setActivePlayers(game.disconnectionAndReconnectionPlayer(nickname,false));
        if(nickname.equals(getTurnNickname()) && Arrays.stream(getActivePlayers()).filter(element -> element).count()>1){
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getModelView().setNextPlayer();
            listenerManager.fireEvent(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }
    }

    /**
     * If the number of connected players is =2 it wakes up InfoAndEndGameListener,
     * furthermore if the turnPlayer is not active it sets the next active player.
     * @param nickname:player reconnected;
     */
    public void reconnectionPlayer(String nickname){
        //System.out.println("RICONNESSSO "+nickname.equals(getTurnNickname()));
        game.getModelView().setActivePlayers(game.disconnectionAndReconnectionPlayer(nickname,true));
        if(Arrays.stream(getActivePlayers()).filter(element -> element).count()==2){
            if(!getActivePlayers()[game.turnPlayerInt()]){
                game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
                game.getModelView().setNextPlayer();
            }
            listenerManager.fireEvent(TurnPhase.ALL_INFO,null,game.getModelView());
        }

    }

    /**
     *
     * @return:nickname of the current player
     */

    public String getTurnNickname() {
        return game.getTurnPlayerOfTheGame().getNickname();
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    /**
     *
     * @return:boolean array of players indicating who is connected;
     */

    public Boolean[] getActivePlayers() {
        return game.getModelView().getActivePlayers();
    }

}
