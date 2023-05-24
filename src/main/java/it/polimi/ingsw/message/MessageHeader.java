package it.polimi.ingsw.message;

import java.io.Serial;
import java.io.Serializable;

/**
 * Class that represents the header of a message sent between client and server
 */
public class MessageHeader implements Serializable {

    @Serial
    private static final long serialVersionUID = -1041059148969477852L;

    private final MessageType messageType;
    private final String nickname;

    /**
     * Constructor of the class MessageHeader that creates a new message header
     * @param messageType the type of the message
     * @param nickname the nickname of the player that sent the message
     */
    public MessageHeader(MessageType messageType, String nickname) {
        this.messageType = messageType;
        this.nickname = nickname;
    }

    /**
     * @return the type of the message
     */
    public MessageType getMessageType() {
        return messageType;
    }

    /**
     * @return the nickname of the player that sent the message
     */
    public String getNickname() {
        return nickname;
    }

}


