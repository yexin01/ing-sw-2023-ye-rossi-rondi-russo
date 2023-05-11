package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable {

    private final EventType messageType;
    private final String nicknameAddressee;

    public ServerMessageHeader(EventType messageType, String nicknameAddressee) {
        this.messageType = messageType;
        this.nicknameAddressee = nicknameAddressee;
    }

    public EventType getMessageType() {
        return messageType;
    }

    public String getNicknameAddressee() {
        return nicknameAddressee;
    }

}