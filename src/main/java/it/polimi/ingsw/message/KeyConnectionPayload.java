package it.polimi.ingsw.message;

/**
 * Enum that represents the key of the payload for information about the connection
 */
public enum KeyConnectionPayload implements KeyAbstractPayload {
    CONNECTION_CREATION,
    RECONNECTION_DURING_GAME,
    DISCONNECTION_FORCED, // from the server
    BROADCAST // only to print to the client
}
