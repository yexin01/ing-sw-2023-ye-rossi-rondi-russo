package it.polimi.ingsw.listeners;


import it.polimi.ingsw.Client;
import it.polimi.ingsw.messages.*;

import java.util.HashMap;

public interface EventListener {


    /*
    public EventListener() {
        super(playerMap);
    }
     */

    //private final HashMap<String, Client> playerMap;

    /*public EventListener(HashMap<String, Client> playerMap) {
        this.playerMap = playerMap;
    }
     */

    public abstract void onEvent(EventType eventType, Object newValue,String nickname);


/*
    public void setSendMessages(SendMessages sendMessages) {
        this.sendMessages = sendMessages;
    }
 */

    /*
    public HashMap<String, Client> getPlayerMap() {
        return playerMap;
    }
     */

    /*
    public void setPlayerMap(HashMap<String, Client> playerMap){
        this.playerMap=playerMap;
    }
    /*
    public static void setPlayerMap(HashMap<String, Client> playerMap){
        EventListener.playerMap=playerMap;
    }
     */

}