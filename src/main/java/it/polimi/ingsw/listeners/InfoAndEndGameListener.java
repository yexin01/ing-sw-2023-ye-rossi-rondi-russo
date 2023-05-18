package it.polimi.ingsw.listeners;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.network.server.GlobalLobby;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                if(playerNickname!=null){
                    Message message=creationMessageInfo(playerNickname,modelView);
                    getGameLobby().sendMessageToSpecificPlayer(message,playerNickname) ;
             }else{
                    for(PlayerPointsView nickname: modelView.getPlayerPoints()){
                        getGameLobby().sendMessageToSpecificPlayer(creationMessageInfo(nickname.getNickname(),modelView),nickname.getNickname()) ;
                    }
                }
            }
            case END_GAME ->{
                MessageHeader header=new MessageHeader(MessageType.DATA,null);
                MessagePayload payload=new MessagePayload(TurnPhase.END_GAME);
                //payload.put(Data.RANKING,newValue);
                Message message=new Message(header,payload);
                getGameLobby().sendMessageToAllPlayers(message);
                getGameLobby().setMessageEndGame(message);
            }
        }
    }
    public Message creationMessageInfo(String nickname,ModelView modelView){
        getGameLobby().setModelView(modelView);
        MessageHeader header;
        MessagePayload payload=new MessagePayload(TurnPhase.ALL_INFO);
        BoardBoxView[][] boardView= modelView.getBoardView();
        header=new MessageHeader(MessageType.DATA,nickname);
        payload.put(Data.NEW_BOARD,boardView);
        ItemTileView[][] bookshelfView=modelView.getBookshelfView(nickname);
        payload.put(Data.NEW_BOOKSHELF,bookshelfView);
        payload.put(Data.COMMON_GOAL,modelView.getCommonGoalView());
        PersonalGoalCard personalGoalCard=modelView.getPlayerPersonalGoal(nickname);
        payload.put(Data.PERSONAL_GOAL_CARD,personalGoalCard);
        PlayerPointsView[] playerPointsView=modelView.getPlayerPoints();
        payload.put(Data.POINTS,playerPointsView);
        payload.put(Data.WHO_CHANGE,playerPointsView[modelView.getTurnPlayer()].getNickname());
        payload.put(Data.PHASE,modelView.getTurnPhase());
        Message m=new Message(header,payload);
       return m;
    }
    public void endGame(){
        try {
            globalLobby.endGameLobbyFromGlobalLobby(getGameLobby().getIdGameLobby());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}