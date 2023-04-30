package it.polimi.ingsw.messages;

import it.polimi.ingsw.network.server.Connection;

import java.io.Serializable;

public class ServerMessageHeader implements Serializable {
    private final MessageFromServerType messageFromServer;
    private final Connection connection;
    //private final String nicknameAddressee;

    public ServerMessageHeader(MessageFromServerType messageFromServer, Connection connection) {
        this.messageFromServer = messageFromServer;
        //this.nicknameAddressee = nicknameAddressee;
        this.connection =connection;
    }

    public MessageFromServerType getMessageFromServer() {
        return messageFromServer;
    }

    public Connection getConnection() {
        return connection;
    }
    /*

    public String getNicknameAddressee() {
        return nicknameAddressee;
    }

     */
}

