package it.polimi.ingsw.messages;

import java.io.Serializable;


public enum EventType {
    SETUP,ALL_INFO,
    START_TURN,BOARD_SELECTION,ORDER_TILES,COLUMN,END_TURN,
    ABANDON_GAME,ERROR,ACK,PING,DISCONNECT;

}


