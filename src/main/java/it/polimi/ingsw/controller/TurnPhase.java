package it.polimi.ingsw.controller;

public enum TurnPhase {
    //START_TURN ,
    SELECT_FROM_BOARD,
    SELECT_ORDER_TILES,
    SELECT_COLUMN,
    INSERT_BOOKSHELF_AND_POINTS,
    END_TURN;
    ;



    //TODO DEFINIRE LA CONTAINS
    public static boolean contains(String s) {
        return true;
    }
}
