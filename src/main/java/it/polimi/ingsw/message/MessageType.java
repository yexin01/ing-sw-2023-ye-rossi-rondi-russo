package it.polimi.ingsw.message;

public enum MessageType {
    ERROR,
    //se è network metto KeyErrorPayload
    //se è data

    CONNECTION,
    LOBBY,

    DATA //tutte le azioni del turno
}
