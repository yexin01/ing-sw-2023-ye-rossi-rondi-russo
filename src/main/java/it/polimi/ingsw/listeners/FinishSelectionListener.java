package it.polimi.ingsw.listeners;


import it.polimi.ingsw.message.*;

import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;

public class FinishSelectionListener extends EventListener {
    public FinishSelectionListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) {
        /*
        KeyAbstractPayload key2= KeyDataPayload.NEW_BOARD;



        ServerMessageHeader head=new ServerMessageHeader(MessageType.ERROR,"bo");
        KeyAbstractPayload key4=KeyErrorPayload.ERROR_CONNECTION;
        MessagePayload p=new MessagePayload();
        p.put(key4,ErrorType.NICKNAME_TAKEN);
        MessageFromServer message=new MessageFromServer(head,p);

        MessageFromServer message2=new MessageFromServer(head);

        ModelView model=(ModelView) newValue;

        BoardBoxView[][] boardView=model.getBoardView();
        ItemTileView[] selectedItem=model.getSelectedItems();
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.WHO_CHANGE,playerNickname);
        payload.put(KeyPayload.NEW_BOARD,boardView);
        payload.put(KeyPayload.TILES_SELECTED,selectedItem);

         */
        //TODO verra gestito diversamente
       // MessageFromServer messageFromServer=new MessageFromServer(new ServerMessageHeader(EventType.BOARD_SELECTION,playerNickname),payload);
        //serverView.sendAllMessage(payload, MessageFromServerType.DATA);

    }
}
