package it.polimi.ingsw.listeners;

import it.polimi.ingsw.message.*;

import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

/**
 *EndTurnListener listen the game controller:if the data received does not pass the controls send an error.
 * message to the player.
 */
public class ErrorListener extends EventListener {
    /**
     * Constructor of ErrorListener
     * @param gameLobby
     */
    public ErrorListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    /**
     *Message contains the error (type ERROR_DATA).
     * @param event:error;
     * @param playerNickname:nickname of the player who sent the message;
     * @param newValue: ErrorrType;
     * @throws IOException
     */
    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        MessageHeader header=new MessageHeader(MessageType.ERROR,playerNickname);
        MessagePayload payload=new MessagePayload(KeyErrorPayload.ERROR_DATA);
        payload.put(Data.ERROR,newValue);
        Message message=new Message(header,payload);
        getGameLobby().sendMessageToSpecificPlayer(message,playerNickname);
    }
}
