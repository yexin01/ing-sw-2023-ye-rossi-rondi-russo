package it.polimi.ingsw.message;

/**
 * Enum that represents the type of the message sent between client and server
 */
public enum MessageType {
    ERROR,
    PING,
    //ACK,
    CONNECTION,
    LOBBY,
    DATA //all the actions that the player can do in the game
}
