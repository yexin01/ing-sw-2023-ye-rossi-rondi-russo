package it.polimi.ingsw.network.client.handlers;

import it.polimi.ingsw.messages.DataClientType;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.messages.MessageFromClient;
import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.network.client.ClientInterface;
import it.polimi.ingsw.network.client.ClientSocket;

public class HandlerToServer {
    private final ClientSocket connection;
    private final ClientInterface clientInterface;


    protected HandlerToServer(ClientSocket connection, ClientInterface clientInterface) {
        this.connection = connection;
        this.clientInterface = clientInterface;
    }
    public void handleMessageToServer(EventType event, DataClientType type, String playerNickname, int[] value) {
        MessageFromClient message=new MessageFromClient(event,type,playerNickname,value);
        connection.sendMessage(message);
    }

}
