package it.polimi.ingsw.exceptions;

public class NotEnoughFreeCellsColumn extends Exception {


    @Override
    public String getMessage() {
        return "You don't have enough cells free in this column ";
    }
}
