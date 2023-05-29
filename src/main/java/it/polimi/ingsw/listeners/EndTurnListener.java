package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

/**
 * EndTurnListener listen the game controller: it is awakened when a player's turn is over or when the
 * current player has disconnected.
 */

public class EndTurnListener extends EventListener{
    /**
     * Constructor EndTurnListener
     * @param gameLobby
     */
    public EndTurnListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    /**
     *Sends the same end of turn message to all connected players
     * @param event:end of the current player's turn
     * @param playerNickname:next connected player
     * @param newValue:modelView updated
     * @throws IOException
     */

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        //System.out.println(playerNickname);
        ModelView model=(ModelView) newValue;
        Boolean[] activePlayers=model.getActivePlayers();
        for(PlayerPointsView nickname: model.getPlayerPoints()){
            if(activePlayers[model.getIntegerValue(nickname.getNickname())]){
                getGameLobby().sendMessageToSpecificPlayer(creationMessageEndTurn(nickname.getNickname(),model),nickname.getNickname()) ;
            }
        }
    }

    /**
     * End of round message creation.Includes: board updated, next player, player who just played, token scores,
     * player scores sorted from lowest to highest score, common goal with points left,
     * player's personal goal score to whom the message is addressed.
     * @param nickname:message addressee;
     * @param modelView: modelView updated;
     * @return: message;
     */
    public Message creationMessageEndTurn(String nickname,ModelView modelView){
        BoardBoxView[][] boardView= modelView.getBoardView();
        MessageHeader header=new MessageHeader(MessageType.DATA,nickname);
        MessagePayload payload=new MessagePayload(TurnPhase.END_TURN);
        payload.put(Data.NEW_BOARD,boardView);
        payload.put(Data.NEXT_PLAYER,modelView.getTurnNickname());
        payload.put(Data.TOKEN,modelView.getToken());
        payload.put(Data.POINTS,modelView.checkWinner());
        payload.put(Data.COMMON_GOAL, modelView.getCommonGoalView());
        payload.put(Data.PERSONAL_POINTS,modelView.getPersonalPoint(nickname));
        payload.put(Data.WHO_CHANGE,modelView.findPreviousPlayer().getNickname());
        Message m=new Message(header,payload);
        return m;
    }
}
