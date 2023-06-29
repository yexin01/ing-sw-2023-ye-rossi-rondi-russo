package it.polimi.ingsw.view.CLI;
/**
 * Enumeration of turn commands.
 */
public enum CommandsTurn implements Commands {

    SELECT_FROM_BOARD1("SELECT a tile"),
    ORDER_TILES1("SELECT order tiles"),
    COLUMN1("SELECT column"),
    PRINT1("Board"),
    PRINT2("Bookshelf"),
    SELECT_FROM_BOARD2("RESET the LAST choice"),
    ORDER_TILES2("RESET order tiles"),
    COLUMN2("RESET column"),
    PRINT3("PersonalGoal"),
    PRINT4("Compare "+(PRINT2.ordinal()+1)+"-"+(PRINT3.ordinal()+1)),
    SELECT_FROM_BOARD3("RESET ALL choices"),
    ORDER_TILES3("CONFIRM order tiles"),
    COLUMN3("CONFIRM column"),
    PRINT5("Points"),
    PRINT6("CommonGoals"),
    SELECT_FROM_BOARD4("CONFIRM all choices"),
    PRINT7("Game-Rules"),
    PRINT8("Something wrong"),
    PRINT9("QUIT app");


    private final String command;

    /**
     * Constructs a turn command with the specified command string.
     *
     * @param command the turn command
     */
    CommandsTurn(String command) {
        this.command = command;
    }
    /**
     * Returns the command string of the turn command.
     *
     * @return the command string
     */
    public String getCommand() {
        return command;
    }

}