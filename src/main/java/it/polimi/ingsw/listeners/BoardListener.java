package it.polimi.ingsw.listeners;

/*
import it.polimi.ingsw.client.ClientUI;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.modelView.BoardBoxView;
import it.polimi.ingsw.model.modelView.MatrixView;
import it.polimi.ingsw.messages.MessageFromServerType;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.PayloadKeyServer;
import it.polimi.ingsw.server.SendMessages;


public class BoardListener extends EventListener{
    public BoardListener(SendMessages sendMessage) {
        super(sendMessage);
    }
    @Override
    public void onEvent(MessagePayload payload) {
        ClientUI player=sendMessage.getClient(nickname);
        System.out.println(player.getNickname() +" changed Board");
        //newBoard.printMatrix();
        sendMessage.sendAll(payload,MessageFromServerType.DATA);
    }
    //public BoardListener(HashMap<String, Client> playerMap) {
       // super(playerMap);
    //}

/*
    public BoardListener(HashMap<String, Client> playerMap) {
        super(playerMap);
    }
 */



