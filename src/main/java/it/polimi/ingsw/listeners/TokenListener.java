package it.polimi.ingsw.listeners;


import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.ServerView;

public class TokenListener implements EventListener {
    private final ServerView serverView;

    public TokenListener(ServerView serverView) {
        this.serverView = serverView;
    }

    @Override
    public void fireEvent(EventType event, java.lang.String playerNickname, Object newValue) {
        ModelView model=(ModelView) newValue;
        int index=model.getIndexRemoveToken();
        CommonGoalView commonGoalView=model.getCommonGoalViews()[index];
        MessagePayload payloadWinner=new MessagePayload(EventType.WIN_TOKEN);
        payloadWinner.put(PayloadKeyServer.TOKEN,commonGoalView);
        payloadWinner.put(PayloadKeyServer.WHO_CHANGE,playerNickname);
        payloadWinner.put(PayloadKeyServer.INDEX_TOKEN,index);
        //serverView.sendMessage(payloadWinner, MessageFromServerType.DATA,playerNickname);
        //serverView.sendAllMessage(payloadWinner, MessageFromServerType.DATA);
    }
}
