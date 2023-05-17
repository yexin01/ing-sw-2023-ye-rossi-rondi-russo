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
import java.util.List;


public class GameController {

    private ListenerManager listenerManager;
    private PhaseController<TurnPhase> turnPhaseController;
    private boolean[] activePlayers;
    private Game game;


    public GameController(GameLobby gameLobby, ArrayList<String> nicknames,InfoAndEndGameListener infoAndEndGameListener) throws Exception {
        listenerManager=new ListenerManager();
        turnPhaseController =new PhaseController<>(TurnPhase.SELECT_FROM_BOARD);
        listenerManager.addListener(KeyErrorPayload.ERROR_DATA,new ErrorListener(gameLobby));
        listenerManager.addListener(TurnPhase.ALL_INFO, infoAndEndGameListener);
        listenerManager.addListener(TurnPhase.END_TURN,new EndTurnListener(gameLobby));
        listenerManager.addListener(Data.PHASE,new TurnListener(gameLobby));
        gameLobby.setStartAndEndGameListener(infoAndEndGameListener);
        activePlayers=new boolean[nicknames.size()];
        Arrays.fill(activePlayers, true);
        GameRules gameRules=new GameRules();
        ModelView modelView=new ModelView(nicknames.size(), gameRules,listenerManager);
        modelView.setTurnPhase(TurnPhase.ALL_INFO);
        game=new Game(gameRules, nicknames.size(),modelView);
        game.addPlayers(nicknames);

        game.getBoard().fillBag(gameRules);
        game.getBoard().firstFillBoard(nicknames.size(), gameRules);

        game.createCommonGoalCard(gameRules);
        game.createPersonalGoalCard(gameRules);
        modelView.setTurnPlayer(game.getPlayers().get(0).getNickname());

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
        String nicknamePlayer= message.getHeader().getNickname();
        try{
            if (!nicknamePlayer.equals(game.getTurnPlayerOfTheGame().getNickname())) {
                listenerManager.fireEvent(KeyErrorPayload.ERROR_DATA,nicknamePlayer, ErrorType.ILLEGAL_TURN);

            }
            illegalPhase((TurnPhase) message.getPayload().getKey());
            switch (turnPhaseController.getCurrentPhase()) {
                case SELECT_FROM_BOARD ->  checkAndInsertBoardBox(message);
                case SELECT_ORDER_TILES-> permutePlayerTiles(message);
                case SELECT_COLUMN->selectingColumn(message);
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
    }

    public void checkAndInsertBoardBox(Message message) throws Exception {
        int[] coordinates=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        int maxPlayerSelectableTiles=game.getTurnPlayerOfTheGame().getBookshelf().numSelectableTiles();
        if(!checkError(game.getBoard().checkSelectable(coordinates,maxPlayerSelectableTiles))){
            System.out.println("Le asocia");
            //ErrorType errorType=game.getBoard().checkFinishChoice();

            //if(errorType!=null){
             //   checkError(errorType);
           // }else{
              game.getTurnPlayerOfTheGame().selection(game.getBoard());
                //game.getBoard().resetBoard();
                turnPhaseController.setCurrentPhase(TurnPhase.SELECT_ORDER_TILES);
                game.getModelView().setTurnPhase(TurnPhase.SELECT_ORDER_TILES);
                System.out.println("CAMBIA FASE CONTROLLER");
                listenerManager.fireEvent(Data.PHASE,getTurnNickname(),TurnPhase.SELECT_ORDER_TILES);
            //}
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
        //TODO disconnection
        System.out.println(nickname.equals(getTurnNickname()));
        System.out.println("I GIOCATORI SONO1\n");
        activePlayers=game.disconnectionAndReconnectionPlayer(activePlayers,nickname,false);
        if(nickname.equals(getTurnNickname())){
            turnPhaseController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            System.out.println("I GIOCATORI SONO2\n");
            game.setNextPlayer(activePlayers);
            System.out.println("I GIOCATORI SONO3\n");
            int i=0;
            for (Player str : game.getPlayers()) {
                System.out.print(str.getNickname());
                System.out.println(activePlayers[i++]);
            }
            game.getModelView().setTurnPlayer(game.getTurnPlayerOfTheGame().getNickname());
            listenerManager.fireEvent(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }

    }
    public void reconnectionPlayer(String nickname){
        System.out.println(nickname.equals(getTurnNickname()));
        activePlayers=game.disconnectionAndReconnectionPlayer(activePlayers,nickname,true);

    }

    public void permutePlayerTiles(Message message) throws Exception {
        int[] orderTiles=(int[]) message.getPayload().getContent(Data.VALUE_CLIENT);
        ErrorType errorType=game.getTurnPlayerOfTheGame().checkPermuteSelection(orderTiles);
        if(errorType!=null){
            checkError(errorType);
        }else {
            game.getTurnPlayerOfTheGame().permuteSelection(orderTiles);
            turnPhaseController.setCurrentPhase(TurnPhase.SELECT_COLUMN);
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
            turnPhaseController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getModelView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
            game.getTurnPlayerOfTheGame().insertBookshelf(column);
            System.out.println("finish1");
            if(game.getTurnPlayerOfTheGame().getBookshelf().isFull()){
                game.setEndGame(true);
            }
            System.out.println("finish2");
            game.updateAllPoints();
            finishTurn();
        }
    }


    public void finishTurn() {
        game.getBoard().resetBoard();
        if(game.isEndGame() && game.getTurnPlayerOfTheGame().equals(game.getLastPlayer())){
            endGame();
            listenerManager.fireEvent(TurnPhase.ALL_INFO,getTurnNickname(),game.getModelView());
        }else{
            if(game.getBoard().checkRefill()){
                game.getBoard().refill();
            }

            game.setNextPlayer(activePlayers);
            System.out.println("Il prossimo giocatore é "+game.getTurnPlayerOfTheGame().getNickname());
            game.getModelView().setTurnPlayer(game.getTurnPlayerOfTheGame().getNickname());
            listenerManager.fireEvent(TurnPhase.END_TURN,getTurnNickname(),game.getModelView());
        }

    }

    public void endGame() {
        //TODO change END GAME
        List<String> ranking=  game.checkWinner();
        MessagePayload payload=new MessagePayload();
        //TODO da finire in base alla parte di rete
        //payload.put(KeyPayload.PLAYERS,ranking);
        //serverView.sendAllMessage(payload,MessageFromServerType.END_GAME);
        //TODO set GamePhase a END_GAME
        //sendMessages.sendAll(payload,MessageFromServerType.END_GAME);
        //sendMessages.sendMessage(game.getTurnPlayer().getNickname(),null,MessageFromServerType.END_GAME);
        //endGameListener.endGame(ranking);
    }

    public void illegalPhase(TurnPhase phase) throws Exception {
        if(!turnPhaseController.getCurrentPhase().equals(phase)){
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

    public boolean[] getActivePlayers(){return activePlayers;};
    public void setActivePlayers(boolean[] activePlayers){this.activePlayers=activePlayers;};


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
