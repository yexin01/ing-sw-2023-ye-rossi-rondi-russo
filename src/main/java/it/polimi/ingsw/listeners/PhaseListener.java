package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;

import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

/**
 * The PhaseListener class is responsible for listening to the game controller and sending
 * the current phase of the game to the turn player.
 *  It is called at the end of each phase when the data sent by the client passes all checks (ACK).
 */

public class PhaseListener extends EventListener {
    /**
     * Constructs a PhaseListener.
     * @param gameLobby The game lobby associated with the listener.
     */

    public PhaseListener(GameLobby gameLobby) {
        super(gameLobby);
    }
    /**
     * Sends a message containing the new phase to the specified player.
     * @param event The event triggered at the end of a phase.
     * @param playerNickname The nickname of the player to whom the message will be sent.
     * @param newValue The new phase of the game.
     * @throws IOException If an I/O error occurs while sending the message.
     */

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
       TurnPhase turnPhase=(TurnPhase) newValue;
       MessageHeader header=new MessageHeader(MessageType.DATA,playerNickname);
       MessagePayload payload=new MessagePayload(turnPhase);
       Message message=new Message(header,payload);
       getGameLobby().sendMessageToSpecificPlayer(message,playerNickname);

    }
}
