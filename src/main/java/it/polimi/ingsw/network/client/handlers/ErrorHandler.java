package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientMain;
import it.polimi.ingsw.view.ClientInterface;

public class ErrorHandler extends MessageHandler {

    public ErrorHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);

    }

    @Override
    public synchronized void handleMessage(Message mes) throws Exception {
        KeyErrorPayload key= (KeyErrorPayload) mes.getPayload().getKey();
        ErrorType error=(ErrorType) mes.getPayload().getContent(Data.ERROR);
        switch(key){
            case ERROR_DATA -> {
                if(!error.equals(ErrorType.ILLEGAL_TURN)){
                    getClientInterface().displayError(error.getErrorMessage());
                    getClientInterface().getClientView().somethingWrong();
                }
            }
            case ERROR_CONNECTION -> {
                switch(error){
                    case ONLY_PLAYER -> {
                        if(!getClient().isOnlyOnePlayer()){
                            getClientInterface().displayError(error.getErrorMessage());
                            getClient().setOnlyOnePlayer(true);
                        }

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                         System.out.println("Il thread è stato interrotto e terminato.");
                        getClientInterface().getClientView().receiveError();
                    }

                    //mandato direttamente dal clientHandler ti sei disconnesso questa gestine potrà cambiare
                    case PING_NOT_RECEIVED -> {
                        getClientInterface().displayError(error.getErrorMessage());
                        //getClientInterface().displayError(error.getErrorMessage());
                    }
                    //errore nella login chiede di riconnettersi
                    case ERR_NICKNAME_LENGTH,ERR_NICKNAME_TAKEN ->{
                        getClientInterface().displayError(error.getErrorMessage());
                        getClientInterface().askNicknameAndConnection();
                    }
                    default->{
                        getClientInterface().displayError(error.getErrorMessage());
                        getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.CONTENT));
                    }
                }
            }
            case ERROR_LOBBY -> {
                getClientInterface().displayError(error.getErrorMessage());
                if(error.equals(ErrorType.ERR_RECONNECT_TO_GAME_LOBBY) || error.equals(ErrorType.ERR_JOIN_GLOBAL_LOBBY)){
                    getClientInterface().askNicknameAndConnection();
                }else {
                    getClientInterface().askLobbyDecision();
                }
            }


        }

        System.out.println("SONO L'ERROR HANDLER");
    }



}
