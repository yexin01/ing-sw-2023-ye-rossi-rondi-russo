package it.polimi.ingsw.controller;

import it.polimi.ingsw.listeners.*;


import it.polimi.ingsw.json.GameRules;


import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.network.server.GameLobby;

import java.util.ArrayList;
import java.util.Arrays;


public class GameController {

    private ListenerManager listenerManager;
    //private PhaseController<TurnPhase> turnPhaseController;

    private Game game;


    public void createGame(GameLobby gameLobby, ArrayList<String> nicknames,InfoAndEndGameListener infoAndEndGameListener) throws Exception {
        listenerManager=new ListenerManager();
        //turnPhaseController =new PhaseController<>(TurnPhase.SELECT_FROM_BOARD);
        listenerManager.addListener(KeyErrorPayload.ERROR_DATA,new ErrorListener(gameLobby));
        listenerManager.addListener(TurnPhase.ALL_INFO, infoAndEndGameListener);
        listenerManager.addListener(TurnPhase.END_GAME, infoAndEndGameListener);
        listenerManager.addListener(TurnPhase.END_TURN,new EndTurnListener(gameLobby));
        listenerManager.addListener(Data.PHASE,new TurnListener(gameLobby));
        gameLobby.setStartAndEndGameListener(infoAndEndGameListener);
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

    public Game getModel() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }


    public void receiveMessageFromClient(Message message){
        System.out.println("IL PROSSIMO GIOCATORE E: "+getModel().getIntByNickname(getTurnNickname()));
        int i=0;
        Boolean[] activePlayers=game.getModelView().getActivePlayers();
        for(boolean p:activePlayers){
            System.out.println("GIOCATORE: "+game.getPlayers().get(i).getNickname()+" attivo: "+activePlayers[i++]);
        }
        String nicknamePlayer= message.getHeader().getNickname();
        try{
            if (!nicknamePlayer.equals(game.getModelView().getTurnNickname())) {
                System.out.println("ERRORE DI TURNO");
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



    public void checkAndInsertBoardBox(Message message) throws Exception {
        int[] coordinates=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        int maxPlayerSelectableTiles=game.getTurnPlayerOfTheGame().getBookshelf().numSelectableTiles();
        if(!checkError(game.getBoard().checkSelectable(coordinates,maxPlayerSelectableTiles))){
            game.getTurnPlayerOfTheGame().selection(game.getBoard());
            //turnPhaseController.setCurrentPhase(TurnPhase.SELECT_ORDER_TILES);
            game.getModelView().setTurnPhase(TurnPhase.SELECT_ORDER_TILES);
            System.out.println("CAMBIA FASE CONTROLLER");
            listenerManager.fireEvent(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_ORDER_TILES);
        }
    }
    public boolean checkError(ErrorType possibleInvalidArgoment) throws Exception {
        if(possibleInvalidArgoment!=null){
            System.out.println("ERRORE NEL CONTROLLER RILEVATO: "+possibleInvalidArgoment.getErrorMessage());
            System.out.println(possibleInvalidArgoment.getErrorMessage());
            listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,getTurnNickname(),possibleInvalidArgoment);
            return true;
        }
        return false;

    }
    public void disconnectionPlayer(String nickname){
        game.getModelView().setActivePlayers(game.disconnectionAndReconnectionPlayer(nickname,false));
        if(nickname.equals(getTurnNickname()) && Arrays.stream(getActivePlayers()).filter(element -> element).count()>1){
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getModelView().setNextPlayer();
            listenerManager.fireEvent(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }
    }
    public void reconnectionPlayer(String nickname){
        System.out.println("RICONNESSSO "+nickname.equals(getTurnNickname()));
        game.getModelView().setActivePlayers(game.disconnectionAndReconnectionPlayer(nickname,true));
        if(Arrays.stream(getActivePlayers()).filter(element -> element).count()==2 && !getActivePlayers()[game.turnPlayerInt()]){
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getModelView().setNextPlayer();
        }

    }

    public void permutePlayerTiles(Message message) throws Exception {
        int[] orderTiles=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        ErrorType errorType=game.getTurnPlayerOfTheGame().checkPermuteSelection(orderTiles);
        if(errorType!=null){
            checkError(errorType);
        }else {
            game.getTurnPlayerOfTheGame().permuteSelection(orderTiles);
            //turnPhaseController.setCurrentPhase(TurnPhase.SELECT_COLUMN);
            game.getModelView().setTurnPhase(TurnPhase.SELECT_COLUMN);
            System.out.println("CAMBIA FASE");
            listenerManager.fireEvent(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_COLUMN);
        }
    }

    public void selectingColumn(Message message) throws Exception {
        int column=(int) message.getPayload().getContent(Data.VALUE_CLIENT);
        System.out.println("You selected "+column);
        ErrorType errorType=game.getTurnPlayerOfTheGame().getBookshelf().checkBookshelf(column,game.getTurnPlayerOfTheGame().getSelectedItems().size());
        if(errorType!=null){
            checkError(errorType);
        }else{
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getTurnPlayerOfTheGame().insertBookshelf(column);
            System.out.println("finish1");
            if(game.getTurnPlayerOfTheGame().getBookshelf().isFull()){
                game.setEndGame(true);
            }
            game.updateAllPoints();
            finishTurn();
        }
    }


    public void finishTurn() {
        game.getBoard().resetBoard();
        Boolean[] activePlayers=game.getModelView().getActivePlayers();
        System.out.println("ULTIMO GIOCATORE CONNESSO ATTIVO è:"+game.getLastPlayer(activePlayers));
        if(game.isEndGame() && getTurnNickname().equals(game.getLastPlayer(activePlayers))){
            endGame();
        }else{
            if(game.getBoard().checkRefill()){
                game.getBoard().refill();
            }
            game.getModelView().setNextPlayer();
            System.out.println("Il prossimo giocatore é "+game.getModelView().getTurnNickname());
            listenerManager.fireEvent(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }
    }
    public void endGame(){
        game.getModelView().winnerEndGame();
        Arrays.fill(game.getModelView().getActivePlayers(), true);
        listenerManager.fireEvent(TurnPhase.END_GAME,getTurnNickname(),game.getModelView());
    }


    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!getModel().getModelView().getTurnPhase().equals(phase)){
            listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,getTurnNickname(),ErrorType.ILLEGAL_PHASE);
            throw new Exception();
        }
        return;
    }
    public String getTurnNickname() {
        return game.getTurnPlayerOfTheGame().getNickname();
    }

    public ListenerManager getListenerManager() {
        return listenerManager;
    }

    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }

    public Boolean[] getActivePlayers() {
        return game.getModelView().getActivePlayers();
    }

}
