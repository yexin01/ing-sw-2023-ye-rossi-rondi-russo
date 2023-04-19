package it.polimi.ingsw.listeners;

import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.List;

public class EndGameListener {
    //TODO handle end game inviando il messaggio a tutti i player
    public void endGame(List<Player> ranking){
        int i=1;
        for(Player p: ranking){
            System.out.println(p.getPlayerPoints()+" "+p.getNickname()+" "+i++);
        }

    }
}
