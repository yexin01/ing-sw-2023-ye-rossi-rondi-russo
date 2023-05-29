package it.polimi.ingsw.message;

/**
 * Enum that represents the key of the payload for information about the connection
 */
public enum KeyConnectionPayload implements KeyAbstractPayload {
    CONNECTION_CREATION,

    RECONNECTION,

    BROADCAST;
}
