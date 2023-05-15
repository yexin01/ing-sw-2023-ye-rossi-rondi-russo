package it.polimi.ingsw.message;

/**
 * Enum that represents the key of the payload for information about the lobby
 */
public enum KeyLobbyPayload implements KeyAbstractPayload  {
    GLOBAL_LOBBY_DECISION,

    CREATE_GAME_LOBBY,
    JOIN_SPECIFIC_GAME_LOBBY,
    JOIN_RANDOM_GAME_LOBBY,
    QUIT_SERVER,

    RECONNECT_TO_GAME_LOBBY,

    JOINED_GAME_LOBBY,
}
