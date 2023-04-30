package it.polimi.ingsw.listeners;


import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.ServerView;

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
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.WHO_CHANGE,playerNickname);
        payload.put(KeyPayload.NEW_BOARD,boardView);
        payload.put(KeyPayload.TILES_SELECTED,selectedItem);
        //TODO verra gestito diversamente
        MessageFromServer messageFromServer=new MessageFromServer(new ServerMessageHeader(EventType.BOARD_SELECTION,playerNickname),payload);
        //serverView.sendAllMessage(payload, MessageFromServerType.DATA);

    }
}
