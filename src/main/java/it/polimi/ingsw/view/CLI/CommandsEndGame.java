package it.polimi.ingsw.view.CLI;

public enum CommandsEndGame implements Commands {
    JOIN_NEW_GAME("Join a new game"),
    //RESET_CHOICE("RESET choice"),
    QUIT_GAME("Quit game");
    // CONFIRM_CHOICE("CONFIRM choice"),




    private final String command;


    CommandsEndGame(String command) {
        this.command = command;
    }
    public String getCommand() {
        return command;
    }
}
