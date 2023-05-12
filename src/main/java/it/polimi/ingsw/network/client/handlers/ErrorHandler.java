package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.listeners.StartAndEndGameListener;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.view.ClientInterface;

public class ErrorHandler extends MessageHandler {
    public ErrorHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);
    }


    @Override
    public void handleMessage(Message mes) throws Exception {
        KeyErrorPayload key= (KeyErrorPayload) mes.getPayload().getKey();
        MessagePayload payload=mes.getPayload();
        ErrorType error=(ErrorType) mes.getPayload().getContent(Data.ERROR);
        getClientInterface().displayError(error.getErrorMessage());
        switch(key){
            case ERROR_DATA -> {


            }
            case ERROR_CONNECTION -> {
                if(error.equals(ErrorType.PING_NOT_RECEIVED)){
                    System.out.println(error.getErrorMessage());
                }
                if(error.equals(ErrorType.ERR_NICKNAME_LENGTH) || error.equals(ErrorType.ERR_NICKNAME_TAKEN)){
                    getClientInterface().askNicknameAndConnection();
                }

            }
            case ERROR_LOBBY -> {
                if(error.equals(ErrorType.ERR_RECONNECT_TO_GAME_LOBBY) || error.equals(ErrorType.ERR_JOIN_GLOBAL_LOBBY)){
                    getClientInterface().askNicknameAndConnection();
                }
                else getClientInterface().askLobbyDecision();

            }


        }

        System.out.println("SONO L'ERROR HANDLER");
    }
}
