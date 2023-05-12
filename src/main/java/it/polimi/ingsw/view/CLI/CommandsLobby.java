package it.polimi.ingsw.view.CLI;

public enum CommandsLobby implements Commands {
    CREATE_GAME_LOBBY("Create a new Lobby"),
    JOIN_SPECIFIC_GAME_LOBBY("Add a specific Lobby"),
    JOIN_RANDOM_GAME_LOBBY("Join random Game");

    private final String command;


    CommandsLobby(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
