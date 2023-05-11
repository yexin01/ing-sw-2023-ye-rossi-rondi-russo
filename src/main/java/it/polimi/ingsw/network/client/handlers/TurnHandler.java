package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.Client;

import it.polimi.ingsw.view.ClientInterface;

public class TurnHandler extends MessageHandler {

    public TurnHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }

    @Override
    public void handleMessage(Message mes) throws Exception {
        KeyDataPayload data = (KeyDataPayload) mes.getPayload().getKey();
        MessagePayload messagePayload=null;
        switch(data){
            case START_TURN->{
                int[] selectionBoard=getClientInterface().askCoordinates();
                messagePayload=new MessagePayload(KeyDataPayload.SELECTION_PHASE);
            }
            case SELECTION_PHASE ->{
              int[] orderTiles=  getClientInterface().askOrder();
              messagePayload=new MessagePayload(KeyDataPayload.ORDER_PHASE);
            }
            case ORDER_PHASE -> {
                int column=getClientInterface().askColumn();
                messagePayload=new MessagePayload(KeyDataPayload.COLUMN);
            }
            case END_TURN ->{
                BoardBoxView[][] boardView= (BoardBoxView[][]) mes.getPayload().getContent(Data.NEW_BOARD);
                getClientInterface().getClientView().setBoardView(boardView);
                ItemTileView[][] bookshelfView= (ItemTileView[][]) mes.getPayload().getContent(Data.NEW_BOARD);
                PlayerPointsView playerPointsView= (PlayerPointsView) mes.getPayload().getContent(Data.POINTS);
                messagePayload=new MessagePayload(KeyDataPayload.END_TURN);
            }
            /*
            default ->{
                //TODO vedere cosa potrebbe fare

            }

             */
        }
        MessageHeader header=new MessageHeader(MessageType.DATA,getClientInterface().getNickname());
        Message messageToServer=new Message(header,messagePayload);
        getClient().sendMessageToServer(messageToServer);
    }

}
