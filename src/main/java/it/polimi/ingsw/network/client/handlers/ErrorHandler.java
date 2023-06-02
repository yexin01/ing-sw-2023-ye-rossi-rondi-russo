package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.*;
import it.polimi.ingsw.network.client.Client;
import it.polimi.ingsw.network.client.ClientMain;
import it.polimi.ingsw.view.ClientInterface;
/**
 * The ErrorHandler class extends the MessageHandler class and handles incoming error messages.
 * It provides methods to handle and process different types of error messages and take appropriate actions based on the error type.
 */
public class ErrorHandler extends MessageHandler {
    /**
     * Constructs an ErrorHandler object with the specified client interface and client.
     *
     * @param clientInterface The client interface used for displaying messages and interacting with the client.
     * @param client The client object associated with this error handler.
     */
    public ErrorHandler(ClientInterface clientInterface, Client client) {
        super(clientInterface, client);

    }
    /**
     * Overrides the handleMessage method from the MessageHandler class.
     * Handles the incoming error message and performs the necessary actions based on the error type and key.
     *
     * @param mes The message object to be handled.
     * @throws Exception if an error occurs while handling the message.
     */
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
                       getClientInterface().onlyPlayer();

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

        //System.out.println("SONO L'ERROR HANDLER");
    }



}
