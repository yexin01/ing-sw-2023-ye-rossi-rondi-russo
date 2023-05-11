package it.polimi.ingsw.message;

public enum KeyNetworkPayload implements KeyAbstractPayload {
    CONNECTION_REQUEST,CONNECTION_RESPONSE,DISCONNECT_REQUEST,DISCONNECT_RESPONSE,
    RECONNECT_REQUEST,RECONNECT_RESPONSE, ACK,
    PING,
}
