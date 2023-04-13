package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageType;
import it.polimi.ingsw.server.ServerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SetupListener implements PropertyChangeListener {
    private static final String PERSONALGOALCARD = "PersonalGoalCard";
    //TODO add setup information
    private final ServerView view;


    public SetupListener(ServerView view) {
        this.view = view;
    }

    //TODO change set personalGoalCard Listener
    public void setPersonalgoalcard(int id, String playerName) {
        MessagePayload messagePayload = new MessagePayload();
        messagePayload.setAttribute("idPersonalGoal", id);
        messagePayload.setAttribute("PlayerName", playerName);
        view.sendMessage(messagePayload,"PersonalGoalTaken", ServerMessageType.GAME_SETUP);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PERSONALGOALCARD -> setPersonalgoalcard((int) evt.getOldValue(), (String) evt.getNewValue());
        }
    }


}
