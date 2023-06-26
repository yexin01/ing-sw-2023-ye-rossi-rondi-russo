package it.polimi.ingsw.controller;

import it.polimi.ingsw.listeners.*;


import it.polimi.ingsw.json.GameRules;


import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.persistence.SaveGame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The GameController class handles the logic and flow of the game.
 * It is responsible for processing messages received from the clients, updating the game state, and notifying listeners accordingly.
 */

public class GameController {

    private ListenerManager listenerManager;


    private Game game;

    /**
     * Creates a new game with the specified lobby and player nicknames.
     * Initializes the game and model view.
     * @param gameLobby The game lobby containing the players of the game.
     * @throws Exception if an error occurs during game creation.
     */
    public void createGame(GameLobby gameLobby) throws Exception {
        addListeners(gameLobby);
        ArrayList<String> nicknames=new ArrayList<>();
        for (String player : gameLobby.getPlayersInGameLobby().keySet()) { nicknames.add(player); }
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
        gameLobby.getGameLobbyInfo().setModelView(modelView);
        listenerManager.fireEvent(TurnPhase.ALL_INFO,null,game.getModelView());
        modelView.setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
        gameLobby.getGameLobbyInfo().setGameLobbyState(gameLobby);
        SaveGame.saveGame(gameLobby.getGameLobbyInfo());
    }

    /**
     * Associates listeners with the game lobby.
     * @param gameLobby The game lobby to associate listeners with.
     */
    public void addListeners(GameLobby gameLobby) throws IOException {
        listenerManager=new ListenerManager();
        listenerManager.addListener(KeyErrorPayload.ERROR_DATA,new ErrorListener(gameLobby));
        listenerManager.addListener(TurnPhase.ALL_INFO, gameLobby.getStartAndEndGameListener());
        listenerManager.addListener(TurnPhase.END_GAME, gameLobby.getStartAndEndGameListener());
        listenerManager.addListener(TurnPhase.END_TURN,new EndTurnListener(gameLobby));
        listenerManager.addListener(Data.PHASE,new PhaseListener(gameLobby));
        //gameLobby.getGameLobbyInfo().setGameLobbyState(gameLobby);
        //SaveGame.saveGame(gameLobby.getGameLobbyInfo());
    }
    /**
     * Returns the game model.
     * @return The game model.
     */
    public Game getModel() {
        return game;
    }
    /**
     * Sets the game model.
     * @param game The game model to be set.
     */
    public void setGame(Game game) {
        this.game = game;
    }


    /**
     * Receives a message from the client and performs the corresponding actions based on the current player and phase.
     * If the message is not from the current player or the phase is incorrect, it triggers the ErrorListener.
     * @param message The message received from the client.
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
     * Checks if there is an error and triggers the errorListener if an error is found.
     * @param possibleInvalidArgument The ErrorType indicating a possible error. If null, it means that no error has occurred.
     * @return True if an error is found, false otherwise.
     * @throws Exception If an exception occurs during the process.
     */
    public boolean checkError(ErrorType possibleInvalidArgument) throws Exception {
        if(possibleInvalidArgument!=null){
            listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,getTurnNickname(),possibleInvalidArgument);
            return true;
        }
        return false;

    }
    /**
     * Checks and inserts the selected board box during the tiles selection phase.
     * If all checks pass, it sets the turnPhase of the modelView to TurnPhase.SELECT_ORDER_TILES
     * and updates the tiles selected by the client in the modelView.
     @param message The message from the client.
     @throws Exception If an error occurs during the process.
     */
    public void checkAndInsertBoardBox(Message message) throws Exception {
        int[] coordinates=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        int maxPlayerSelectableTiles=game.getTurnPlayerOfTheGame().getBookshelf().numSelectableTiles();
        if(!checkError(game.getBoard().checkSelectable(coordinates,maxPlayerSelectableTiles))){
            game.getTurnPlayerOfTheGame().setSelectedItems(game.getBoard().selected());
            game.getTurnPlayerOfTheGame().cloneTilesSelected();
            game.getModelView().setTurnPhase(TurnPhase.SELECT_ORDER_TILES);
            send(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_ORDER_TILES);
        }
    }



    /**
     * Permute the order of the selected tiles in the order phase.
     * If all checks pass, it sets the turnPhase of the modelView to TurnPhase.SELECT_COLUMN
     * and updates the tile order in the modelView.
     * @param message The message from the client.
     * @throws Exception If an exception occurs during the process.
     */

    public void permutePlayerTiles(Message message) throws Exception {
        int[] orderTiles=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        ErrorType errorType=game.getTurnPlayerOfTheGame().checkPermuteSelection(orderTiles);
        if(errorType!=null){
            checkError(errorType);
        }else {
            game.getTurnPlayerOfTheGame().permuteSelection(orderTiles);
            game.getModelView().setTurnPhase(TurnPhase.SELECT_COLUMN);
            send(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_COLUMN);
        }
    }


    /**
     * Process the column selection phase.
     * If all checks pass, it updates the players' scores and sets the turnPhase of the modelView to TurnPhase.SELECT_FROM_BOARD.
     * @param message The message from the client.
     * @throws Exception If an exception occurs during the process.
     */
    public void selectingColumn(Message message) throws Exception {
        int column=(int) message.getPayload().getContent(Data.VALUE_CLIENT);
        ErrorType errorType=game.getTurnPlayerOfTheGame().getBookshelf().checkBookshelf(column,game.getTurnPlayerOfTheGame().getSelectedItems().size());
        if(errorType!=null){
            checkError(errorType);
        }else{
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getTurnPlayerOfTheGame().insertBookshelf(column);
            if(game.getTurnPlayerOfTheGame().getBookshelf().isFull()&& !game.isEndGame()){
                game.getModelView().setBookshelfFullPoints(game.getTurnPlayerOfTheGame().getNickname());
                game.setEndGame(true);
            }
            game.updateAllPoints();
            finishTurn();
        }
    }

    /**
     * Finish the current turn.
     * If all checks pass, it sets the next player and notifies the EndTurnListener.
     */
    public void finishTurn() {
        game.getBoard().changeBoardAfterUserChoice();
        if(game.isEndGame() && getTurnNickname().equals(game.getLastPlayer())){
            endGame();
        }else{
            if(game.getBoard().checkRefill()){
                game.getBoard().refill();
            }
            game.getModelView().setNextPlayer();
            send(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }
    }

    /**
     * End the game.
     * It notifies the InfoAndEndGameListener, and then the endGame message will be sent.
     */
    public void endGame(){
        game.getModelView().winnerEndGame();
        Arrays.fill(game.getModelView().getActivePlayers(), true);
        listenerManager.fireEvent(TurnPhase.END_GAME,getTurnNickname(),game.getModelView());
    }


    /**
     * Check if the received phase is a legal phase in the current game state.
     * If the phase is not valid, it calls the errorListener.
     * @param phase The phase of the client message.
     * @throws Exception If an exception occurs during the process.
     */
    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!getModel().getModelView().getTurnPhase().equals(phase)){
            listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,getTurnNickname(),ErrorType.ILLEGAL_PHASE);
            throw new Exception();
        }
    }

    /**
     * Send the specified event to the corresponding listener based on the number of active players.
     * If the number of active players is greater than 1, it awakens the relative listener.
     * Otherwise, it means that either no player is connected or only one player is connected. In this case,
     * the data has been updated to the last phase received, but the message for the next phase is not sent.
     * @param event The event to send (error, end of phase, or end of turn).
     * @param playerNickname The nickname of the current or next player, depending on the phase.
     * @param newValue The updated modelView or current phase.
     */
    public void send(KeyAbstractPayload event, String playerNickname, Object newValue){
        if(Arrays.stream(getActivePlayers()).filter(element -> element).count()>1){
            listenerManager.fireEvent(event,playerNickname,newValue);
        }
    }


    /**
     * Called from the gameLobby after a player disconnects. If there are connected players and the disconnected player is the current turn player,
     * it sets the next player and wakes up the EndTurnListener.
     * @param nickname The nickname of the disconnected player.
     */
    public void disconnectionPlayer(String nickname){
        game.getModelView().disconnectionAndReconnectionPlayer(nickname,false);
        if(nickname.equals(getTurnNickname()) && Arrays.stream(getActivePlayers()).filter(element -> element).count()>1){
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getModelView().setNextPlayer();
            listenerManager.fireEvent(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }
    }

    /**
     * Called from the gameLobby after a player reconnects. If there are two connected players, it wakes up the InfoAndEndGameListener.
     * Additionally, if the current turn player is not active, it sets the next active player.
     * @param nickname The nickname of the reconnected player.
     */
    public void reconnectionPlayer(String nickname){
        game.getModelView().disconnectionAndReconnectionPlayer(nickname,true);
        if(Arrays.stream(getActivePlayers()).filter(element -> element).count()==2){
            if(!getActivePlayers()[game.getModelView().getTurnPlayer()]){
                game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
                game.getModelView().setNextPlayer();
            }
            listenerManager.fireEvent(TurnPhase.ALL_INFO,null,game.getModelView());
        }

    }

    /**
     * Returns the nickname of the current turn player.
     * @return The nickname of the current turn player.
     */
    public String getTurnNickname() {
        return game.getTurnPlayerOfTheGame().getNickname();
    }


    /**
     * Returns the array of the modelView indicating the active status of each player.
     * @return An array of booleans where each element represents the active status of a player.
     */
    public Boolean[] getActivePlayers() {
        return game.getModelView().getActivePlayers();
    }

}
