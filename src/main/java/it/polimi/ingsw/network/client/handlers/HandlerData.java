package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.network.server.ErrorType;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;

import java.util.List;


public class HandlerData extends MessageHandler {


    //TODO questa classe verrà cambiata suddividendo la gestione del messaggi in diversi handler

    public HandlerData(ClientSocket connection, ClientInterface clientInterface) {
        super(connection, clientInterface);
    }

    @Override
    public void handleMessage(MessageFromServer messageServer) {
        switch(messageServer.getServerMessageHeader().getMessageFromServer()){
            case DATA -> receiveMessageDataFromServer(messageServer);
            case ERROR ->System.out.println("ERROR "+((ErrorType)messageServer.getMessagePayload().get(PayloadKeyServer.ERRORMESSAGE)).getErrorMessage()) ;
            case START_TURN ->System.out.println(getClientInterface().askCoordinates());
            case RECEIVE ->{
                System.out.println("Server has received the sent message ");
                // askClient(5);
            }
            //case END_GAME->endGame(messageServer);
        }
    }

    public void receiveMessageFromServer(String nickname, MessageFromServer messageServer) {
        try{
            System.out.println(nickname + " RECEIVE MESSAGE");
            switch (messageServer.getServerMessageHeader().getMessageFromServer()) {
                case ERROR ->System.out.println("ERROR "+((ErrorType)messageServer.getMessagePayload().get(PayloadKeyServer.ERRORMESSAGE)).getErrorMessage()) ;
                case START_TURN ->System.out.println(nickname + " START TURN ");
                case DATA ->receiveMessageDataFromServer(messageServer);
                case RECEIVE ->System.out.println("Server has received the sent message ");
                case END_GAME->endGame(messageServer);
            }
        }catch(Exception e){
        }
    }

    private void receiveMessageDataFromServer(MessageFromServer mes) {
        switch(mes.getMessagePayload().getEvent()){
            case BOARD_SELECTION->boardSelection(mes);
            //case END_TURN->endTurn(mes);
            case WIN_TOKEN->{
                //TODO gestione dei token verrà cambiata
                if(getMessageWhoChange(mes).equals(getClientView().getNickname())){
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
        getClientInterface().askCoordinates();
    }
    public void endTurn(MessageFromServer mes) throws Exception {
        getClientView().setPlayerPoints((PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS));
        getClientView().setBookshelfView((ItemTileView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));
        throw new Exception();
    }
    public void endGame(MessageFromServer mes) throws Exception {
        List<String> ranking=(List<String>) mes.getMessagePayload().get(PayloadKeyServer.RANKING);
        for (String nickname : ranking) {
            System.out.println(nickname);
        }
        //listeners.fireEvent(EventType.valueOf("EndGame"),null,ranking);
        throw new Exception();
    }




    public void allInfo(MessageFromServer mes){
        getClientView().setBoardView((BoardBoxView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        //getClientInterface().printMatrixBoard();
        getClientView().setBookshelfView((ItemTileView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOOKSHELF));;
        //getClientInterface().printMatrixBookshelf();
        getClientView().setPlayerPoints((PlayerPointsView) mes.getMessagePayload().get(PayloadKeyServer.POINTS));
        //getClientInterface().printPlayerPoints();
        getClientView().setCommonGoalViews((CommonGoalView[]) mes.getMessagePayload().get(PayloadKeyServer.TOKEN));
        getClientView().setPlayerPersonalGoal((PersonalGoalCard) mes.getMessagePayload().get(PayloadKeyServer.PERSONAL_GOAL));
        //getClientInterface().printCommonGoalPoints();
        //getClientInterface().printPersonalGoal();


    }
    public void tilesSelected(MessageFromServer mes){
        getClientView().setBoardView((BoardBoxView[][]) mes.getMessagePayload().get(PayloadKeyServer.NEWBOARD));
        //getClientInterface().printMatrixBoard();
        getClientView().setTilesSelected((ItemTileView[]) mes.getMessagePayload().get(PayloadKeyServer.TILES_SELECTED));
        //getClientInterface().printItemTilesSelected();
        getClientInterface().askOrder();
        //listeners.fireEvent(EventType.valueOf("TileSelected"),null,mes);
    }
    public String getMessageWhoChange(MessageFromServer mes){
        return (String)mes.getMessagePayload().get(PayloadKeyServer.WHO_CHANGE);

    }
}




/*
public class MessageHandler {
    private ClientInterface clientInterface;
    private ClientSocket clientSocket;
    private Map<String, MessageHandlerInterface> handlers = new HashMap<>();

    public MessageHandler(ClientInterface clientInterface, ClientSocket clientSocket) {
        this.clientInterface = clientInterface;
        this.clientSocket = clientSocket;

        handlers.put("request", () -> clientInterface.ask());
        handlers.put("response", () -> sendMessage(EventType event, DataClientType type,String nickname,int[] value));
        handlers.put("error", () -> clientInterface.handleError());
        handlers.put("outgoing", () -> sendMessage(event,type,nickname,value)); // nuovo handler per i messaggi in uscita
        handlers.put("incoming", (message) -> clientInterface.handleIncomingMessage(message)); // nuovo handler per i messaggi in entrata
    }

    public void handleMessage(String message) {
        // determina il tipo di messaggio e richiama il metodo appropriato
        int separatorIndex = message.indexOf(" ");
        if (separatorIndex == -1) {
            // messaggio non valido, gestisci l'errore
            return;
        }

        String messageType = message.substring(0, separatorIndex);
        MessageHandlerInterface handler = handlers.get(messageType);
        if (handler == null) {
            // messaggio non valido, gestisci l'errore
            return;
        }

        handler.handle(message);
    }

    // Metodo di callback per il messaggio in uscita
    private void sendMessage(String event, String type, String nickname, String value) {
        clientSocket.sendMessage(event, type, nickname, value);
    }

    // Interfaccia funzionale per gli handler di messaggio
    private interface MessageHandlerInterface {
        void handle();
        void handle(String message);
    }
}







/*
public abstract class MessageHandler {
    private HashMap<EventType, List<EventListener>> listenersMap;
    private final ClientSocket connection;
    private final ClientInterface clientInterface;
    private ClientView clientView;

    protected MessageHandler(ClientSocket connection, ClientInterface clientInterface, ClientView clientView) {
        this.connection = connection;
        this.clientInterface = clientInterface;
        this.clientView=clientView;
    }


    public ClientSocket getConnection() {
        return connection;
    }


    public ClientInterface getClientListener() {
        return clientInterface;
    }


    public ClientView getClientView() {
        return clientView;
    }

    public void setClientView(ClientView clientView) {
        this.clientView = clientView;
    }
    abstract void handleMessage(MessageFromServer message) throws Exception;
}

 */