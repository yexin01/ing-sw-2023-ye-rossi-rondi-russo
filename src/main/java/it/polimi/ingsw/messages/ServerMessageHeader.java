package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable{
    private final MessageFromServerType messageFromServer;
    private final String nicknameSender;

    public ServerMessageHeader(MessageFromServerType messageFromServer, String nicknameSender) {
        this.messageFromServer = messageFromServer;
        this.nicknameSender = nicknameSender;
    }

    public MessageFromServerType getMessageFromServer() {
        return messageFromServer;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }
}

