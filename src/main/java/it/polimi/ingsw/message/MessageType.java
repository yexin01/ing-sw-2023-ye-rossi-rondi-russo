package it.polimi.ingsw.message;

/**
 * Enum that represents the type of the message sent between client and server
 */
public enum MessageType {
    ERROR,
    PING,
    CONNECTION,
    LOBBY,
    DATA
}
