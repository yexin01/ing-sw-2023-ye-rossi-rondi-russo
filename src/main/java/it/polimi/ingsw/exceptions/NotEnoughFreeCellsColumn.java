package it.polimi.ingsw.exceptions;

public class NotEnoughFreeCellsColumn extends Exception {

    public NotEnoughFreeCellsColumn(){
        System.err.println("You don't have enough cells free in this column");
    }

}
