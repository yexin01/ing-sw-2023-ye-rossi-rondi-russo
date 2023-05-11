package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.KeyPayload;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.network.server.GameLobby;

public class ErrorListener extends EventListener {
    public ErrorListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.MESSAGE_ERROR,(newValue));
        //getGameLobby().sendMessage(null,EventType.ERROR,playerNickname);
    }
}
