package it.polimi.ingsw.messages;

import it.polimi.ingsw.Client;

import java.util.Collection;
import java.util.HashMap;

public class SendMessages {
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
            //System.out.println("YOU ARE "+client.getNickname()+" token WIN by "+payload.get(PayloadKeyServer.WHO_CHANGE)+""+payload.get(PayloadKeyServer.POINTS));
            System.out.println("YOU ARE "+client.getNickname());
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
        getClient(nickname).receiveMessageFromClient(nickname,message);
    }

    public void sendError(String nickname, ErrorType error){
        ServerMessageHeader header=new ServerMessageHeader(MessageFromServerType.ERROR,nickname);
        MessagePayload payload=new MessagePayload(null);
        payload.put(PayloadKeyServer.ERRORMESSAGE,error);
        MessageFromServer message=new MessageFromServer(header,payload);
        getClient(nickname).receiveMessageFromClient(nickname,message);
    }

}
