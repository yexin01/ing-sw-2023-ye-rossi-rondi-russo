package it.polimi.ingsw.message;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * Class that represents a message sent between client and server
 */
public class Message implements Serializable {

    @Serial
    private static final long serialVersionUID = -5158808756179690476L;

    private MessageHeader messageHeader;
    private MessagePayload messagePayload;

    /**
     * Constructor of the class Message that creates a new message without a payload
     * @param messageHeader the header of the message
     */
    public Message(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
        this.messagePayload = null;
    }

    /**
     * Constructor of the class Message that creates a new message with a payload
     * @param messageHeader the header of the message
     * @param messagePayload the payload of the message
     */
    public Message(MessageHeader messageHeader, MessagePayload messagePayload) {
        this.messageHeader = messageHeader;
        this.messagePayload = messagePayload;
    }

    /**
     * @return the header of the message
     */
    public MessageHeader getHeader() {
        return messageHeader;
    }

    /**
     * @return the payload of the message
     */
    public MessagePayload getPayload() {
        return messagePayload;
    }

    /**
     * Method that returns the string representation of the message
     * @return the string representation of the message
     */
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

