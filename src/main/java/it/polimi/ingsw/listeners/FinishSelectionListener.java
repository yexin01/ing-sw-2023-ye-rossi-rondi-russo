package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.server.ServerView;

public class FinishSelectionListener implements EventListener {
    private final ServerView serverView;

    public FinishSelectionListener(ServerView serverView) {
        this.serverView = serverView;
    }


    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        ModelView model=(ModelView) newValue;
        BoardBoxView[][] boardView=model.getBoardView();
        ItemTileView[] selectedItem=model.getSelectedItems();
        MessagePayload payload=new MessagePayload(EventType.TILES_SELECTED);
        payload.put(PayloadKeyServer.WHO_CHANGE,playerNickname);
        payload.put(PayloadKeyServer.NEWBOARD,boardView);
        payload.put(PayloadKeyServer.TILES_SELECTED,selectedItem);
        serverView.sendAllMessage(payload, MessageFromServerType.DATA);

    }
}
