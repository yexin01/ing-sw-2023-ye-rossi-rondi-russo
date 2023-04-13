package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageType;
import it.polimi.ingsw.server.ServerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurnListener implements PropertyChangeListener {
    //TODO change
    private final ServerView view;


    public TurnListener(ServerView view) {
        this.view = view;
    }


    public void endTurn(String turnEnder, String turnStarter) {
        MessagePayload payload = new MessagePayload();
        payload.setAttribute("TurnEnder", turnEnder);
        payload.setAttribute("TurnStarter", turnStarter);
        view.sendMessage(payload, "EndTurn", ServerMessageType.GAME_UPDATE);
    }


    public void endPhase(TurnPhase newPhase, String player) {
        MessagePayload payload = new MessagePayload();
        payload.setAttribute("NewPhase", newPhase);
        payload.setAttribute("Player", player);
        view.sendMessage(payload, "ChangePhase", ServerMessageType.GAME_UPDATE);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "EndTurn" -> endTurn((String) evt.getOldValue(), (String) evt.getSource());
            case "EndPhase" -> endPhase((TurnPhase) evt.getNewValue(), (String) evt.getSource());
        }
    }

}
