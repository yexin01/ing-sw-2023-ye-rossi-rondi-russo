package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientMain;
import it.polimi.ingsw.view.ClientInterface;

public class ErrorHandler extends MessageHandler {
    private final LobbyHandler lobbyHandler;
    public ErrorHandler(ClientInterface clientInterface, Client client, LobbyHandler lobbyHandler) {
        super(clientInterface, client);
        this.lobbyHandler = lobbyHandler;
    }



    @Override
    public void handleMessage(Message mes) throws Exception {
        KeyErrorPayload key= (KeyErrorPayload) mes.getPayload().getKey();
        MessagePayload payload=mes.getPayload();
        ErrorType error=(ErrorType) mes.getPayload().getContent(Data.ERROR);
        getClientInterface().displayError(error.getErrorMessage());
        switch(key){
            case ERROR_DATA -> {
                if(error.equals(ErrorType.WRONG_PHASE)){
                    switch(getClientInterface().getTurnPhase()){
                        case SELECT_FROM_BOARD -> getClientInterface().askCoordinates();
                        case SELECT_ORDER_TILES -> getClientInterface().askOrder();
                        case SELECT_COLUMN -> getClientInterface().askColumn();
                    }

                }
                if(!error.equals(ErrorType.ILLEGAL_TURN)){
                    /*
                    switch(getClientInterface().getTurnPhase()){
                        //case SELECT_FROM_BOARD -> getClientInterface().askCoordinates();
                        case SELECT_ORDER_TILES -> getClientInterface().askOrder();
                        case SELECT_COLUMN -> getClientInterface().askColumn();
                    }

                     */

                }
            }
            case ERROR_CONNECTION -> {
                if(error.equals(ErrorType.PING_NOT_RECEIVED)){
                }
                if(error.equals(ErrorType.ERR_NICKNAME_LENGTH) || error.equals(ErrorType.ERR_NICKNAME_TAKEN)){
                    getClientInterface().askNicknameAndConnection();
                }

            }
            case ERROR_LOBBY -> {
            if(error.equals(ErrorType.ERR_RECONNECT_TO_GAME_LOBBY) || error.equals(ErrorType.ERR_JOIN_GLOBAL_LOBBY)){
                    //new ClientMain();
                }else {
                    getClientInterface().askLobbyDecision();
            }
            }


        }

        System.out.println("SONO L'ERROR HANDLER");
    }


}
