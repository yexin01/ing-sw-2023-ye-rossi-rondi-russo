package it.polimi.ingsw.exceptions;

public class WrongTurnActionRequestedException extends Exception{

    public WrongTurnActionRequestedException(){
        System.err.println("You can't do this action now.");
    }

}
