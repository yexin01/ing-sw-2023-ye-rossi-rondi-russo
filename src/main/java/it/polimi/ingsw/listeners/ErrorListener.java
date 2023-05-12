package it.polimi.ingsw.listeners;

import it.polimi.ingsw.message.*;

import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

public class ErrorListener extends EventListener {
    public ErrorListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        MessageHeader header=new MessageHeader(MessageType.ERROR,playerNickname);
        MessagePayload payload=new MessagePayload(KeyErrorPayload.ERROR_DATA);
        payload.put(Data.ERROR,newValue);
        Message message=new Message(header,payload);
        getGameLobby().sendMessageToSpecificPlayer(message,playerNickname);
    }
}
