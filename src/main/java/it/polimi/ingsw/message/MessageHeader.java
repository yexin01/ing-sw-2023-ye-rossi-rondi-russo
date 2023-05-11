package it.polimi.ingsw.message;

import java.io.Serializable;

public class MessageHeader implements Serializable {

    private final MessageType messageType; //ERROR
    private final String nickname;

    public MessageHeader(MessageType messageType, String nickname) {
        this.messageType = messageType;
        this.nickname = nickname;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNickname() {
        return nickname;
    }


}


