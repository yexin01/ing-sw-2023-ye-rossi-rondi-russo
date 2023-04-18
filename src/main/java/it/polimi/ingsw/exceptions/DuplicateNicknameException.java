package it.polimi.ingsw.exceptions;

public class DuplicateNicknameException extends Exception {

    public DuplicateNicknameException(){
        System.err.println("This nickname is already taken, chose another one please.");
    }

}
