package it.polimi.ingsw.network.messages;

import java.io.Serializable;



public class MessageFromClient implements Serializable {

    private final EventType messageType;
    private final DataClientType clientMessage;
    private final EventType NicknameSender;
    private final int[] value;

    public MessageFromClient(EventType messageType, DataClientType clientMessage, EventType nicknameSender, int[] value) {
        this.messageType = messageType;
        this.clientMessage = clientMessage;
        NicknameSender = nicknameSender;
        this.value = value;
    }

    public DataClientType getClientMessage() {
        return clientMessage;
    }

    public EventType getNicknameSender() {
        return NicknameSender;
    }

    public int[] getValue() {
        return value;
    }


    public EventType getMessageType() {
        return messageType;
    }
}

/*
public class MessageFromClient implements Serializable {

    private final MessageFromClientType clientMessage;
    private final String NicknameSender;
    private final int[] value;


    public MessageFromClient(MessageFromClientType clientMessage, String nicknameSender, int[] value) {
        this.clientMessage = clientMessage;
        NicknameSender = nicknameSender;
        this.value = value;
    }

    public MessageFromClientType getClientMessage() {
        return clientMessage;
    }

    public String getNicknameSender() {
        return NicknameSender;
    }

    public int[] getValue() {
        return value;
    }
}

 */

/*

public class MessageFromClient implements Serializable {
    private final ClientMessageHeader clientMessageHeader;
    private final MessagePayload messagePayload;


    public MessageFromClient(ClientMessageHeader clientMessageHeader, MessagePayload messagePayload) {
        this.clientMessageHeader = clientMessageHeader;
        this.messagePayload = messagePayload;
    }

    public ClientMessageHeader getClientMessageHeader() {
        return clientMessageHeader;
    }

    public MessagePayload getMessagePayload() {
        return messagePayload;
    }
}

 */