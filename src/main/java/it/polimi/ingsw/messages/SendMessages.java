package it.polimi.ingsw.messages;

import it.polimi.ingsw.Client;
import it.polimi.ingsw.exceptions.Error;
import it.polimi.ingsw.exceptions.ErrorType;

import java.util.Collection;
import java.util.HashMap;

public class SendMessages {
    private final HashMap<String, Client> playerMap;
    public SendMessages(HashMap<String, Client> playerMap) {
        this.playerMap = playerMap;
    }
    public Client getClient(String nickname){
        Client player = playerMap.get(nickname);
        if (player != null) {
            return player;
        }
        try{
            throw new Error(ErrorType.NOT_PLAYER_FOUND);
        }catch(Exception e){
        }
        return null;
    }
    public void sendAll(Object newValue, MessageFromServerType messageType){
        Collection<Client> clients = playerMap.values();
        for(Client client : clients) {
            sendMessage(client.getNickname(),newValue,messageType);
        }
    }
    public void sendAllExcept(String nickname,Object newValue, MessageFromServerType messageType){
        Collection<Client> clients = playerMap.values();
        for(Client client : clients) {
            if(!(client.getNickname().equals(nickname))){
                sendMessage(client.getNickname(),newValue,messageType);
            }
        }
    }

    public void sendMessage(String nickname, Object newValue, MessageFromServerType messageType){
        ServerMessageHeader header=new ServerMessageHeader(messageType,nickname);
        MessagePayload object=new MessagePayload(newValue);
        MessageFromServer message=new MessageFromServer(header,object);
        getClient(nickname).receiveMessageFromClient(nickname,newValue,messageType);
    }
}
