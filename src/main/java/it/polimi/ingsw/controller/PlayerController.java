package it.polimi.ingsw.controller;

import it.polimi.ingsw.exceptions.NotEnoughFreeCellsColumn;
import it.polimi.ingsw.exceptions.WrongTurnActionRequestedException;
import it.polimi.ingsw.messages.ErrorMessageType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.model.BoardBox;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class PlayerController {


    private transient final TurnController turnController;
    private transient final GameController gameController;


    private final List<TurnPhase> currentTurnRemainingActions = new ArrayList<>();

    private transient final PropertyChangeSupport listeners = new PropertyChangeSupport(this);


    public PlayerController(TurnController turnController, GameController gameController) {
        this.turnController = turnController;
        this.gameController = gameController;
        currentTurnRemainingActions.add(TurnPhase.SELECT_FROM_BOARD);

    }

    public void addListener(String propertyName, PropertyChangeListener listener) {
        listeners.addPropertyChangeListener(propertyName, listener);
    }

    public void turnPhase(MessageFromClient message) throws IllegalArgumentException {
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
                case "InsertBookshelfAndPoint" -> insertBookshelf(message);
                default -> throw new IllegalArgumentException();
            }
        } catch (WrongTurnActionRequestedException e) {
            fireError(ErrorMessageType.ILLEGAL_TURN_ACTION, e.getMessage());
            throw new IllegalArgumentException();
        } catch (NotEnoughFreeCellsColumn e) {
            throw new RuntimeException(e);
        }
    }


    public boolean checkAndInsertBoardBox( MessageFromClient message) throws WrongTurnActionRequestedException {
        illegalTurn(TurnPhase.SELECT_FROM_BOARD);
        BoardBox boardBox= (BoardBox) message.getMessagePayload().getAttributeMessage("BoardBox").getAsObject();
        int maxPlayerSelectableTiles=gameController.getModel().getTurnPlayer().getBookshelf().numSelectableTiles();
        return gameController.getModel().getBoard().checkSelectable(boardBox,maxPlayerSelectableTiles);
    }
    public void associatePlayerTiles() throws WrongTurnActionRequestedException {
        illegalTurn(TurnPhase.SELECT_FROM_BOARD);
        gameController.getModel().getTurnPlayer().setSelectedItems(gameController.getModel().getBoard().selected());
        turnController.changePhase();
    }
    private void fireError(ErrorMessageType errorType, String errorInfo) {
        listeners.firePropertyChange(new PropertyChangeEvent(turnController.getTurnPlayer().getNickname(),
                "Error", errorType, errorInfo));
    }

    public void changeOrderTiles( MessageFromClient message) throws WrongTurnActionRequestedException {
        illegalTurn(TurnPhase.SELECT_ORDER_TILES);
        Integer[] order= (Integer[]) message.getMessagePayload().getAttributeMessage("OrderTiles").getAsIntArray();
        //TODO insert method that moves the user-selected cards into the right order
        if (!true){
            fireError(ErrorMessageType.ERROR_INSERTING_ORDER_TILES, "Invalid order");
            throw new IllegalArgumentException();
        }

        turnController.changePhase();
    }
    public void selectingColumn(MessageFromClient message) throws WrongTurnActionRequestedException, NotEnoughFreeCellsColumn {
        illegalTurn(TurnPhase.SELECT_COLUMN);
        int column= (int) message.getMessagePayload().getAttributeMessage("NumColumn").getAsInt();
        if(!(gameController.getModel().getTurnPlayer().getBookshelf().checkBookshelf(column,gameController.getModel().getTurnPlayer().getSelectedItems().size()))){
            //TODO decide how to handle the exception
            fireError(ErrorMessageType.INVALID_COLUMN, "Invalid column");
            throw new NotEnoughFreeCellsColumn();
        }
        gameController.getModel().getTurnPlayer().getBookshelf().setColumnSelected(column);
        turnController.changePhase();
    }


    public void insertBookshelf(MessageFromClient message) throws WrongTurnActionRequestedException {
        illegalTurn(TurnPhase.INSERT_BOOKSHELF_AND_POINTS);
        gameController.getModel().getTurnPlayer().insertBookshelf(gameController.getModel().getTurnPlayer().getBookshelf().getColumnSelected());
         //
        //TODO points update
        //TODO if the bookshelf is full set endGame to true
        turnController.changePhase();
    }

    public void illegalTurn(TurnPhase phase) throws WrongTurnActionRequestedException {
        if(turnController.getCurrentPhase().equals(phase)){
            fireError(ErrorMessageType.ILLEGAL_TURN_ACTION, "Illegal Turn Action");
            throw new WrongTurnActionRequestedException();
        }
        return;
    }




}




