package it.polimi.ingsw.listeners;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SetupListener implements PropertyChangeListener {
    private static final String PERSONALGOALCARD = "PersonalGoalCard";
    //TODO add setup information
    //private final ServerView view;


    //public SetupListener(ServerView view) {
      //  this.view = view;
    //}

    //TODO change set personalGoalCard Listener
    public void setPersonalgoalcard(int id, String playerName) {
        /*
        MessageBoardBox messagePayload = new MessageBoardBox(0,0);
        messagePayload.setAttribute("idPersonalGoal", id);
        messagePayload.setAttribute("PlayerName", playerName);

         */
        //view.sendMessage(messagePayload,"PersonalGoalTaken", ServerMessageType.GAME_SETUP);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case PERSONALGOALCARD -> setPersonalgoalcard((int) evt.getOldValue(), (String) evt.getNewValue());
        }
    }


}
