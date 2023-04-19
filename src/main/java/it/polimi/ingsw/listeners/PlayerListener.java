package it.polimi.ingsw.listeners;

import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.exceptions.ErrorType;
import it.polimi.ingsw.model.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

/*
public class PlayerListener implements EventListener {

    private static final String BOARD_SELECTION = "BoardSelection";
    private static final String BOOKSHELF_INSERTION = "BookshelfInsertion";
    private static final String POINTS = "Points";
    //private final ServerView view;

    //public PlayerListener(ServerView view) {
    //   this.view = view;
    //}

    public void onEvent(String eventName, Object newValue, String nickname) {

        switch(eventName) {
            case BOARD_SELECTION -> updateBoard((Board) newValue, (String) nickname);
            case BOOKSHELF_INSERTION -> updateBookshelf((Bookshelf) newValue, (String) nickname);
            case POINTS -> updatePointsPlayer((int) newValue, (String) nickname);
        }
    }

    private void updateBoard(Board board, String nickname) {
        System.out.println(nickname +" changed Board");
        board.printMatrix();

        MessageBoardBox messagePayload = new MessageBoardBox(0,0);
        messagePayload.setAttribute("NewBoard", board);
        messagePayload.setAttribute("PlayerName", playerName);

        // view.sendBroadcast(messagePayload,"UpdateBoard", ServerMessageType.GAME_UPDATE);
    }
    private void updateBookshelf(Bookshelf bookshelf, String nickname) {
        System.out.println(nickname +" changed Bookshelf");
        bookshelf.printBookshelf();


        MessageBoardBox messagePayload = new MessageBoardBox(0,0);
        messagePayload.setAttribute("NewBookshelf", bookshelf);
        messagePayload.setAttribute("PlayerName", playerName);
        //view.sendBroadcast(messagePayload,"UpdateBookshelf", ServerMessageType.GAME_UPDATE);

    }

    private void updatePointsPlayer(int points, String nickname) {
        System.out.println(nickname +" changed  POINTS: "+points);

        MessageBoardBox messagePayload = new MessageBoardBox(0,0);
        messagePayload.setAttribute("NewPoints", points);
        messagePayload.setAttribute("PlayerName", playerName);
        //view.sendBroadcast(messagePayload,"UpdatePoints", ServerMessageType.GAME_UPDATE);

    }

    @Override
    public void onEvent(EventType eventType, EventValue eventValue, String nickname)  {
        try {
            throw new Error(ErrorType.INVALID_COLUMN);
        } catch (Error e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case BOARD_SELECTION -> updateBoard((Board) evt.getNewValue(), (String) evt.getSource());
            case BOOKSHELF_INSERTION -> updateBookshelf((Bookshelf) evt.getNewValue(), (String) evt.getSource());
            case POINTS -> updatePointsPlayer((int) evt.getNewValue(), (String) evt.getSource());
        }
    }


}*/