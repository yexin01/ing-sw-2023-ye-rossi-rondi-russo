package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.server.ServerView;

public class EndTurnListener implements EventListener{
    private final ServerView serverView;

    public EndTurnListener(ServerView serverView) {
        this.serverView = serverView;
    }


    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        ModelView model=(ModelView) newValue;
        ItemTileView[][] bookshelfView=model.getBookshelfView(playerNickname);
        PlayerPointsView playerPointsView=model.getPlayerPoints(playerNickname);
        MessagePayload payload=new MessagePayload(EventType.END_TURN);
        payload.put(PayloadKeyServer.WHO_CHANGE,playerNickname);
        payload.put(PayloadKeyServer.NEWBOOKSHELF,bookshelfView);
        payload.put(PayloadKeyServer.POINTS, playerPointsView);
        serverView.sendMessage(payload, MessageFromServerType.DATA,playerNickname);
    }
}
