package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;

import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.List;

public class PlayerController {



    private transient final GameController gameController;

    public PlayerController(GameController gameController) {
        this.gameController = gameController;
    }

    /*
    public void addListener(String propertyName, PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

     */

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
        gameController.getModel().getBoard().checkCoordinates(x,y);

        System.out.println("You selected "+message.getMessagePayload().getX()+","+message.getMessagePayload().getY());
        BoardBox boardBox=gameController.getModel().getBoard().getBoardBox(message.getMessagePayload().getX(),message.getMessagePayload().getY());
        int maxPlayerSelectableTiles=gameController.getModel().getTurnPlayer().getBookshelf().numSelectableTiles();
        gameController.getModel().getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles);

    }


    public void associatePlayerTiles() throws Error {
        illegalPhase(TurnPhase.SELECT_FROM_BOARD);
        gameController.getModel().getTurnPlayer().selection(gameController.getModel().getBoard());
        gameController.getTurnController().changePhase();
        gameController.getTurnController().changePhase();
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
        gameController.getModel().getTurnPlayer().permuteSelection(orderTiles);
        gameController.getTurnController().changePhase();
    }


    public void selectingColumn(MessageFromClient message) throws Error {
        illegalPhase(TurnPhase.SELECT_COLUMN);
        int column=message.getMessagePayload().getColumn();
        System.out.println("You selected "+column);
        gameController.getModel().getTurnPlayer().getBookshelf().checkBookshelf(column,gameController.getModel().getTurnPlayer().getSelectedItems().size());
        /*
        if(!(gameController.getModel().getTurnPlayer().getBookshelf().checkBookshelf(column,gameController.getModel().getTurnPlayer().getSelectedItems().size()))){
            //TODO decide how to handle the exception
            System.err.println("Invalid column");
            fireError(ErrorMessageType.INVALID_COLUMN, "Invalid column");
            throw new NotEnoughFreeCellsColumn();
        }

         */
        gameController.getModel().getTurnPlayer().getBookshelf().setColumnSelected(column);
        gameController.getTurnController().changePhase();
    }


    public void insertBookshelf() throws Exception {
        illegalPhase(TurnPhase.INSERT_BOOKSHELF_AND_POINTS);
        gameController.getModel().getTurnPlayer().insertBookshelf();
        if(gameController.getModel().getTurnPlayer().getBookshelf().isFull()){
            gameController.getModel().setEndGame(true);
        }
        gameController.getModel().updateAllPoints();
        finishTurn();

    }
    public void finishTurn() {
        if(gameController.getModel().getBoard().checkRefill()){
            gameController.getModel().getBoard().refill();
        }
        //TODO da finire
        if(gameController.getModel().isEndGame() && gameController.getModel().getTurnPlayer().equals(gameController.getModel().getLastPlayer())){
            //listeners.firePropertyChange(new PropertyChangeEvent(gameController.getModel().getTurnPlayer(), "EndGame",
             //       null,null));
        }
        gameController.getModel().setNextPlayer();
        gameController.getTurnController().setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    public void illegalPhase(TurnPhase phase) throws Error {
        if(!gameController.getTurnController().getCurrentPhase().equals(phase)){
            throw new Error(ErrorType.ILLEGAL_PHASE);
        }
        return;
    }




}




