package it.polimi.ingsw.exceptions;

public enum ErrorType {
    DUPLICATE_NAME("This nickname is already taken"),
    WRONG_PHASE("You can't do this action now."),
    ILLEGAL_TURN("it isn't your turn"),
    ILLEGAL_PHASE("this isn't the phase"),
    INVALID_COLUMN("Invalid Column"),
    NOT_ENOUGH_FREE_CELLS_COLUMN("You don't have enough cells free in this column"),
    NOT_SELECTABLE_TILE("Not selectable tile"),
    TOO_MANY_TILES("Invalid Tile"),
    INVALID_ORDER_TILE("Invalid order tile "),

    INVALID_COORDINATES("COORDINATES of the tile are invalid");

    private final String errorMessage;


    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }


}
