package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.messages.*;

import java.util.HashMap;
import java.util.Map;

public class ServerView extends ModelView {
    //TODO Client entity will change once the network part is finished
    private final HashMap<String, ClientView> playerMap;

    //private final ClientUI player;
    //private final String nickname;
    public ServerView(HashMap<String, ClientView> playerMap, GameRules gameRules,int numPlayers) {
       super(gameRules,numPlayers);
        //this.player = player;
        this.playerMap = playerMap;
        //this.nickname = nickname;
    }
    //se e true lo invia a tutti se e false a tutti eccetto il giocatore con quel nickname
    public void fireEvent(MessagePayload payload, MessageFromServerType messageType, boolean sendToAll, String playerNickname) {
        for (Map.Entry<String, ClientView> entry : playerMap.entrySet()) {
            String nickname = entry.getKey();
            ClientView listener = entry.getValue();
            if (sendToAll || !nickname.equals(playerNickname)) {
                firePlayer(payload, messageType, nickname);
            }
        }
    }

    public void firePlayer(MessagePayload payload, MessageFromServerType messageType, String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(messageType,playerNickname);
        MessageFromServer message=new MessageFromServer(header,payload);
        //System.out.println(playerNickname+" RECEIVE");
        //System.out.println(playerNickname+" RECEIVE"+message.getMessagePayload().getEvent());
        playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }

    public void sendError(ErrorType error,String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.ERROR,playerNickname);
        MessagePayload payload=new MessagePayload(null);
        payload.put(PayloadKeyServer.ERRORMESSAGE,error);
        MessageFromServer message=new MessageFromServer(header,payload);
        playerMap.get(playerNickname).receiveMessageFromServer(playerNickname,message);
    }

    public void sendInfo(String playerNickname){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.INFO,playerNickname);
        MessagePayload payload=new MessagePayload(EventType.ALL_INFO);
        payload.put(PayloadKeyServer.NEWBOARD, getBoardView());
        payload.put(PayloadKeyServer.NEWBOOKSHELF, getBookshelfView(getPlayerByNickname(playerNickname)));
        payload.put(PayloadKeyServer.POINTS,getPlayerPoints(getPlayerByNickname(playerNickname)));
        payload.put(PayloadKeyServer.PERSONAL_GOAL,getPlayerPersonal(getPlayerByNickname(playerNickname)));
        firePlayer(payload,MessageFromServerType.DATA,playerNickname);
    }

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
