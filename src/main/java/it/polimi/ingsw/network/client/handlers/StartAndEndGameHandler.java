package it.polimi.ingsw.network.client.handlers;


import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientInterface;
/**
 * The StartAndEndGameHandler class extends the MessageHandler class and handles incoming messages related to the start and end of the game.
 * It processes the incoming message and performs the necessary actions based on the message content.
 * It also handles the scenario where the user requests information from the server and receives a response that sets all attributes of
 * clientView to continue the game.
 */
public class StartAndEndGameHandler extends MessageHandler {

    /**
     * Constructs a StartAndEndGameHandler object with the specified client interface and client.
     *
     * @param clientInterface The client interface used for displaying messages and interacting with the client.
     * @param client The client object associated with this start and end game handler.
     */
    public StartAndEndGameHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }
    /**
     * Overrides the handleMessage method from the MessageHandler class.
     * Handles the incoming message by processing the turn phase data and performing the necessary actions based on the data.
     *
     * @param mes The message object to be handled.
     * @throws Exception if an error occurs while handling the message.
     */
    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        TurnPhase data = (TurnPhase) mes.getPayload().getKey();

        switch(data){

            case ALL_INFO ->{
                BoardBoxView[][] boardView= (BoardBoxView[][]) mes.getPayload().getContent(Data.NEW_BOARD);
                getClientInterface().getClientView().setBoardView(boardView);
                ItemTileView[][] bookshelfView=((ItemTileView[][])mes.getPayload().getContent(Data.NEW_BOOKSHELF));
                getClientInterface().getClientView().setBookshelfView(bookshelfView);
                PersonalGoalCard personalGoalCard=((PersonalGoalCard)mes.getPayload().getContent(Data.PERSONAL_GOAL_CARD));
                getClientInterface().getClientView().setPlayerPersonalGoal(personalGoalCard);
                getClientInterface().getClientView().setPersonalPoints((int)mes.getPayload().getContent(Data.PERSONAL_POINTS));
                int[][] commonGoalsView=((int[][])mes.getPayload().getContent(Data.COMMON_GOAL));
                getClientInterface().getClientView().setCommonGoalView(commonGoalsView);
                PlayerPointsView[] playerPointsView=((PlayerPointsView[])mes.getPayload().getContent(Data.POINTS));
                String turnPlayer=(String) mes.getPayload().getContent(Data.NEXT_PLAYER);
                getClientInterface().getClientView().setTurnPlayer(turnPlayer);
                getClientInterface().getClientView().setPlayerPointsViews(playerPointsView);
                Check.MAX_SELECTABLE_TILES= (int) mes.getPayload().getContent(Data.MAX_SELECTABLE_TILES);
                if(turnPlayer.equals(getClientInterface().getClientView().getNickname())){
                    switch((TurnPhase)mes.getPayload().getContent(Data.PHASE)) {
                        case END_TURN, SELECT_FROM_BOARD ,ALL_INFO-> {
                            getClientInterface().askCoordinates();
                        }
                        case SELECT_ORDER_TILES -> {
                            ItemTileView[] selectedItems = ((ItemTileView[]) mes.getPayload().getContent(Data.SELECTED_ITEMS));
                            getClientInterface().getClientView().setTilesSelected(selectedItems);
                            getClientInterface().askOrder();
                        }
                        case SELECT_COLUMN -> {
                            ItemTileView[] selectedItems = ((ItemTileView[]) mes.getPayload().getContent(Data.SELECTED_ITEMS));
                            getClientInterface().getClientView().setTilesSelected(selectedItems);
                            getClientInterface().askColumn();
                        }
                    }
                }else{
                    getClientInterface().waitingRoom();
                }
            }
            case END_GAME ->{
                getClientInterface().getClientView().setPlayerPointsViews((PlayerPointsView[]) mes.getPayload().getContent(Data.POINTS));
                getClientInterface().endGame((int[]) mes.getPayload().getContent(Data.PERSONAL_POINTS),(String)mes.getPayload().getContent(Data.BOOKSHELF_FULL_PLAYER));
            }
        }
    }
}

