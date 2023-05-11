package it.polimi.ingsw.message;

import java.io.Serializable;

public class ClientMessageHeader implements Serializable {

    private final MessageType messageType;
    private final String nicknameSender;

    public ClientMessageHeader(MessageType messageType, String nicknameSender) {
        this.messageType = messageType;
        this.nicknameSender = nicknameSender;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }

}