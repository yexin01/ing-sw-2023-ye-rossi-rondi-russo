package it.polimi.ingsw.message;

public enum ErrorType implements KeyAbstractPayload {

    //qui connection (nickname, porte, connessioni)
    PING_NOT_RECEIVED("Ping not received.\n Disconnection started.."),//stampare
    DISCONNECTION_FORCED("Something went wrong during login. Connection refused! \n Disconnection started.."),
    DISCONNECTION("Disconnection from the server"),

    ERR_NICKNAME_LENGTH("Invalid username length.\n Disconnection.."),//da rifare connessione+login lato client
    ERR_NICKNAME_TAKEN("This nickname is already taken \n Disconnection.."),




    //qui lobby (tutte le cose per connettersi alle mappe e prima di creare il game)
    ERR_NUM_PLAYER_WANTED("You must select a number of players between 2 and 4. \n You will be sent back to GlobalLobby.."), //da rimandare alla globalLobby
    ERR_JOINING_GAME_LOBBY("Failed in joining the Game Lobby!\n You will be sent back to GlobalLobby.."),
    ERR_GAME_FULL("Failed in joining the requested game lobby because is full!\n You will be sent back to GlobalLobby.."),
    ERR_GAME_NOT_FOUND("Failed in joining the requested game lobby because it doesn't exist!\n You will be sent back to GlobalLobby.."),


    ERR_NO_FREE_SPOTS("Failed in joining a random game lobby because all games are full!\nCreating a new Game Lobby for min num players..."),//stampare


    ERR_RECONNECT_TO_GAME_LOBBY("Failed to reconnect to previous game lobby! \n Disconnection.."),//da rifare connnesione+Login lato client
    ERR_JOIN_GLOBAL_LOBBY("Failed in joining the Global Lobby! \n Disconnection.."),

    //qui data
    ILLEGAL_TURN("it isn't your turn"),
    ILLEGAL_PHASE("This isn't the phase"),
    INVALID_COLUMN("Invalid Column"),
    INVALID_INPUT("Invalid Input"),
    NOT_ENOUGH_FREE_CELLS_COLUMN("You don't have enough cells free in this column"),
    NOT_SELECTABLE_TILE("Not selectable tile"),
    TOO_MANY_TILES("You cannot select another tile:\n1)you have reached the maximum number of selectable, or \n2)bookshelf has a maximum number of free cells lower than the maximum number."),
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

    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
