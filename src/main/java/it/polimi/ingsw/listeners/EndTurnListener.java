package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

public class EndTurnListener extends EventListener{
    public EndTurnListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        System.out.println("STO INVIANDO END, PROSSIMO GIOCATORE");
        System.out.println(playerNickname);
        ModelView model=(ModelView) newValue;
        Boolean[] activePlayers=model.getActivePlayers();
        for(PlayerPointsView nickname: model.getPlayerPoints()){
            if(activePlayers[model.getIntegerValue(nickname.getNickname())]){
                getGameLobby().sendMessageToSpecificPlayer(creationMessageEndTurn(nickname.getNickname(),model),nickname.getNickname()) ;
            }
        }
    }
    public Message creationMessageEndTurn(String nickname,ModelView modelView){
        BoardBoxView[][] boardView= modelView.getBoardView();
        MessageHeader header=new MessageHeader(MessageType.DATA,nickname);
        MessagePayload payload=new MessagePayload(TurnPhase.END_TURN);
        payload.put(Data.NEW_BOARD,boardView);
        payload.put(Data.NEXT_PLAYER,modelView.getTurnNickname());
        payload.put(Data.TOKEN,modelView.getToken());
        payload.put(Data.POINTS,modelView.checkWinner());
        payload.put(Data.PERSONAL_POINTS,modelView.getPersonalPoint(nickname));
        payload.put(Data.WHO_CHANGE,modelView.findPreviousPlayer().getNickname());
        Message m=new Message(header,payload);
        return m;
    }
}
