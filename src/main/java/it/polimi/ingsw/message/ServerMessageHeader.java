package it.polimi.ingsw.message;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable {

    private final MessageType messageType;
    private final String nicknameAddressee;

    public ServerMessageHeader(MessageType messageType, String nicknameAddressee) {
        this.messageType = messageType;
        this.nicknameAddressee = nicknameAddressee;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getNicknameAddressee() {
        return nicknameAddressee;
    }

}
