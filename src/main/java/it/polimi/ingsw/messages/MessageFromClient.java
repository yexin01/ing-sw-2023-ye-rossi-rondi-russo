package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessageFromClient implements Serializable {
    private final EventType event;
    private final DataClientType clientMessage;
    private final String nicknameSender;
    private final int[] value;

    public MessageFromClient(EventType event,DataClientType clientMessage, String nicknameSender, int[] value) {
        this.clientMessage = clientMessage;
        this.event=event;
        this.nicknameSender = nicknameSender;
        this.value = value;
    }

    public DataClientType getClientMessage() {
        return clientMessage;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }

    public int[] getValue() {
        return value;
    }
}

