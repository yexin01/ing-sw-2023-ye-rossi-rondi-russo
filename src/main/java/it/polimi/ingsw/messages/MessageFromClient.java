package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessageFromClient implements Serializable {
    private ClientMessageHeader header;
    private MessagePayload payload;

    public MessageFromClient(ClientMessageHeader header) {
        this.header = header;
        this.payload = new MessagePayload(null);
    }

    public MessageFromClient(ClientMessageHeader header, MessagePayload payload) {
        this.header = header;
        this.payload = payload;
    }

    public ClientMessageHeader getHeader() {
        return header;
    }

    public MessagePayload getPayload() {
        return payload;
    }

    @Override
    public String toString() {
        return "Message from Client {" +
                "sender '" + header.getNicknameSender() + '\'' +
                ", type= " + header.getMessageType() +
                ", content= \"" + payload.getContent() +"\""+
                '}';
    }

}