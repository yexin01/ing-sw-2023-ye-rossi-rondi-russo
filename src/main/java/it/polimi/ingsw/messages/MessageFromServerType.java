package it.polimi.ingsw.messages;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public enum MessageFromServerType {
    ERROR,START_TURN,DATA,RECEIVE,END_GAME,INFO,

    CONNECTION_RESPONSE, CONNECTION_REQUEST,
    DISCONNECTION_REQUEST, DISCONNECTION_RESPONSE,
    PING_MESSAGE, PING_RESPONSE,
    ACK_MESSAGE,
    BROADCAST,
    NETWORK_ERROR;

}



