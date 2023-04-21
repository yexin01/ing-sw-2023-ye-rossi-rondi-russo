package it.polimi.ingsw.listeners;


import it.polimi.ingsw.Client;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.messages.SendMessages;

import java.util.HashMap;

public class TokenListener  extends EventListener{
    public TokenListener(SendMessages sendMessage) {
        super(sendMessage);
    }
    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        int point=(int) newValue;
        //System.out.println(nickname +" reached token points: "+point);
        MessagePayload payload=new MessagePayload(EventType.REMOVE_TOKEN);
        payload.put(PayloadKeyServer.WHO_CHANGE,nickname);
        payload.put(PayloadKeyServer.POINTS,point);
        //sendMessage(nickname,payload,MessageFromServerType.DATA);
        sendMessage.sendAll(payload,MessageFromServerType.DATA);
        //sendAllExcept(nickname,newValue,MessageFromServerType.REMOVE_TOKEN);
        //sendMessage(nickname,newValue,MessageFromServerType.WIN_TOKEN);
    }

    /*
    public TokenListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

     */



    /*public TokenListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

     */


}

