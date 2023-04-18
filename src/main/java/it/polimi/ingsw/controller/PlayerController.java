package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.*;

import it.polimi.ingsw.exceptions.WrongTurnActionRequestedException;
import it.polimi.ingsw.messages.ErrorMessageType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.model.*;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {



    private transient final GameController gameController;


    private final List<TurnPhase> currentTurnRemainingActions = new ArrayList<>();

    private transient final PropertyChangeSupport listeners = new PropertyChangeSupport(this);


    public PlayerController(GameController gameController) {
        this.gameController = gameController;

    }

    public void addListener(String propertyName, PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

    public void turnPhase(MessageFromClient message) throws Exception {
        String messageName = message.getClientMessageHeader().getMessageName();
        MessagePayload payload = message.getMessagePayload();
        if (!TurnPhase.contains(messageName)) throw new IllegalArgumentException();
        try {
            switch (messageName) {
                case "SelectionBoard" -> checkAndInsertBoardBox(message);
                case "FinishSelectionBoard" -> associatePlayerTiles();
                //  case "RESET_PLAYER_CHOICE_TILES" -> ;
                case "SelectOrder" -> changeOrderTiles(message);
                case "SelectColumn" -> selectingColumn(message);
                case "InsertBookshelfAndPoint" -> insertBookshelf();
                default -> throw new IllegalArgumentException();
            }
        } catch (WrongTurnActionRequestedException e) {
            fireError(ErrorMessageType.ILLEGAL_TURN_ACTION, e.getMessage());
            throw new IllegalArgumentException();
        } catch (NotEnoughFreeCellsColumn e) {
            throw new RuntimeException(e);
        }
    }


    public void checkAndInsertBoardBox( MessageFromClient message) throws WrongTurnActionRequestedException, InvalidTile, InvalidCoordinates {
        illegalTurn(TurnPhase.SELECT_FROM_BOARD);
        int x=message.getMessagePayload().getX();
        int y=message.getMessagePayload().getY();
        if(checkCoordinate(x)|| !gameController.getModel().getBoard().getBoardBox(x,y).isOccupiable() || checkCoordinate(y)){
            throw new InvalidCoordinates();
        }
        System.out.println("You selected "+message.getMessagePayload().getX()+","+message.getMessagePayload().getY());
        BoardBox boardBox=gameController.getModel().getBoard().getBoardBox(message.getMessagePayload().getX(),message.getMessagePayload().getY());
        int maxPlayerSelectableTiles=gameController.getModel().getTurnPlayer().getBookshelf().numSelectableTiles();
        if(!gameController.getModel().getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles)){
            throw new InvalidTile();
            //TODO add fire.. Error
        }
    }
    public boolean checkCoordinate(int num){
        if(num > gameController.getModel().getBoard().getMatrix()[0].length-1 || num<0){
            return true;
        }
        return false;

    }

    public void associatePlayerTiles() throws WrongTurnActionRequestedException {
        illegalTurn(TurnPhase.SELECT_FROM_BOARD);
        gameController.getModel().getTurnPlayer().selection(gameController.getModel().getBoard());
        gameController.getTurnController().changePhase();
        gameController.getTurnController().changePhase();
        //TODO poi andrà tolta
        gameController.getTurnController().changePhase();
    }
    private void fireError(ErrorMessageType errorType, String errorInfo) {
        listeners.firePropertyChange(new PropertyChangeEvent(gameController.getTurnController().getTurnPlayer().getNickname(),
                "Error", errorType, errorInfo));
    }

    public void changeOrderTiles( MessageFromClient message) throws WrongTurnActionRequestedException {
        illegalTurn(TurnPhase.SELECT_ORDER_TILES);
        //TODO insert method that moves the user-selected cards into the right order
        if (!true){
            fireError(ErrorMessageType.ERROR_INSERTING_ORDER_TILES, "Invalid order");
            throw new IllegalArgumentException();
        }

        gameController.getTurnController().changePhase();
    }
    public void selectingColumn(MessageFromClient message) throws WrongTurnActionRequestedException, NotEnoughFreeCellsColumn, InvalidColumn {
        illegalTurn(TurnPhase.SELECT_COLUMN);
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
        illegalTurn(TurnPhase.INSERT_BOOKSHELF_AND_POINTS);
        gameController.getModel().getTurnPlayer().insertBookshelf();
        if(gameController.getModel().getTurnPlayer().getBookshelf().isFull()){
            gameController.getModel().setEndGame(true);
        }
        gameController.getModel().getTurnPlayer().setPlayerPoints(1);
        // gameController.getModel().updateAllPoints();
        finishTurn();

    }
    public void finishTurn() throws Exception {
        if(gameController.getModel().getBoard().checkRefill()){
            gameController.getModel().getBoard().refill();
        }
        //TODO da finire
        if(gameController.getModel().isEndGame() && gameController.getModel().getTurnPlayer().equals(gameController.getModel().getLastPlayer())){
            listeners.firePropertyChange(new PropertyChangeEvent(gameController.getModel().getTurnPlayer(), "EndGame",
                    null,null));
        }
        gameController.getModel().setNextPlayer();
        gameController.getTurnController().setCurrentPhase(TurnPhase.SELECT_FROM_BOARD);
    }

    public void illegalTurn(TurnPhase phase) throws WrongTurnActionRequestedException {
        if(!gameController.getTurnController().getCurrentPhase().equals(phase)){
            System.err.println("Invalid turnPhase");
            fireError(ErrorMessageType.ILLEGAL_TURN_ACTION, "Illegal Turn Action");
            throw new WrongTurnActionRequestedException();
        }
        return;
    }




}




