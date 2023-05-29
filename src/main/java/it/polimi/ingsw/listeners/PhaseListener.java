package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;

import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

/**
 *PhaseListener:listen the game controller:called at the end of each phase if the data sent
 * by the client passes all checks (ACK);
 */

public class PhaseListener extends EventListener {
    /**
     *
     * Constructor PhaseListener
     *
     * @param gameLobby
     */
    public PhaseListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    /**
     *message contains  the new phase;
     * @param event:end of phase;
     * @param playerNickname:turn player;
     * @param newValue: new phase;
     * @throws IOException
     */
    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
       TurnPhase turnPhase=(TurnPhase) newValue;
       //System.out.println("STO PHASEEEEEEEEEEEE "+turnPhase);
       MessageHeader header=new MessageHeader(MessageType.DATA,playerNickname);
       MessagePayload payload=new MessagePayload(turnPhase);
       Message message=new Message(header,payload);
       getGameLobby().sendMessageToSpecificPlayer(message,playerNickname);

    }
}
