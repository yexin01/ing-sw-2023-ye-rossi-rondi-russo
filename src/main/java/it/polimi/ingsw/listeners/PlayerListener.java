package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageType;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.server.ServerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PlayerListener implements PropertyChangeListener {

    private static final String BOARD_SELECTION = "BoardSelection";
    private static final String BOOKSHELF_INSERTION = "BookshelfInsertion";
    private static final String POINTS = "Points";
    private final ServerView view;

    public PlayerListener(ServerView view) {
        this.view = view;
    }

    private void updateBoard(Board board, String playerName) {
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setAttribute("NewBoard", board);
        messagePayload.setAttribute("PlayerName", playerName);
        view.sendBroadcast(messagePayload,"UpdateBoard", ServerMessageType.GAME_UPDATE);
    }
    private void updateBookshelf(Bookshelf bookshelf, String playerName) {
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setAttribute("NewBookshelf", bookshelf);
        messagePayload.setAttribute("PlayerName", playerName);
        view.sendBroadcast(messagePayload,"UpdateBookshelf", ServerMessageType.GAME_UPDATE);
    }

    private void updatePointsPlayer(int points, String playerName) {
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setAttribute("NewPoints", points);
        messagePayload.setAttribute("PlayerName", playerName);
        view.sendBroadcast(messagePayload,"UpdatePoints", ServerMessageType.GAME_UPDATE);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case BOARD_SELECTION -> updateBoard((Board) evt.getNewValue(), (String) evt.getSource());
            case BOOKSHELF_INSERTION -> updateBookshelf((Bookshelf) evt.getNewValue(), (String) evt.getSource());
            case POINTS -> updatePointsPlayer((int) evt.getNewValue(), (String) evt.getSource());
        }

    }
}
