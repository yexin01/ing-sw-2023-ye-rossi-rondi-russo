package it.polimi.ingsw.network.server;


public enum ErrorType {
    DUPLICATE_NAME("This nickname is already taken"),
    WRONG_PHASE("You can't do this action now."),
    ILLEGAL_TURN("it isn't your turn"),
    ILLEGAL_PHASE("This isn't the phase"),
    INVALID_COLUMN("Invalid Column"),
    INVALID_INPUT("Invalid Input"),
    NOT_ENOUGH_FREE_CELLS_COLUMN("You don't have enough cells free in this column"),
    NOT_SELECTABLE_TILE("Not selectable tile"),
    TOO_MANY_TILES("You selected too many tile"),
    INVALID_ORDER_TILE_REPETITION("You cannot enter the same number twice "),
    INVALID_ORDER_TILE_NUMBER("You have entered a number outside the permitted range "),
    NOT_PLAYER_FOUND("Not player with this nickname found "),
    NOT_VALUE_SELECTED("You have not selected anything"),

    INVALID_COORDINATES("COORDINATES of the tile are invalid"),
    TOO_MANY_PLAYERS("Max number of player reached!"),
    GAME_STARTED("Game already started! Cannot join!"),
    NOT_RECEIVED_TILES("Problem with receiving tiles"),
    DISCONNECTION("Disconnection from the server"),

    NOT_SAME_ROW_OR_COLUMN("Tiles are not on the same row or column, tiles must be all adjacent"),

    NOT_ENOUGH_FREE_EDGES("The selected tile has no free edges");

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
