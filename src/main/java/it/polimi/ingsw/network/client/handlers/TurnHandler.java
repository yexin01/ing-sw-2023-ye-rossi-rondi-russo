package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;
/**
 * The TurnHandler class extends the MessageHandler class and handles incoming messages related to the game turns.
 * It processes the incoming message and performs the necessary actions based on the turn phase data.
 * It also delegates certain messages to the StartAndEndGameHandler for further handling.
 */
public class TurnHandler extends MessageHandler {
    /**
     * Constructs a TurnHandler object with the specified client interface, client, and startAndEndGameHandler.
     *
     * @param clientInterface The client interface used for displaying messages and interacting with the client.
     * @param client The client object associated with this turn handler.
     * @param startAndEndGameHandler The start and end game handler used for handling certain messages.
     */
    public TurnHandler(ClientInterface clientInterface, Client client, StartAndEndGameHandler startAndEndGameHandler) {
        super(clientInterface, client);
        this.startAndEndGameHandler = startAndEndGameHandler;
    }
    private final StartAndEndGameHandler startAndEndGameHandler;
    /**
     * Overrides the handleMessage method from the MessageHandler class.
     * Handles the incoming message by processing the turn phase data and performing the necessary actions based on the data.
     * Certain messages, such as actions related to the start and end of the game or when the entire ClientView is reset
     * after a disconnection, are delegated to the StartAndEndGameHandler for further handling.
     *
     * @param mes The message object to be handled.
     * @throws Exception if an error occurs while handling the message.
     */
    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        TurnPhase turnPhase=(TurnPhase) mes.getPayload().getKey();
        switch(turnPhase){
            case SELECT_ORDER_TILES ->{
                getClientInterface().askOrder();
            }
            case SELECT_COLUMN ->{
                getClientInterface().askColumn();
            }
            case END_TURN -> {
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
                       getClientInterface().displayToken(num,player);
                   }
                }
                if(turnPlayer.equals(getClientInterface().getClientView().getNickname())){
                    getClientInterface().askCoordinates();
                }else getClientInterface().waitingRoom();
            }
            default -> {
                startAndEndGameHandler.handleMessage(mes);
                return;
            }
        }
    }
}
