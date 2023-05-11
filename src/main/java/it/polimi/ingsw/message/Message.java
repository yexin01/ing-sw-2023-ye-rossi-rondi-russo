package it.polimi.ingsw.message;

import java.io.Serializable;

public class Message implements Serializable {

    private MessageHeader messageHeader;
    private MessagePayload messagePayload;

    public Message(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
        this.messagePayload = null;
    }

    public Message(MessageHeader messageHeader, MessagePayload messagePayload) {
        this.messageHeader = messageHeader;
        this.messagePayload = messagePayload;
    }

    public MessageHeader getHeader() {
        return messageHeader;
    }

    public MessagePayload getPayload() {
        return messagePayload;
    }



    public String toString(KeyAbstractPayload key) {
        return "Message from server {" +
                "sent to '" + messageHeader.getNickname() + '\'' +
                ", type= " + messageHeader.getMessageType() +
                ", content= \"" + messagePayload.getContent(key) +"\""+
                '}';
    }

}

