package it.polimi.ingsw.server;

import it.polimi.ingsw.client.ClientUI;
import it.polimi.ingsw.messages.*;

import java.util.Collection;
import java.util.HashMap;

public class SendMessages {
    //TODO Client entity will change once the network part is finished
    //private final HashMap<String, ClientUI> playerMap;
    private final ClientUI player;
    private final String nickname;
    public SendMessages(HashMap<String, ClientUI> playerMap, ClientUI player, String nickname) {
        this.player = player;
        //this.playerMap = playerMap;
        this.nickname = nickname;
    }

    public void sendMessage(MessagePayload payload, MessageFromServerType messageType){
        ServerMessageHeader header=new ServerMessageHeader(messageType,nickname);
        MessageFromServer message=new MessageFromServer(header,payload);
        player.receiveMessageFromServer(nickname,message);
    }
    public void sendError(ErrorType error){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.ERROR,nickname);
        MessagePayload payload=new MessagePayload(null);
        payload.put(PayloadKeyServer.ERRORMESSAGE,error);
        MessageFromServer message=new MessageFromServer(header,payload);
        player.receiveMessageFromServer(nickname,message);
    }

    public String getNickname() {
        return nickname;
    }



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
