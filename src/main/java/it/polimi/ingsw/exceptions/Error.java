package it.polimi.ingsw.exceptions;

public class Error extends Exception{

    public Error(ErrorType e){
        //System.err.println(e.getErrorMessage());
    }

    public String getMessage(ErrorType error){
        return error.getErrorMessage();
    }

}