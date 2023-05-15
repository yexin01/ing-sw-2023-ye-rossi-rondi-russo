package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
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
        ErrorType error=(ErrorType) mes.getPayload().getContent(Data.ERROR);
        getClientInterface().displayError(error.getErrorMessage());
        switch(key){
            case ERROR_DATA -> {
                if(!error.equals(ErrorType.ILLEGAL_TURN)){
                    getClientInterface().getClientView().somethingWrong();
                }
            }
            case ERROR_CONNECTION -> {
                switch(error){
                    //TODO finire gli errori di disconnessione
                    /*
                    case DISCONNECTION -> {
                        switch((KeyErrorPayload)mes.getPayload().getContent(Data.CONTENT)){
                            //un giocatore si é riconnesso
                            case BROADCAST,RECONNECTION_DURING_GAME -> getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.VALUE_CLIENT));
                            //case CONNECTION_CREATION ->
                            //case DISCONNECTION_FORCED ->
                        }


                    }

                     */
                    //mandato direttamente dal clientHandler ti sei disconnesso questa gestine potrà cambiare
                    case PING_NOT_RECEIVED -> {
                        getClientInterface().displayError(error.getErrorMessage());
                        //getClientInterface().askNicknameAndConnection();
                    }
                    //errore nella login chiede di riconnettersi
                    case ERR_NICKNAME_LENGTH,ERR_NICKNAME_TAKEN ->{
                        getClientInterface().askNicknameAndConnection();
                    }
                    default->{
                        getClientInterface().displayMessage((String) mes.getPayload().getContent(Data.CONTENT));
                        getClientInterface().endGame();
                    }
                }

            }
            case ERROR_LOBBY -> {
                //il gioco continua automaticamente con startGame
                //if(error.equals(ErrorType.ERR_NO_FREE_SPOTS)){
                    //System.out.println("IN REALTA NON SEI UN ERRORE PERCHE IL GIOCO CONTINUA");
                /*} else*/ if(error.equals(ErrorType.ERR_RECONNECT_TO_GAME_LOBBY) || error.equals(ErrorType.ERR_JOIN_GLOBAL_LOBBY)){
                    getClientInterface().askNicknameAndConnection();
                }else {
                    getClientInterface().askLobbyDecision();
                }
            }


        }

        System.out.println("SONO L'ERROR HANDLER");
    }


}
