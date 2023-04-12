package it.polimi.ingsw.messages;

public class ClientMessageHeader {
    private final String messageName;
    private final String nicknameSender;
    private final ClientMessageType messageType;

    public ClientMessageHeader(String messageName, String nicknameSender, ClientMessageType messageType) {
        this.messageName = messageName;
        this.nicknameSender = nicknameSender;
        this.messageType = messageType;
    }

    public String getMessageName() {
        return messageName;
    }

    public String getNicknameSender() {
        return nicknameSender;
    }

    public ClientMessageType getMessageType() {
        return messageType;
    }
}
