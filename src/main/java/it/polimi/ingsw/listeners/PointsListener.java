package it.polimi.ingsw.listeners;

import it.polimi.ingsw.Client;

import java.util.HashMap;

public class PointsListener extends EventListener{
    public PointsListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

    /*
    public PointsListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

     */

   /*
    @Override
    public void onEvent(EventType eventType, EventValue eventValue, String nickname) {
        int points= (int) eventValue.getValue();
        System.out.println(nickname +" changed  POINTS: "+points);
    }
*/


    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        int points=(Integer)newValue;
        System.out.println(getClient(nickname).getNickname() +" changed points "+points);
        //sendMessage(nickname,newValue, MessageFromServerType.POINTS,getPlayerMap());
    }


}
