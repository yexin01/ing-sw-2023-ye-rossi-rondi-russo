package it.polimi.ingsw.network.client.handlers;


import com.sun.net.httpserver.Headers;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

import java.io.IOException;
import java.rmi.RemoteException;

public class StartAndEndGameHandler extends MessageHandler {


    public StartAndEndGameHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(Message mes) throws IOException {
        System.out.println("SONO IN START AND GAME HANDLER");

        try {
            Thread.sleep(3000);
            System.out.println("Il thread si è interrotto dopo 3 secondi.");
        } catch (InterruptedException e) {
            System.out.println("Il thread è stato interrotto prematuramente.");
        }

        /*
        KeyDataPayload data = (KeyDataPayload) mes.getPayload().getKey();
        MessagePayload messagePayload=null;
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
                messagePayload=new MessagePayload(KeyDataPayload.START_GAME);
            }
            case END_GAME ->{
                String[] ranking=(String[]) mes.getPayload().getContent(Data.RANKING);
                messagePayload=new MessagePayload(KeyDataPayload.END_GAME);
            }
        }
        MessageHeader messageHeader=new MessageHeader(MessageType.DATA,getClientInterface().getNickname());
        Message message=new Message(messageHeader, messagePayload);
        getClient().sendMessageToServer(message);

         */

    }
}
/*
messaggio message

        createGame lobbi + numberOfPlayer valueClient


        joinspecifi game lobby  value client idgame


        joinedRandomGame






 */
