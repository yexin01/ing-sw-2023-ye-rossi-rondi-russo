package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.*;
import it.polimi.ingsw.view.ClientView;


import it.polimi.ingsw.model.modelView.ModelView;


import java.util.HashMap;
import java.util.Map;
//TODO cambiare enumerazione dei messaggi dal server una volta terminata la parte di rete
public class ServerView  {
    //TODO Client entity will change once the network part is finished
    private HashMap<String, ClientView> playerMap;
    private ModelView modelView;
    //private final ClientUI player;
    //private final String nickname;
    public ServerView() {
        //this.player = player;
        //this.playerMap = playerMap;
        //this.nickname = nickname;
        //this.playerMap = playerMap;
    }

    public void sendAllMessage(MessagePayload payload, MessageFromServerType messageType) {
        for (Map.Entry<String, ClientView> entry : playerMap.entrySet()) {
            String nickname = entry.getKey();
            ClientView listener = entry.getValue();
                sendMessage(payload, messageType, nickname);

        }
    }

    public void sendMessage(MessagePayload payload, MessageFromServerType messageType, String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(messageType,playerNickname);
        MessageFromServer message=new MessageFromServer(header,payload);
        //System.out.println(playerNickname+" RECEIVE");
        //System.out.println(playerNickname+" RECEIVE"+message.getMessagePayload().getEvent());
        //TODO gestire invio messaggio
       // playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }

    public void sendError(ErrorType error, java.lang.String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.ERROR,playerNickname);
        MessagePayload payload=new MessagePayload(null);
        payload.put(PayloadKeyServer.ERRORMESSAGE,error);
        MessageFromServer message=new MessageFromServer(header,payload);
        //TODO gestire invio messaggio
       // playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }

    public void sendInfo(String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.INFO,playerNickname);
        MessagePayload payload=new MessagePayload(EventType.ALL_INFO);
        payload.put(PayloadKeyServer.NEWBOARD, modelView.getBoardView());
        payload.put(PayloadKeyServer.NEWBOOKSHELF, modelView.getBookshelfView(playerNickname));
        payload.put(PayloadKeyServer.POINTS,modelView.getPlayerPoints(playerNickname));
        payload.put(PayloadKeyServer.TOKEN,modelView.getCommonGoalViews());
        payload.put(PayloadKeyServer.PERSONAL_GOAL,modelView.getPlayerPersonal(playerNickname));
        payload.put(PayloadKeyServer.TOKEN,modelView.getCommonGoalViews());
        //TODO gestire invio messaggio (quando viene inizializzato il gioco viene inviato questo messaggio)
        //sendMessage(payload,MessageFromServerType.DATA,playerNickname);
    }

    public void removeClient(String nickname) {
        playerMap.remove(nickname);
    }


    public ModelView getModelView() {
        return modelView;
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    public void setPlayerMap(HashMap<String, ClientView> playerMap) {
        this.playerMap = playerMap;
    }


    /*
   //se e true lo invia a tutti se e false a tutti eccetto il giocatore con quel nickname
    public void sendAllMessage(MessagePayload payload, MessageFromServerType messageType, boolean sendToAll, String playerNickname) {
        for (Map.Entry<String, ClientView> entry : playerMap.entrySet()) {
            String nickname = entry.getKey();
            ClientView listener = entry.getValue();
            if (sendToAll || !nickname.equals(playerNickname)) {
                sendMessage(payload, messageType, nickname);
            }
        }
    }

    public void sendMessage(MessagePayload payload, MessageFromServerType messageType, String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(messageType,playerNickname);
        MessageFromServer message=new MessageFromServer(header,payload);
        //System.out.println(playerNickname+" RECEIVE");
        //System.out.println(playerNickname+" RECEIVE"+message.getMessagePayload().getEvent());
        playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }


     */

    /*


 */






/*
    public ClientUI getClient(String nickname) {
        try{
            if (nickname == null) {
                throw new NullPointerException("Nickname cannot be null");
            }
            ClientUI player = playerMap.get(nickname);
            if (player == null) {
                throw new IllegalArgumentException("No client found for nickname: " + nickname);
            }
            return player;
        }catch(Exception e){

        }
        return null;
    }

 */


 /*
    public void sendAll(MessagePayload payload, MessageFromServerType messageType){
        Collection<ClientUI> clients = playerMap.values();
        for(ClientUI client : clients) {
            sendMessage(client.getNickname(),payload,messageType);
        }
    }
    public void sendAllExcept(String nickname,MessagePayload payload, MessageFromServerType messageType){
        Collection<ClientUI> clients = playerMap.values();
        for(ClientUI client : clients) {
            if(!(client.getNickname().equals(nickname))){
                sendMessage(client.getNickname(),payload,messageType);
            }
        }
    }

  */

}
