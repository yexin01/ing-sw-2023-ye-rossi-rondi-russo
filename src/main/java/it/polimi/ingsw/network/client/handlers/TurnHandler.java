package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

public class TurnHandler extends MessageHandler {

    public TurnHandler(ClientInterface clientInterface, Client client, StartAndEndGameHandler startAndEndGameHandler) {
        super(clientInterface, client);
        this.startAndEndGameHandler = startAndEndGameHandler;
    }
    private final StartAndEndGameHandler startAndEndGameHandler;

    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        TurnPhase turnPhase=(TurnPhase) mes.getPayload().getKey();
        //System.out.println("SONO TURN HANDLER"+turnPhase);
        switch(turnPhase){
            case SELECT_ORDER_TILES ->{
                getClientInterface().askOrder();
            }
            case SELECT_COLUMN ->{
                getClientInterface().askColumn();
            }
            case END_TURN -> {
                //System.out.println("END TURN CLIENT");
               BoardBoxView[][] boardBoxViews= (BoardBoxView[][]) mes.getPayload().getContent(Data.NEW_BOARD);
               getClientInterface().getClientView().setBoardView(boardBoxViews);
               PlayerPointsView[] points=(PlayerPointsView[]) mes.getPayload().getContent(Data.POINTS);
               getClientInterface().getClientView().setPlayerPointsViews(points);
               String player= (String) mes.getPayload().getContent(Data.WHO_CHANGE);
               getClientInterface().getClientView().setPersonalPoints((int)mes.getPayload().getContent(Data.PERSONAL_POINTS));
               int[][] commonGoalViews= (int[][]) mes.getPayload().getContent(Data.COMMON_GOAL);
               getClientInterface().getClientView().setCommonGoalView(commonGoalViews);
               int[] token= (int[]) mes.getPayload().getContent(Data.TOKEN);
               String turnPlayer=(String) mes.getPayload().getContent(Data.NEXT_PLAYER);
               getClientInterface().getClientView().setTurnPlayer(turnPlayer);
               for(int num:token){
                   if(num!=0){
                       //getClientInterface().displayMessage(num+"vinto da "+player+" della common goal numero "+(i+1)+" identita"+getClientInterface().getClientView().getCommonGoalView()[0][i]+"\n");
                       getClientInterface().displayToken(num,player);
                   }
                }
                //é il suo turno
                if(turnPlayer.equals(getClientInterface().getClientView().getNickname())){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    getClientInterface().askCoordinates();
                    //non é il suo turno
                }else getClientInterface().waitingRoom();
            }
            default -> {
                startAndEndGameHandler.handleMessage(mes);
                return;
            }
        }
    }
}
