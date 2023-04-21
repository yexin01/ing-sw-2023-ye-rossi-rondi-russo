package it.polimi.ingsw.messages;

import java.io.Serializable;



public class MessageFromClient implements Serializable {

    private final DataClientType clientMessage;
    private final String NicknameSender;
    private final int[] value;

    public MessageFromClient(DataClientType clientMessage, String nicknameSender, int[] value) {
        this.clientMessage = clientMessage;
        NicknameSender = nicknameSender;
        this.value = value;
    }

    public DataClientType getClientMessage() {
        return clientMessage;
    }

    public String getNicknameSender() {
        return NicknameSender;
    }

    public int[] getValue() {
        return value;
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