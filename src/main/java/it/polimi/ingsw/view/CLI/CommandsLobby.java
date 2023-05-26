package it.polimi.ingsw.view.CLI;

public enum CommandsLobby implements Commands {
    CREATE_GAME_LOBBY("CREATE a new Lobby"),
    //RESET_CHOICE("RESET choice"),
    JOIN_SPECIFIC_GAME_LOBBY("JOIN a specific Lobby"),
    // CONFIRM_CHOICE("CONFIRM choice"),
    JOIN_RANDOM_GAME_LOBBY("JOIN random Game"),
    QUIT_SERVER("QUIT");

    private final String command;


    CommandsLobby(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
