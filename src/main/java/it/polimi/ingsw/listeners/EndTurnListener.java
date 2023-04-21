package it.polimi.ingsw.listeners;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.messages.SendMessages;
import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.Player;

import java.util.HashMap;

public class EndTurnListener extends SendMessages implements EventListener{
    public EndTurnListener(HashMap<String, Client> playerMap) {
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
        Player player=(Player)newValue;
        int newPoints=player.getPlayerPoints();
        Bookshelf newBookshelf=player.getBookshelf();
        MessagePayload payload=new MessagePayload(eventType);
        payload.put(PayloadKeyServer.WHO_CHANGE,nickname);
        payload.put(PayloadKeyServer.NEWBOOKSHELF,newBookshelf);
        payload.put(PayloadKeyServer.POINTS,newPoints);
        //payload.put(PayloadKeyServer.ENDTURN,newBookshelf);
        //newBoard.printMatrix();
        //TODO INVIARLO A TUTTI una volta inseriti thread
        //sendAll(payload, MessageFromServerType.DATA);
        sendMessage(nickname,payload,MessageFromServerType.DATA);
    }


}
