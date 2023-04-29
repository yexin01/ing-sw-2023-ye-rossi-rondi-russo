package it.polimi.ingsw.network.messages;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable {
    private final MessageFromServerType messageFromServer;
    private final EventType nicknameAddressee;

    public ServerMessageHeader(MessageFromServerType messageFromServer, EventType nicknameAddressee) {
        this.messageFromServer = messageFromServer;
        this.nicknameAddressee = nicknameAddressee;
    }

    public MessageFromServerType getMessageFromServerType() {
        return messageFromServer;
    }

    public EventType getNicknameAddressee() {
        return nicknameAddressee;
    }
}
