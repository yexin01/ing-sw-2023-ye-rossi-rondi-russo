package it.polimi.ingsw.listeners;

//TODO verrà cancellato

/*
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.KeyPayload;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageHeader;
import it.polimi.ingsw.network.server.ServerView;

public class InfoPlayerListener extends EventListener{
    protected InfoPlayerListener(ServerView serverView) {
        super(serverView);
    }

    @Override
    public void fireEvent(EventType event, String playerNickname, Object newValue) {
        ServerMessageHeader header=new ServerMessageHeader(EventType.ALL_INFO,playerNickname);
        MessagePayload payload=new MessagePayload();

        /*
        payload.put(KeyPayload.NEW_BOARD, modelView.getBoardView());
        payload.put(KeyPayload.NEW_BOOKSHELF, modelView.getBookshelfView(playerNickname));
        payload.put(KeyPayload.POINTS,modelView.getPlayerPoints(playerNickname));
        payload.put(KeyPayload.TOKEN,modelView.getCommonGoalViews());
        payload.put(KeyPayload.PERSONAL_GOAL_CARD,modelView.getPlayerPersonal(playerNickname));
        //payload.put(PayloadKeyServer.TOKEN,modelView.getCommonGoalViews());
        //TODO gestire invio messaggio (quando viene inizializzato il gioco viene inviato questo messaggio)
        //sendMessage(payload,MessageFromServerType.DATA,playerNickname);


    }
}

            */

