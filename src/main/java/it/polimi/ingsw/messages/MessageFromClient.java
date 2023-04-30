package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessageFromClient implements Serializable {
    private final EventType event;
    private final String nicknameSender;
    private final int[] value;

    public MessageFromClient(EventType event,String nicknameSender, int[] value) {
        this.event=event;
        this.nicknameSender = nicknameSender;
        this.value = value;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }

    public int[] getValue() {
        return value;
    }

    public EventType getEvent() {
        return event;
    }
}

