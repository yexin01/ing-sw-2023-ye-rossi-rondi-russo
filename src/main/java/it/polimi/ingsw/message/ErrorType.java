package it.polimi.ingsw.message;

/**
 * Enum that represents the type of the error message sent between client and server and the String to print when the error occurs
 */
public enum ErrorType implements KeyAbstractPayload {

    //Connection error
    PING_NOT_RECEIVED("Ping not received. Disconnection started.."),
    DISCONNECTION_FORCED("Something went wrong during login. Connection refused! Disconnection started.."),
    DISCONNECTION("Disconnection from the server"),
    ERROR_CONNECTION("Error in creating connection, try again"),

    ERR_NICKNAME_LENGTH("Invalid username length. Disconnection.."),
    ERR_NICKNAME_TAKEN("This nickname is already taken  Disconnection.."),
    ONLY_PLAYER("You are the only player connected, wait for another to continue the game"),




    //Lobby error
    ERR_NUM_PLAYER_WANTED("You must select a number of players between 2 and 4.  You will be sent back to GlobalLobby.."),
    ERR_JOINING_GAME_LOBBY("Failed in joining the Game Lobby! You will be sent back to GlobalLobby.."),
    ERR_GAME_FULL("Failed in joining the requested game lobby because is full! You will be sent back to GlobalLobby.."),
    ERR_GAME_NOT_FOUND("Failed in joining the requested game lobby because it doesn't exist! You will be sent back to GlobalLobby.."),


    ERR_NO_FREE_SPOTS("Failed in joining a random game lobby because all games are full!Creating a new Game Lobby for min num players..."),


    ERR_RECONNECT_TO_GAME_LOBBY("Failed to reconnect to previous game lobby! Disconnection.."),
    ERR_JOIN_GLOBAL_LOBBY("Failed in joining the Global Lobby! Disconnection.."),

    //Data error
    ILLEGAL_TURN("it isn't your turn"),
    ILLEGAL_PHASE("This isn't the phase"),
    INVALID_COLUMN("Invalid Column"),
    INVALID_INPUT("Invalid Input"),
    NOT_ENOUGH_FREE_CELLS_COLUMN("You don't have enough cells free in this column"),
    NOT_SELECTABLE_TILE("Not selectable tile"),
    TOO_MANY_TILES("You cannot select another tile:\n1)you have reached the maximum number of selectable, \nor 2)bookshelf has a maximum number of free cells lower than the maximum number."),
    INVALID_ORDER_TILE_REPETITION("You cannot enter the same number twice "),
    INVALID_ORDER_TILE_NUMBER("You have entered a number outside the permitted range "),
    NOT_VALUE_SELECTED("You have not selected anything"),
    INVALID_COORDINATES("COORDINATES of the tile are invalid"),
    NOT_SAME_ROW_OR_COLUMN("Tiles are not on the same row or column, tiles must be all adjacent"),
    NOT_ENOUGH_FREE_EDGES("The selected tile has no free edges"),
    WRONG_PHASE("You can't do this action now.");








    //da dividere

    //NOT_RECEIVED_TILES("Problem with receiving tiles"),






    private final String errorMessage;

    /**
     * Constructor of the class ErrorType that creates a new error type
     * @param errorMessage the error message to print
     */
    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @return the error message to print
     */
    public String getErrorMessage() {
        return errorMessage;
    }

}
