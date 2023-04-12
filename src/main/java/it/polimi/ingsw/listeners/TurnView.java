package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.server.ServerView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TurnView implements PropertyChangeListener {
    private final ServerView serverView;

    public TurnView(ServerView serverView) {
        this.serverView = serverView;
    }

    public void endTurnEvent(String turnEnder,String turnStarter, TurnPhase action){
        MessagePayload messagePayload =new MessagePayload();
        messagePayload.setAttributeMessage("TurnEnder",turnEnder);
        messagePayload.setAttributeMessage("TurnStarter",turnStarter);
        messagePayload.setAttributeMessage("Actions",action);
       //TODO add when we will finish server and client
        // serverView.sendMessage(clientAndServerMessage,"EndTurn",ServerMessageType.GAME_UPDATE);
    }

    public void endPhaseEvent(TurnPhase newPhase,String starter, TurnPhase[] actions){
        MessagePayload messagePayload =new MessagePayload();
        messagePayload.setAttributeMessage("NewPhase",newPhase);
        messagePayload.setAttributeMessage("Starter",starter);
        messagePayload.setAttributeMessage("Actions",actions);
        //TODO add when we will finish server and client

        // serverView.sendMessage(clientAndServerMessage,"ChangePhase",ServerMessageType.GAME_UPDATE);
    }
/*
    public void sendBoardChange()

 */



    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()){
            case "EndTurn" -> endTurnEvent((String)evt.getOldValue(),(String) evt.getSource(),(TurnPhase) evt.getNewValue());
            case"EndPhase" -> endPhaseEvent((TurnPhase) evt.getOldValue(),(String) evt.getSource(),(TurnPhase[]) evt.getNewValue());
        }

    }
}
