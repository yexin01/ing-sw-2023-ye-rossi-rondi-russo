package it.polimi.ingsw.server;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.messages.*;

import java.util.Collection;
import java.util.HashMap;

public class SendMessages {
    //TODO Client entity will change once the network part is finished
    private final HashMap<String, Client> playerMap;
    public SendMessages(HashMap<String, Client> playerMap) {
        this.playerMap = playerMap;
    }

    public Client getClient(String nickname) {
        try{
            if (nickname == null) {
                throw new NullPointerException("Nickname cannot be null");
            }
            Client player = playerMap.get(nickname);
            if (player == null) {
                throw new IllegalArgumentException("No client found for nickname: " + nickname);
            }
            return player;
        }catch(Exception e){

        }
        return null;
    }
    public void sendAll(MessagePayload payload, MessageFromServerType messageType){
        Collection<Client> clients = playerMap.values();
        for(Client client : clients) {
            sendMessage(client.getNickname(),payload,messageType);
        }
    }
    public void sendAllExcept(String nickname,MessagePayload payload, MessageFromServerType messageType){
        Collection<Client> clients = playerMap.values();
        for(Client client : clients) {
            if(!(client.getNickname().equals(nickname))){
                sendMessage(client.getNickname(),payload,messageType);
            }
        }
    }

    public void sendMessage(String nickname, MessagePayload payload, MessageFromServerType messageType){
        ServerMessageHeader header=new ServerMessageHeader(messageType,nickname);
        MessageFromServer message=new MessageFromServer(header,payload);
        getClient(nickname).receiveMessageFromServer(nickname,message);
        //TODO serialize message
    }

    public void sendError(String nickname, ErrorType error){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.ERROR,nickname);
        MessagePayload payload=new MessagePayload(null);
        payload.put(PayloadKeyServer.ERRORMESSAGE,error);
        MessageFromServer message=new MessageFromServer(header,payload);
        getClient(nickname).receiveMessageFromServer(nickname,message);
        //TODO serialize message
    }

}
