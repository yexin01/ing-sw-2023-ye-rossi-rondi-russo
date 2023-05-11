package it.polimi.ingsw.message;

import java.io.Serializable;

public class MessageFromServer implements Serializable {

    private ServerMessageHeader serverMessageHeader;
    private MessagePayload messagePayload;

    public MessageFromServer(ServerMessageHeader serverMessageHeader) {
        this.serverMessageHeader = serverMessageHeader;
        this.messagePayload = null;
    }

    public MessageFromServer(ServerMessageHeader serverMessageHeader, MessagePayload messagePayload) {
        this.serverMessageHeader = serverMessageHeader;
        this.messagePayload = messagePayload;
    }

    public ServerMessageHeader getServerMessageHeader() {
        return serverMessageHeader;
    }

    public MessagePayload getMessagePayload() {
        return messagePayload;
    }



    public String toString(KeyAbstractPayload key) {
        return "Message from server {" +
                "sent to '" + serverMessageHeader.getNicknameAddressee() + '\'' +
                ", type= " + serverMessageHeader.getMessageType() +
                ", content= \"" + messagePayload.getContent(key) +"\""+
                '}';
    }

}

