package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.model.BoardBox;
import it.polimi.ingsw.model.Game;


public class GameController implements Controller{

    /*
        private SetupController setupController;

     */
    private TurnController turnController;

    private Game game;
    //private transient final PropertyChangeSupport listeners=new PropertyChangeSupport(this);

    public GameController(Game game) throws Exception {
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
    public void receiveMessageFromClient(MessageFromClient message) throws Exception {
        String nicknamePlayer = message.getClientMessageHeader().getNicknameSender();
        String messageName = message.getClientMessageHeader().getMessageName();
        try{
            if (!nicknamePlayer.equals(turnController.getTurnPlayer().getNickname())) {
                throw new Error(ErrorType.ILLEGAL_TURN);
            }
            turnPhase(message);

        }catch(Exception e){

        }


    }
    public void turnPhase(MessageFromClient message) throws Exception {
        String messageName = message.getClientMessageHeader().getMessageName();
        MessagePayload payload = message.getMessagePayload();
        if (!TurnPhase.contains(messageName)) throw new IllegalArgumentException();
        //try {
        switch (messageName) {
            case "SelectionBoard" -> checkAndInsertBoardBox(message);
            case "FinishSelectionBoard" -> associatePlayerTiles();
            //  case "RESET_PLAYER_CHOICE_TILES" -> ;
            case "SelectOrder" -> permutePlayerTiles(message);
            case "SelectColumn" -> selectingColumn(message);
            case "InsertBookshelfAndPoint" -> insertBookshelf();
            default -> throw new IllegalArgumentException();
        }
        //} catch (Error e) {
        //}
    }


    public void checkAndInsertBoardBox( MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);

        int x=message.getMessagePayload().getX();
        int y=message.getMessagePayload().getY();
        game.getBoard().checkCoordinates(x,y);

        System.out.println("You selected "+message.getMessagePayload().getX()+","+message.getMessagePayload().getY());
        BoardBox boardBox=game.getBoard().getBoardBox(message.getMessagePayload().getX(),message.getMessagePayload().getY());
        int maxPlayerSelectableTiles=game.getTurnPlayer().getBookshelf().numSelectableTiles();
        game.getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles);

    }


    public void associatePlayerTiles() throws Error {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        game.getTurnPlayer().selection(game.getBoard());
        turnController.changePhase();
        turnController.changePhase();
        //TODO poi andrà tolta
        // gameController.getTurnController().changePhase();
    }
    public void permutePlayerTiles(MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_ORDER_TILES);
        int[] orderTiles=message.getMessagePayload().getOrderTiles();
        //TODO check Order
        if (!true){
            throw new Error(ErrorType.INVALID_ORDER_TILE);
        }
        game.getTurnPlayer().permuteSelection(orderTiles);
        turnController.changePhase();
    }


    public void selectingColumn(MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_COLUMN);
        int column=message.getMessagePayload().getColumn();
        System.out.println("You selected "+column);
        game.getTurnPlayer().getBookshelf().checkBookshelf(column,game.getTurnPlayer().getSelectedItems().size());
        /*
        if(!(gameController.getModel().getTurnPlayer().getBookshelf().checkBookshelf(column,gameController.getModel().getTurnPlayer().getSelectedItems().size()))){
            //TODO decide how to handle the exception
            System.err.println("Invalid column");
            fireError(ErrorMessageType.INVALID_COLUMN, "Invalid column");
            throw new NotEnoughFreeCellsColumn();
        }

         */
        game.getTurnPlayer().getBookshelf().setColumnSelected(column);
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
        if(game.getBoard().checkRefill()){
            game.getBoard().refill();
        }
        //TODO da finire
        if(game.isEndGame() && game.getTurnPlayer().equals(game.getLastPlayer())){
            //listeners.firePropertyChange(new PropertyChangeEvent(gameController.getModel().getTurnPlayer(), "EndGame",
            //       null,null));
        }
        game.setNextPlayer();
        turnController.setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    public void illegalPhase(TurnPhase phase) throws Error {
        if(!turnController.getCurrentPhase().equals(phase)){
            throw new Error(ErrorType.ILLEGAL_PHASE);
        }
        return;
    }

}
