package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable {
    private final MessageFromServerType messageFromServer;
    private final String nicknameAddressee;

    public ServerMessageHeader(MessageFromServerType messageFromServer, String nicknameAddressee) {
        this.messageFromServer = messageFromServer;
        this.nicknameAddressee = nicknameAddressee;
    }

    public MessageFromServerType getMessageFromServer() {
        return messageFromServer;
    }

    public String getNicknameAddressee() {
        return nicknameAddressee;
    }
}

