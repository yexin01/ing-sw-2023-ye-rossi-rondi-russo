package it.polimi.ingsw.controller;

public enum TurnPhase {
    //START_TURN ,
    SELECT_FROM_BOARD,
    FINISH_SELECTION_BOARD,
    SELECT_ORDER_TILES,
    SELECT_COLUMN,
    INSERT_BOOKSHELF_AND_POINTS;



    //TODO DEFINIRE LA CONTAINS
    public static boolean contains(String s) {
        return true;
    }
}
