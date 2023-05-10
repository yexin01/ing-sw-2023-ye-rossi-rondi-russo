package it.polimi.ingsw.view.CLI;

public enum Commands {

    SELECT_FROM_BOARD1("SELECT a tile"),
    ORDER_TILES1("SELECT order tiles"),
    COLUMN1("SELECT column"),
    PRINT1("THE Board"),
    SELECT_FROM_BOARD2("RESET the LAST choice"),
    ORDER_TILES2("RESET order tiles"),
    COLUMN2("RESET column"),
    PRINT2("YOUR bookshelf"),
    SELECT_FROM_BOARD3("RESET ALL choices"),
    ORDER_TILES3("CONFIRM order tiles"),
    COLUMN3("CONFIRM column"),
    PRINT3("YOUR personalGoalCard"),
    SELECT_FROM_BOARD4("CONFIRM all choices"),
    PRINT4("Compare bookshelf and personal"),
    PRINT5("YOUR Points"),
    PRINT6("Common Goals"),
    PRINT7("HELP");















    private final String command;


    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

   /* public String getSecondErrorMessage(ErrorType e) {
        return e.errorMessage;
    }
    */
}
