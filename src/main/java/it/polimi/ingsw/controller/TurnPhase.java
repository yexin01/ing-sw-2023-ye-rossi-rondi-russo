package it.polimi.ingsw.controller;

import it.polimi.ingsw.message.KeyAbstractPayload;

public enum TurnPhase implements KeyAbstractPayload {

    ALL_INFO,
    SELECT_FROM_BOARD,
    SELECT_ORDER_TILES,
    SELECT_COLUMN,
    END_TURN,
    END_GAME;
}
