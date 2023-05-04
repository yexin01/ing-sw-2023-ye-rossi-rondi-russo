package it.polimi.ingsw.listeners;


import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;

public class EndTurnListener extends EventListener{
    public EndTurnListener(GameLobby gameLobby) {
        super(gameLobby);
    }
    /*
    private final ServerView serverView;

    public EndTurnListener(ServerView serverView) {
        this.serverView = serverView;
    }

     */


    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        ModelView model=(ModelView) newValue;
        ItemTileView[][] bookshelfView=model.getBookshelfView(playerNickname);
        PlayerPointsView playerPointsView=model.getPlayerPoints(playerNickname);
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.WHO_CHANGE,playerNickname);
        payload.put(KeyPayload.NEW_BOOKSHELF,bookshelfView);
        payload.put(KeyPayload.POINTS, playerPointsView);
        MessageFromServer messageFromServer=new MessageFromServer(new ServerMessageHeader(EventType.END_TURN,playerNickname),payload);
        //TODO aggiungere i token
        //TODO inviarlo a client socket
        //getServerView().sendMessage(payload, MessageFromServerType.DATA,playerNickname);
    }
}
