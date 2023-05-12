package it.polimi.ingsw.message;

import java.io.Serializable;
import java.util.Map;

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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Message [Header: ")
                .append(messageHeader.getMessageType())
                .append(", Nickname='")
                .append(messageHeader.getNickname())
                .append("', Payload: Key='")
                .append(messagePayload.getKey())
                .append("', Data=");
        for (Map.Entry<Data, Object> entry : messagePayload.getData().entrySet()) {
            sb.append("{")
                    .append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("}");
        }
        sb.append("]");
        return sb.toString();
    }

}

