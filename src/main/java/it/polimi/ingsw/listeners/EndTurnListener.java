package it.polimi.ingsw.listeners;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
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
        BoardBoxView[][] boardView= model.getBoardView();
        //ItemTileView[][] bookshelfView=model.getBookshelfView(playerNickname);
        //PlayerPointsView playerPointsView=model.getPlayerPoints(playerNickname);
        MessageHeader header=new MessageHeader(MessageType.DATA,playerNickname);
        MessagePayload payload=new MessagePayload(TurnPhase.END_TURN);
        payload.put(Data.NEW_BOARD,boardView);
        payload.put(Data.WHO_CHANGE,playerNickname);
        Message message=new Message(header,payload);
        getGameLobby().sendMessageToAllPlayers(message);

    }
}
