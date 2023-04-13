package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.ErrorMessageType;
import it.polimi.ingsw.server.ServerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class Error implements PropertyChangeListener {
    private final List<ServerView> clients;


    public Error(List<ServerView> views) {
        this.clients = views;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ErrorMessageType error = (ErrorMessageType) evt.getOldValue();
        String errorCommitter = (String) evt.getSource();
        String errorInfo = (String) evt.getNewValue();
        for (ServerView view: clients) {
            if (view.getPlayerNickname().equals(errorCommitter)) {
                view.sendError(error, errorInfo);
                return;
            }
        }

    }
}
