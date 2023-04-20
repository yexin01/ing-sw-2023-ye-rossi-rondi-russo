package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ClientMessageHeader implements Serializable {
    private final MessageFromClientType messageFromClient;
    private final String nicknameSender;

    public ClientMessageHeader(MessageFromClientType messageFromClient, String nicknameSender) {
        this.messageFromClient = messageFromClient;
        this.nicknameSender = nicknameSender;
    }
    public MessageFromClientType getClientMessage() {
        return messageFromClient;
    }
    public String getNicknameSender() {
        return nicknameSender;
    }

}
