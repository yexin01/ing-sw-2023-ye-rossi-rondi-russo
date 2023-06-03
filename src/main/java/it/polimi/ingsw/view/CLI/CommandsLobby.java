package it.polimi.ingsw.view.CLI;
/**
 * Enumeration of lobby commands.
 */
public enum CommandsLobby implements Commands {
    CREATE_GAME_LOBBY("CREATE a new Lobby"),
    JOIN_SPECIFIC_GAME_LOBBY("JOIN a specific Lobby"),
    JOIN_RANDOM_GAME_LOBBY("JOIN random Game"),
    QUIT_SERVER("QUIT");

    private final String command;
    /**
     * Constructs a lobby command with the specified command string.
     *
     * @param command the lobby command
     */
    CommandsLobby(String command) {
        this.command = command;
    }
    /**
     * Returns the command string of the lobby command.
     *
     * @return the command string
     */
    public String getCommand() {
        return command;
    }
}
