package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ClientMessageHeader implements Serializable {

    private final EventType messageType;
    private final String nicknameSender;

    public ClientMessageHeader(EventType messageType, String nicknameSender) {
        this.messageType = messageType;
        this.nicknameSender = nicknameSender;
    }

    public EventType getMessageType() {
        return messageType;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }

}
