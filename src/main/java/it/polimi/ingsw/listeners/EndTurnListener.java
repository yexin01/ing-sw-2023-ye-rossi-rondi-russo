package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.server.SendMessages;
import it.polimi.ingsw.model.Player;
/*
public class EndTurnListener extends EventListener{
    public EndTurnListener(SendMessages sendMessage) {
        super(sendMessage);
    }
    @Override
    public void onEvent(EventType eventType,MessagePayload payload, String nickname) {
        PlayerPointsView player=new PlayerPointsView((Player)newValue);
        MessagePayload payload=new MessagePayload(eventType);
        payload.put(PayloadKeyServer.WHO_CHANGE,nickname);
        payload.put(PayloadKeyServer.NEWBOOKSHELF,player.getBookshelf());
        payload.put(PayloadKeyServer.POINTS,player.getPoints());
        //TODO INVIARLO A TUTTI una volta inseriti thread
        //sendAll(payload, MessageFromServerType.DATA);
        sendMessage.sendMessage(nickname,payload,MessageFromServerType.DATA);
    }

    /*
    public EndTurnListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

     */

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






