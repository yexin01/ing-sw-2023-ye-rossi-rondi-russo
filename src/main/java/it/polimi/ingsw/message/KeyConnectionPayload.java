package it.polimi.ingsw.message;

public enum KeyConnectionPayload implements KeyAbstractPayload {
    CONNECTION_CREATION,

    RECONNECTION_DURING_GAME,



    DISCONNECTION_FORCED,//chiesto dal server

    BROADCAST //solo da stampare
}
