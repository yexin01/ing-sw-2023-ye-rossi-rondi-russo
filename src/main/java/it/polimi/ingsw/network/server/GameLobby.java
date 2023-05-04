package it.polimi.ingsw.network.server;

import it.polimi.ingsw.messages.*;


import it.polimi.ingsw.model.modelView.ModelView;


import java.util.HashMap;

//TODO cambiare enumerazione dei messaggi dal server una volta terminata la parte di rete
public class GameLobby {
    //TODO Client entity will change once the network part is finished
    private HashMap<String, Connection> playerMap;
    private ModelView modelView;



    public void sendMessage(MessagePayload payload,EventType messageType, String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(messageType,playerNickname);
        MessageFromServer message=new MessageFromServer(header,payload);
        //System.out.println(playerNickname+" RECEIVE");
        //System.out.println(playerNickname+" RECEIVE"+message.getMessagePayload().getEvent());
        //TODO gestire invio messaggio
        // playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }

    //TODO se il nickname é uguale a null significa che é il gioco é cominciato e quindi va inviato a tutti
    public void sendInfo(String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(EventType.ALL_INFO,playerNickname);
        MessagePayload payload=new MessagePayload();
        payload.put(KeyPayload.NEW_BOARD, modelView.getBoardView());
        payload.put(KeyPayload.NEW_BOOKSHELF, modelView.getBookshelfView(playerNickname));
        payload.put(KeyPayload.POINTS,modelView.getPlayerPoints(playerNickname));
        payload.put(KeyPayload.TOKEN,modelView.getCommonGoalViews());
        payload.put(KeyPayload.PERSONAL_GOAL_CARD,modelView.getPlayerPersonal(playerNickname));
        //payload.put(PayloadKeyServer.TOKEN,modelView.getCommonGoalViews());
        //TODO gestire invio messaggio (quando viene inizializzato il gioco viene inviato questo messaggio)
        //sendMessage(payload,MessageFromServerType.DATA,playerNickname);
    }

    //TODO questi verranno cambiati
/*
    public void sendAllMessage(MessagePayload payload, MessageFromServerType messageType) {
        for (Map.Entry<String, ClientView> entry : playerMap.entrySet()) {
            String nickname = entry.getKey();
            ClientView listener = entry.getValue();
                sendMessage(payload, messageType, nickname);

        }
    }

    public void sendMessage(MessagePayload payload, MessageFromServerType messageType, String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(messageType,playerNickname, connection);
        MessageFromServer message=new MessageFromServer(header,payload);
        //System.out.println(playerNickname+" RECEIVE");
        //System.out.println(playerNickname+" RECEIVE"+message.getMessagePayload().getEvent());
        //TODO gestire invio messaggio
       // playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }

 */



    public void removeClient(String nickname) {
        playerMap.remove(nickname);
    }


    public ModelView getModelView() {
        return modelView;
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }

    public void setPlayerMap(HashMap<String, Connection> playerMap) {
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
