package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ClientMessageHeader2 implements Serializable {

    private final EventType messageType;
    private final String nicknameSender;

    public ClientMessageHeader2(EventType messageType, String nicknameSender) {
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
