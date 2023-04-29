package it.polimi.ingsw.network.messages;

public class ClientMessageHeader {
    private final EventType messageName;
    private final DataClientType messageType;
    private final EventType nicknameSender;


    public ClientMessageHeader(EventType messageName, DataClientType messageType, EventType nicknameSender) {
        this.messageName = messageName;
        this.messageType = messageType;
        this.nicknameSender = nicknameSender;

    }


    public EventType getMessageName() {
        return messageName;
    }


    public EventType getNicknameSender() {
        return nicknameSender;
    }


    public DataClientType getMessageType() {
        return messageType;
    }
}
