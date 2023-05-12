package it.polimi.ingsw.listeners;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;
import it.polimi.ingsw.network.server.GameLobby;
import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.ClientView;

import java.io.IOException;
import java.util.ArrayList;

public class StartAndEndGameListener extends EventListener{
    public StartAndEndGameListener(GameLobby gameLobby) {
        super(gameLobby);
    }

    @Override
    public void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) {
        switch((KeyDataPayload)event){
            case START_GAME ->{
                ModelView modelView=(ModelView) newValue;
                getGameLobby().setModelView(modelView);
                MessageHeader header;
                MessagePayload payload=new MessagePayload(KeyDataPayload.START_GAME);
                BoardBoxView[][] boardView= modelView.getBoardView();

                ArrayList<String> nicknames=modelView.getPlayersOrder();
                for(String nickname:nicknames){
                    header=new MessageHeader(MessageType.DATA,nickname);
                    payload.put(Data.NEW_BOARD,boardView);
                    ItemTileView[][] bookshelfView=modelView.getBookshelfView(nickname);
                    payload.put(Data.NEW_BOOKSHELF,bookshelfView);
                    PersonalGoalCard personalGoalCard=modelView.getPlayerPersonalGoal(nickname);
                    payload.put(Data.PERSONAL_GOAL_CARD,personalGoalCard);
                    PlayerPointsView playerPointsView=modelView.getPlayerPoints(nickname);
                    payload.put(Data.POINTS,playerPointsView);
                    CommonGoalView[] commonGoalViews=modelView.getCommonGoalViews();
                    payload.put(Data.COMMON_GOAL_CARD,commonGoalViews);
                    payload.put(Data.PLAYERS,nicknames.toArray(new String[nicknames.size()]));
                    Message m=new Message(header,payload);
                    try {
                        getGameLobby().sendMessageToSpecificPlayer(m,nickname);
                    } catch (IOException e) {
                        //TODO gestire se non e arrivato
                    }
                }
            }
            case END_GAME ->{
                //TODO in base a come implementiamo la CLI e la GUI vediamo cosa inserire nel messaggio di endGame

            }
        }

    }
}




                /*
                ClientView clientView=new ClientView();
                CLI cl=new CLI();
                clientView.setBoardView(((ModelView) newValue).getBoardView());
                clientView.setBookshelfView(((ModelView) newValue).getBookshelfView("TIZIO"));
                clientView.setPlayerPersonalGoal(((ModelView) newValue).getPlayerPersonalGoal("TIZIO"));
                cl.setClientView(clientView);
                try {
                    cl.askCoordinates();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                 */