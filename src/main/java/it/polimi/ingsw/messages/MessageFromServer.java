package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessageFromServer implements Serializable {

    private ServerMessageHeader header;
    private MessagePayload payload;

    public MessageFromServer(ServerMessageHeader header) {
        this.header = header;
        this.payload = new MessagePayload(null);
    }

    public MessageFromServer(ServerMessageHeader header, MessagePayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public ServerMessageHeader getHeader() {
        return header;
    }

    public MessagePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Message from server {" +
                "sent to '" + header.getNicknameAddressee() + '\'' +
                ", type= " + header.getMessageType() +
                ", content= \"" + payload.getContent() +"\""+
                '}';
    }

}
