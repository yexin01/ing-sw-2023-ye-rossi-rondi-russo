package it.polimi.ingsw.network.messages;

import java.io.Serializable;

public abstract class Message implements Serializable {

    private static final long serialVersionUID = -5294639376713450875L;

    private final String senderUsername;
    private final MessageContent content; //enum

    public Message(String senderUsername, MessageContent content) {
        this.senderUsername = senderUsername;
        this.content = content;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public MessageContent getContent() {
        return content;
    }



}
