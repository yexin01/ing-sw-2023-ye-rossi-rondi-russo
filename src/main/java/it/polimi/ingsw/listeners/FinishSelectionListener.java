package it.polimi.ingsw.listeners;


import it.polimi.ingsw.message.*;

import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

public class FinishSelectionListener extends EventListener {
    public FinishSelectionListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
       MessageHeader header=new MessageHeader(MessageType.DATA,playerNickname);
       MessagePayload payload=new MessagePayload(KeyDataPayload.SELECTION_PHASE);
       Message message=new Message(header,payload);
       getGameLobby().sendMessageToSpecificPlayer(message,playerNickname);

    }
}
