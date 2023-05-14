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
        /*
        MessagePayload messagePayload=null;
        System.out.println(getClientInterface().getClientView().getNickname());
         */
        String[] p=((String[])mes.getPayload().getContent(Data.PLAYERS));


        System.out.println("IL PRIMO GIOCATORE é "+p[0]);
        switch(data){
            case START_GAME->{
                BoardBoxView[][] boardView= (BoardBoxView[][]) mes.getPayload().getContent(Data.NEW_BOARD);
                getClientInterface().getClientView().setBoardView(boardView);
                ItemTileView[][] bookshelfView=((ItemTileView[][])mes.getPayload().getContent(Data.NEW_BOOKSHELF));
                getClientInterface().getClientView().setBookshelfView(bookshelfView);
                PersonalGoalCard personalGoalCard=((PersonalGoalCard)mes.getPayload().getContent(Data.PERSONAL_GOAL_CARD));
                getClientInterface().getClientView().setPlayerPersonalGoal(personalGoalCard);
                String[] players=((String[])mes.getPayload().getContent(Data.PLAYERS));
                getClientInterface().getClientView().setPlayers(players);
                CommonGoalView[] commonGoalViews=((CommonGoalView[])mes.getPayload().getContent(Data.COMMON_GOAL_CARD));
                getClientInterface().getClientView().setCommonGoalViews(commonGoalViews);
                PlayerPointsView playerPointsView=((PlayerPointsView)mes.getPayload().getContent(Data.POINTS));
                getClientInterface().getClientView().setPlayerPoints(playerPointsView);
                System.out.println("I GIOCATORI SONO\n");
                for (String str : players) {
                    System.out.println(str);
                }
                if(!getClientInterface().getTurnPhase().equals(TurnPhase.START_GAME)){
                    System.out.println("RICEVUTO I DATI tutto ok ricevuto ACK dell'error message");
                    switch(getClientInterface().getTurnPhase()){
                        case SELECT_FROM_BOARD -> getClientInterface().askCoordinates();
                        case SELECT_ORDER_TILES ->{
                            ItemTileView[] selectedItems=((ItemTileView[])mes.getPayload().getContent(Data.SELECTED_ITEMS));
                            getClientInterface().getClientView().setTilesSelected(selectedItems);
                            getClientInterface().askOrder();
                        }
                        case SELECT_COLUMN->{
                            ItemTileView[] selectedItems=((ItemTileView[])mes.getPayload().getContent(Data.SELECTED_ITEMS));
                            getClientInterface().getClientView().setTilesSelected(selectedItems);
                            getClientInterface().askColumn();
                        }
                    }

                } else{
                    getClientInterface().getClientView().setTurnPhase(TurnPhase.END_TURN);
                    if(players[0].equals(getClientInterface().getClientView().getNickname())){
                        getClientInterface().getClientView().setTurnPhase(TurnPhase.SELECT_FROM_BOARD);
                        getClientInterface().askCoordinates();
                    }else getClientInterface().start();
                }
                /*
                MessageHeader messageHeader=new MessageHeader(MessageType.DATA,getClientInterface().getNickname());
                Message message=new Message(messageHeader, messagePayload);
                getClient().sendMessageToServer(message);

                 */
                //
            }
            case END_GAME ->{
                String[] ranking=(String[]) mes.getPayload().getContent(Data.RANKING);
                //messagePayload=new MessagePayload(KeyDataPayload.END_GAME);
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
