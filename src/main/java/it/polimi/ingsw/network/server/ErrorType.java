package it.polimi.ingsw.network.server;

//TODO molti non verranno piu ulizzati
public enum ErrorType {
    DUPLICATE_NAME("This nickname is already taken"),
    WRONG_PHASE("You can't do this action now."),
    ILLEGAL_TURN("it isn't your turn"),
    ILLEGAL_PHASE("this isn't the phase"),
    INVALID_COLUMN("Invalid Column"),
    NOT_ENOUGH_FREE_CELLS_COLUMN("You don't have enough cells free in this column"),
    NOT_SELECTABLE_TILE("Not selectable tile"),
    TOO_MANY_TILES("You selected too many tile"),
    INVALID_ORDER_TILE("Invalid order tile "),
    NOT_PLAYER_FOUND("Not player with this nickname found "),
    NOT_TILES_SELECTED("You haven't selected any tile "),

    INVALID_COORDINATES("COORDINATES of the tile are invalid"),
    TOO_MANY_PLAYERS("Max number of player reached!"),
    GAME_STARTED("Game already started! Cannot join!"),
    NOT_RECEIVED_TILES("Problem with receiving tiles"),
    DISCONNECTION("Disconnection from the server");

    private final String errorMessage;


    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

   /* public String getSecondErrorMessage(ErrorType e) {
        return e.errorMessage;
    }

    */
}