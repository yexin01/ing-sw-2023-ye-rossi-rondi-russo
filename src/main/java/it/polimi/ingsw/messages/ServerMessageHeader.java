package it.polimi.ingsw.messages;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable {
    private final EventType messageFromServer;

    private final String nicknameAddressee;

    public ServerMessageHeader(EventType messageFromServer, String nicknameAddressee) {
        this.messageFromServer = messageFromServer;
        this.nicknameAddressee = nicknameAddressee;

    }

    public EventType getMessageFromServer() {
        return messageFromServer;
    }

    /*public Connection getConnection() {
        return connection;
    }

     */


    public String getNicknameAddressee() {
        return nicknameAddressee;
    }


}