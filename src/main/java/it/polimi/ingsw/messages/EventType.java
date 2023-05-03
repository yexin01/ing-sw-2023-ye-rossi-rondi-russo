package it.polimi.ingsw.messages;

import java.io.Serializable;


public enum EventType {
    SETUP,ALL_INFO,NEW_GAME,
    START_TURN,BOARD_SELECTION,ORDER_TILES,COLUMN,END_TURN,END_GAME,
    ABANDON_GAME,ERROR,ACK,PING,DISCONNECT,CONNECT;

}


