package it.polimi.ingsw.message;

/**
 * Enum that represents the key of the payload for information about the data
 */
public enum KeyDataPayload implements KeyAbstractPayload {
    PHASE,CLIENT_CONNECTED, ORDER_PHASE,COLUMN,START_TURN,VALUE_CLIENT,START_GAME,END_GAME,END_TURN;/*RANKING*/
}
