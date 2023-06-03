package it.polimi.ingsw.view;


/**
 * The ClientInterface interface defines the methods that a client implementation should provide for interacting with the game.
 * These methods are used to obtain and display information, as well as to prompt the user for input during the game.
 */
public interface ClientInterface  {

    /**
     * Returns the current view of the client.
     *
     * @return The client view.
     */
    ClientView getClientView();
    /**
     * Displays the waiting room screen, where players wait for their turn.
     */
    void waitingRoom();
    /**
     * Displays the token won with the score won and nickname.
     *
     * @param num      The player score won.
     * @param nickname The player nickname.
     */
    void displayToken(int num, String nickname);
    /**
     * Asks the user to input coordinates of the board to select tiles.
     */
    void askCoordinates();
    /**
     * Asks the user to input the order of tiles selected.
     */
    void askOrder() ;
    /**
     * Asks the user to input the column.
     */
    void askColumn() ;
    /**
     * Displays an error message to the user.
     *
     * @param error The error message.
     */
    void displayError(String error);
    /**
     * Displays a general message to the user.
     *
     * @param message The message to display.
     */
    void displayMessage(String message);
    /**
     * Asks the user to input a nickname and establish a connection to the server.
     */
    void askNicknameAndConnection();
    /**
     * Asks the user to make a decision regarding the lobby.
     */
    void askLobbyDecision();
    /**
     * Displays the end game results.
     *
     * @param personalPoints     The array of personal points for each player.
     * @param playerBookshelfFull The nickname of the player with a full bookshelf.
     */
    void endGame(int[] personalPoints,String playerBookshelfFull);
    /**
     * Notifies the user that he is the only player in the game.
     */
    void onlyPlayer();



}
