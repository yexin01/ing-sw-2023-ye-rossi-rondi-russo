package it.polimi.ingsw.message;

public enum KeyLobbyPayload implements KeyAbstractPayload  {
    JOIN_GLOBAL_LOBBY,

    GLOBAL_LOBBY_DECISION,

    CREATE_GAME_LOBBY,
    JOIN_SPECIFIC_GAME_LOBBY,
    JOIN_RANDOM_GAME_LOBBY,

    RECONNECT_TO_GAME_LOBBY,

    JOINED_GAME_LOBBY,
}
