package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;

import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

public class TurnListener extends EventListener {
    public TurnListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        TurnPhase turnPhase=(TurnPhase) newValue;
        System.out.println("STO INVIANDO");
        System.out.println(turnPhase);
       MessageHeader header=new MessageHeader(MessageType.DATA,playerNickname);
       MessagePayload payload=new MessagePayload(turnPhase);
       Message message=new Message(header,payload);
       //getGameLobby().sendMessageToSpecificPlayer(message,playerNickname);

    }
}
