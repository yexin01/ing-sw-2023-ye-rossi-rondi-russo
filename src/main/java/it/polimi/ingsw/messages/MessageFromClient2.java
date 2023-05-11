package it.polimi.ingsw.messages;

import java.io.Serializable;

public class MessageFromClient2 implements Serializable {
    private ClientMessageHeader2 header;
    private MessagePayload2 payload;
//TODO questo poi andrà tolto, nel payload metteremo tutt le info in modo da non avere cosi tanti eventType
    public MessageFromClient2(ClientMessageHeader2 header) {
        this.header = header;
        this.payload = new MessagePayload2(null);
    }

    public MessageFromClient2(ClientMessageHeader2 header, MessagePayload2 payload) {
        this.header = header;
        this.payload = payload;
    }

    public ClientMessageHeader2 getHeader() {
        return header;
    }

    public MessagePayload2 getPayload() {
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