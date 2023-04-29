package it.polimi.ingsw.network.client.messageHandlers;

import it.polimi.ingsw.listeners.ListenerManager;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.ClientListener;
import it.polimi.ingsw.network.client.ClientSocket;

import it.polimi.ingsw.view.ClientView;

import java.util.List;

public class DataHandler extends MessageHandler {

    private ListenerManager listeners=new ListenerManager();
    protected DataHandler(ClientSocket connection, ClientListener clientListener, ClientView clientView) {
        super(connection, clientListener, clientView);
    }

    @Override
    void handleMessage(MessageFromServer mes) throws Exception {
        switch(mes.getMessagePayload().getEvent()){
            case BOARD_SELECTION->boardSelection(mes);
            case END_TURN->endTurn(mes);
            case WIN_TOKEN->{
                //TODO gestione dei token verrà cambiata
                if(getMessageWhoChange(mes).equals(getClientListener().getNickname())){
                    //winToken(mes);
                    //else loseToken(mes);
                }

            }
            case ALL_INFO->allInfo(mes);
            case TILES_SELECTED->tilesSelected(mes);
        }
    }


    public void boardSelection(MessageFromServer mes){
        BoardBoxView[][] newBoard=(BoardBoxView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD);
        getClientView().setBoardView(newBoard);
        System.out.println(getMessageWhoChange(mes)+" change board");
        listeners.fireEvent(EventType.valueOf("Board_Selection"),null,newBoard);


        //printMatrixBoard();
    }
    public void endTurn(MessageFromServer mes) throws Exception {
        getClientView().setPlayerPoints((PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS));
        getClientView().setBookshelfView((ItemTileView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));
        listeners.fireEvent(EventType.valueOf("EndTurn"),null,mes);
        //printMatrixBookshelf();
        throw new Exception();
    }
    public void endGame(MessageFromServer mes) throws Exception {
        List<EventType> ranking=(List<EventType>) mes.getMessagePayload().get(PayloadKeyServer.RANKING);
        for (EventType nickname : ranking) {
            System.out.println(nickname);
        }
        listeners.fireEvent(EventType.valueOf("EndGame"),null,ranking);
        throw new Exception();
    }




    public void allInfo(MessageFromServer mes){
        getClientView().setBoardView((BoardBoxView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        //printMatrixBoard();
        getClientView().setBookshelfView((ItemTileView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));;
        //printMatrixBookshelf();
        getClientView().setPlayerPoints((PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS));
        //printPlayerPoints();
        getClientView().setCommonGoalViews((CommonGoalView[]) mes.getMessagePayload().get(PayloadKeyServer.TOKEN));
        getClientView().setPlayerPersonalGoal((PersonalGoalCard) mes.getMessagePayload().get(PayloadKeyServer.PERSONAL_GOAL));
        //printCommonGoalPoints();
        //printPersonalGoal();
        listeners.fireEvent(EventType.valueOf("All_Info"),null,mes);

    }
    public void tilesSelected(MessageFromServer mes){
        getClientView().setBoardView((BoardBoxView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        //printMatrixBoard();
        getClientView().setTilesSelected((ItemTileView[]) mes.getMessagePayload().get(PayloadKeyServer.TILES_SELECTED));
        //printItemTilesSelected();
        listeners.fireEvent(EventType.valueOf("TileSelected"),null,mes);
    }
    public String getMessageWhoChange(MessageFromServer mes){
        return (String)mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE);

    }
    //TODO token arriva con il messaggio di entTurn, questi metodi verranno spostati
    /*
    public void winToken(MessageFromServer mes){
        int index=(int)mes.getMessagePayload().get(PayloadKeyServer.INDEX_TOKEN);
        getClientView().setCommonGoalPoints(new int[]{(int) mes.getMessagePayload().get(PayloadKeyServer.POINTS)},index);
        getClientView().setCommonGoalViews(new CommonGoalView[]{(CommonGoalView) mes.getMessagePayload().get(PayloadKeyServer.TOKEN)},index);
        //printCommonGoalPoints();
        //System.out.println("YOU WON TOKEN POINTS"+getClientView().getCommonGoalViews()[index].getPointsWon()+" n:"+(index+1)+" .Points that remain: "+getClientView().getCommonGoalViews()[index].getLastPointsLeft());
    }
    public void loseToken(MessageFromServer mes){
        int index=(int)mes.getMessagePayload().get(PayloadKeyServer.INDEX_TOKEN);
        getClientView().setCommonGoalViews(new CommonGoalView[]{(CommonGoalView) mes.getMessagePayload().get(PayloadKeyServer.TOKEN)},index);
        //System.out.println("YOU LOSE TOKEN POINTS"+getClientView().getCommonGoalViews()[index].getPointsWon()+" n:"+(index+1)+" .Points that remain: "+getClientView().getCommonGoalViews()[index].getLastPointsLeft());
    }

     */

}
