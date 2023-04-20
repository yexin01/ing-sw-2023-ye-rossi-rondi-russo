package it.polimi.ingsw.listeners;


import it.polimi.ingsw.Client;
import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.CommonGoalCard;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
//TODO it is not a listener methods will go in the controller
public class TurnListener extends SendMessages {


    public TurnListener() {
        super(playerMap);
    }

    public void endTurnMessage(String nickname, HashMap<String, Client> playerMap){
        sendMessage(nickname,null,MessageFromServerType.END_TURN,playerMap);
    }
    public void endPhase(String nickname, TurnPhase turnPhase,HashMap<String, Client> playerMap){
        sendMessage(nickname,null,MessageFromServerType.END_PHASE,playerMap);
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

 */
