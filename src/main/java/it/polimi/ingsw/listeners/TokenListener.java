package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.CommonGoalCard;


public class TokenListener implements EventListener{
    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        int point=(int) newValue;
        System.out.println(nickname +" reached token points: "+point);
        //TODO MANDARE un messaggio a tutti i giocatori
    }
}

