package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;

import java.io.IOException;
import java.util.ArrayList;

public class InfoAndEndGameListener extends EventListener{
    private final GlobalLobby globalLobby;
    public InfoAndEndGameListener(GameLobby gameLobby, GlobalLobby globalLobby) {
        super(gameLobby);
        this.globalLobby = globalLobby;
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException {
        System.out.println("SONO NEL LISTENER STARTGAME e NEL caso something wrong LATO SERVER sto inviando");

        switch((TurnPhase)event){
            case ALL_INFO ->{
                ModelView modelView=(ModelView) newValue;
                ArrayList<String> nicknames=modelView.getPlayersOrder();
                //TODO questa sarbbe una funzionalità in piu nel caso in cui vengano corrotti i dati
                if(playerNickname!=null){
                    Message message=creationMessageInfo(playerNickname,modelView,nicknames);
                    if(!modelView.getTurnPhase().equals(TurnPhase.SELECT_FROM_BOARD)){
                        MessagePayload payload=message.getPayload();
                        payload.put(Data.SELECTED_ITEMS,modelView.getSelectedItems());
                        Message message1=new Message(message.getHeader(),payload);
                        getGameLobby().sendMessageToSpecificPlayer(message1,playerNickname);
                    }else  getGameLobby().sendMessageToSpecificPlayer(creationMessageInfo(playerNickname,modelView,nicknames),playerNickname) ;
             }else{
                    for(String nickname:nicknames){
                        getGameLobby().sendMessageToSpecificPlayer(creationMessageInfo(nickname,modelView,nicknames),nickname) ;
                    }
                }

            }
            case END_GAME ->{
                MessageHeader header=new MessageHeader(MessageType.DATA,null);
                MessagePayload payload=new MessagePayload(TurnPhase.END_GAME);
                payload.put(Data.RANKING,newValue);
                Message message=new Message(header,payload);
                getGameLobby().sendMessageToAllPlayers(message);
                globalLobby.endGameLobbyFromGlobalLobby(getGameLobby().getIdGameLobby());
            }
        }
    }
    public Message creationMessageInfo(String nickname,ModelView modelView,ArrayList<String> nicknames){
        getGameLobby().setModelView(modelView);
        MessageHeader header;
        MessagePayload payload=new MessagePayload(TurnPhase.ALL_INFO);
        BoardBoxView[][] boardView= modelView.getBoardView();
        header=new MessageHeader(MessageType.DATA,nickname);
        payload.put(Data.NEW_BOARD,boardView);
        ItemTileView[][] bookshelfView=modelView.getBookshelfView(nickname);
        payload.put(Data.IDCOMMON,modelView.getIdCommon());
        payload.put(Data.IDPERSONAL,modelView.getIdPersonal(nickname));
        payload.put(Data.NEW_BOOKSHELF,bookshelfView);
        PersonalGoalCard personalGoalCard=modelView.getPlayerPersonalGoal(nickname);
        payload.put(Data.PERSONAL_GOAL_CARD,personalGoalCard);
        PlayerPointsView playerPointsView=modelView.getPlayerPoints(nickname);
        payload.put(Data.POINTS,playerPointsView);
        String turnPlayer= modelView.getTurnPlayer();
        payload.put(Data.WHO_CHANGE,turnPlayer);
        CommonGoalView[] commonGoalViews=modelView.getCommonGoalViews();
        payload.put(Data.COMMON_GOAL_CARD,commonGoalViews);
        payload.put(Data.PHASE,modelView.getTurnPhase());
        payload.put(Data.PLAYERS,nicknames.toArray(new String[nicknames.size()]));
        Message m=new Message(header,payload);
       return m;
    }
}
