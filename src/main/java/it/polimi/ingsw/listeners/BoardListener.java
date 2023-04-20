package it.polimi.ingsw.listeners;


import it.polimi.ingsw.Client;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.model.Board;

import java.util.HashMap;


public class BoardListener extends EventListener{
    public BoardListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

/*
    public BoardListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }

 */

    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        Client player=getClient(nickname);
        System.out.println(player.getNickname() +" changed Board");
        Board newBoard=(Board) newValue;
        //newBoard.printMatrix();
        sendMessage(player.getNickname(),newValue,MessageFromServerType.NEW_BOARD);

    }
}

