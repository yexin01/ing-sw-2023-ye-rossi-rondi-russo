package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

public class StartAndEndGameHandler extends MessageHandler {


    public StartAndEndGameHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(Message mes) throws Exception {
        System.out.println("SONO IN START AND GAME HANDLER");
        TurnPhase data = (TurnPhase) mes.getPayload().getKey();

        switch(data){
            //sia all'inizio del game che durant la partita se un utente si disconnette
            case ALL_INFO ->{
                String p=((String)mes.getPayload().getContent(Data.WHO_CHANGE));
                getClientInterface().displayMessage("Turn player is: "+p);
                BoardBoxView[][] boardView= (BoardBoxView[][]) mes.getPayload().getContent(Data.NEW_BOARD);
                getClientInterface().getClientView().setBoardView(boardView);
                ItemTileView[][] bookshelfView=((ItemTileView[][])mes.getPayload().getContent(Data.NEW_BOOKSHELF));
                getClientInterface().getClientView().setBookshelfView(bookshelfView);
                PersonalGoalCard personalGoalCard=((PersonalGoalCard)mes.getPayload().getContent(Data.PERSONAL_GOAL_CARD));
                getClientInterface().getClientView().setPlayerPersonalGoal(personalGoalCard);
                String[] players=((String[])mes.getPayload().getContent(Data.PLAYERS));
                getClientInterface().getClientView().setPlayers(players);
                int[] idCommon=((int[])mes.getPayload().getContent(Data.IDCOMMON));
                getClientInterface().getClientView().setIdCommonGoals(idCommon);
                int idPersonal=((int)mes.getPayload().getContent(Data.IDPERSONAL));
                getClientInterface().getClientView().setIdPersonal(idPersonal);
                CommonGoalView[] commonGoalViews=((CommonGoalView[])mes.getPayload().getContent(Data.COMMON_GOAL_CARD));
                getClientInterface().getClientView().setCommonGoalViews(commonGoalViews);
                PlayerPointsView playerPointsView=((PlayerPointsView)mes.getPayload().getContent(Data.POINTS));
                getClientInterface().getClientView().setPlayerPoints(playerPointsView);
                System.out.println("I GIOCATORI SONO\n");
                for (String str : players) {
                    System.out.println(str);
                }
                //se si é disconnesso l'utente si troverà automaticamente nella fase end Turn, in piu se non e di turno non entrera in questo if
                if(mes.getPayload().getContent(Data.WHO_CHANGE).equals(getClientInterface().getClientView().getNickname())){
                    switch((TurnPhase)mes.getPayload().getContent(Data.PHASE)) {
                        case END_TURN, SELECT_FROM_BOARD ,ALL_INFO-> {
                            getClientInterface().askCoordinates();
                        }
                        case SELECT_ORDER_TILES -> {
                            System.out.println("anche i selected Items");
                            ItemTileView[] selectedItems = ((ItemTileView[]) mes.getPayload().getContent(Data.SELECTED_ITEMS));
                            getClientInterface().getClientView().setTilesSelected(selectedItems);
                            getClientInterface().askOrder();
                        }
                        case SELECT_COLUMN -> {
                            System.out.println("anche i selected items");
                            ItemTileView[] selectedItems = ((ItemTileView[]) mes.getPayload().getContent(Data.SELECTED_ITEMS));
                            getClientInterface().getClientView().setTilesSelected(selectedItems);
                            getClientInterface().askColumn();
                        }
                    }
                }else{
                    getClientInterface().start();
                }

            }
            case END_GAME ->{
                getClientInterface().endGame();
            }
        }




    }
}
/*
messaggio message

        createGame lobbi + numberOfPlayer valueClient


        joinspecifi game lobby  value client idgame


        joinedRandomGame






 */
