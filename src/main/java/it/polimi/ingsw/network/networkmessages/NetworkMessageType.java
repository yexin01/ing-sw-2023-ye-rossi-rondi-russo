package it.polimi.ingsw.network.networkmessages;

public enum NetworkMessageType {
    CONNECTION_RESPONSE, CONNECTION_REQUEST,
    DISCONNECTION_REQUEST, DISCONNECTION_RESPONSE,
    PING_MESSAGE, PING_RESPONSE,
    ACK_MESSAGE,
    BROADCAST,
    NETWORK_ERROR;
}