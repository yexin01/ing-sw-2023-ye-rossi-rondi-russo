package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageType;
import it.polimi.ingsw.server.GameLobby;
import it.polimi.ingsw.server.ServerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class AcknowledgementDispatcher implements PropertyChangeListener {

    private static final String ACTION = "Action";
    private static final String SETUP = "Setup";
    private final List<ServerView> views;
    //TODO gameLobby
    private final GameLobby gameLobby;


    public AcknowledgementDispatcher(List<ServerView> views, GameLobby gameLobby) {
        this.views = views;
        this.gameLobby = gameLobby;
    }

    private void dispatchAck(ServerView view, MessagePayload payload, String messageName) {
        view.sendMessage(payload, messageName, ServerMessageType.ACK_MESSAGE);
    }

    //TODO method similar to: end phase in turn listener delete one depends on the implementation
    public void confirmActionPerformed(String clientName, String actionName, TurnPhase newAction) {
        MessagePayload payload = new MessagePayload();
        payload.setAttribute("ActionName", actionName);
        payload.setAttribute("NewAction", newAction);
        for (ServerView view: views) {
            if (view.getPlayerNickname().equals(clientName)) {
                dispatchAck(view, payload, "ActionAck");
                return;
            }
        }
    }

    //TODO change setup nickname listener
    public void confirmSetupChoice(String clientName) {
        MessagePayload payload = new MessagePayload();
        payload.setAttribute("SetupNickname", clientName);
        for (ServerView view: views) {
            if (view.getPlayerNickname().equals(clientName)) {
                dispatchAck(view, payload, "SetupAck");
                return;
            }
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ACTION -> confirmActionPerformed((String) evt.getSource(), (String) evt.getOldValue(), (TurnPhase) evt.getNewValue());
            case SETUP -> confirmSetupChoice((String) evt.getNewValue());
        }
        //gameLobby.setSaveGame();

    }
}
