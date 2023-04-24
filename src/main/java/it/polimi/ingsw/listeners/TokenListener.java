package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.server.ServerView;

public class TokenListener implements EventListener {
    private final ServerView serverView;

    public TokenListener(ServerView serverView) {
        this.serverView = serverView;
    }

    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        ModelView model=(ModelView) newValue;
        int index=model.getIndexRemoveToken();
        CommonGoalView commonGoalView=model.getCommonGoalViews()[index];
        MessagePayload payloadWinner=new MessagePayload(EventType.WIN_TOKEN);
        payloadWinner.put(PayloadKeyServer.TOKEN,commonGoalView);
        payloadWinner.put(PayloadKeyServer.WHO_CHANGE,playerNickname);
        payloadWinner.put(PayloadKeyServer.INDEX_TOKEN,index);
        //serverView.sendMessage(payloadWinner, MessageFromServerType.DATA,playerNickname);
        serverView.sendAllMessage(payloadWinner, MessageFromServerType.DATA);
    }
}
