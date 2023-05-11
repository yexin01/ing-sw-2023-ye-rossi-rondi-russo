package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessageFromServer2 implements Serializable {

    private ServerMessageHeader2 serverMessageHeader2;
    private MessagePayload2 messagePayload;

    public MessageFromServer2(ServerMessageHeader2 serverMessageHeader2) {
        this.serverMessageHeader2 = serverMessageHeader2;
        this.messagePayload = new MessagePayload2(null);
    }

    public MessageFromServer2(ServerMessageHeader2 serverMessageHeader2, MessagePayload2 messagePayload) {
        this.serverMessageHeader2 = serverMessageHeader2;
        this.messagePayload = messagePayload;
    }

    public ServerMessageHeader2 getServerMessageHeader() {
        return serverMessageHeader2;
    }

    public MessagePayload2 getMessagePayload() {
        return messagePayload;
    }



    @Override
    public String toString() {
        return "Message from server {" +
                "sent to '" + serverMessageHeader2.getNicknameAddressee() + '\'' +
                ", type= " + serverMessageHeader2.getMessageType() +
                ", content= \"" + messagePayload.getContent() +"\""+
                '}';
    }

}
