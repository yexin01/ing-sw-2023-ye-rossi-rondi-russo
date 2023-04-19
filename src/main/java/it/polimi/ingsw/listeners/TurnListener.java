package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Player;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

//TODO it is not a listener methods will go in the controller
public class TurnListener  {
    public void endTurnMessage(String nickname){

        //inviare un messaggio al giocatore che ha terminato il turno
    }
    public void endPhase(String nickname, TurnPhase turnPhase){

        //inviare un messaggio al giocatore che ha terminato la fase
    }
    public void startGame(Game game){
        ArrayList<CommonGoalCard> commonGoalCardGame=game.getCommonGoalCards();
        for(Player p:game.getPlayers()){
            PersonalGoalCard personalGoalCard=p.getPersonalGoalCard();
            //TODO inviare ad ogni player le informazioni tutte le common Goal e il personal goal del player
        }
    }
    public void endGame(List<Player> ranking){
        int i=1;
        for(Player p: ranking){
            System.out.println(p.getPlayerPoints()+" "+p.getNickname()+" "+i++);
        }

    }
}
