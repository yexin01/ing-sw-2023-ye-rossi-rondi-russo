package it.polimi.ingsw.listeners;


import it.polimi.ingsw.Client;
import it.polimi.ingsw.messages.MessageFromServerType;

import java.util.HashMap;

public class TokenListener extends EventListener{
    public TokenListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }



    /*public TokenListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

     */

    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        int point=(int) newValue;
        System.out.println(nickname +" reached token points: "+point);
        sendAllExcept(nickname,newValue,MessageFromServerType.REMOVE_TOKEN);
        sendMessage(nickname,newValue,MessageFromServerType.WIN_TOKEN);
    }
}

