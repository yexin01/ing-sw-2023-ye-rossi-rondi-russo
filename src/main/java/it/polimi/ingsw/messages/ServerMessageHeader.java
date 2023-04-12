package it.polimi.ingsw.messages;

public class ServerMessageHeader {
    private final String messageName;
    private final ServerMessageType messageType;

    public ServerMessageHeader(String messageName, ServerMessageType messageType) {
        this.messageName = messageName;
        this.messageType = messageType;
    }

    public String getMessageName(){
        return messageName;
    }

    public ServerMessageType getMessageType() {
        return messageType;
    }
}
