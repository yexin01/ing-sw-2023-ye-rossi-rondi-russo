package it.polimi.ingsw.listeners;

import it.polimi.ingsw.message.*;

import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

/**
 * The ErrorListener class is an event listener that listens to the game controller and handles error events.
 *  If the received data does not pass the required checks, it sends an error message to the player.
 */

public class ErrorListener extends EventListener {
    /**
     * Constructs an ErrorListener with the specified game lobby.
     * @param gameLobby The game lobby associated with the listener.
     */
    public ErrorListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    /**
     * Handles the error event by sending an error message to the player.
     * The message contains the error type.
     * @param event The error event.
     * @param playerNickname The nickname of the player who sent the message.
     * @param newValue The error type or details.
     * @throws IOException If an I/O error occurs while sending the error message.
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
