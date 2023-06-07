package it.polimi.ingsw.listeners;


import it.polimi.ingsw.message.KeyAbstractPayload;
import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

/**
 * The EventListener class is an abstract class that serves as a base for implementing event listeners.
 * It provides a common structure and access to the associated game lobby.
 */
public abstract class EventListener {
    private final GameLobby gameLobby;

    /**
     * Constructs an EventListener with the specified game lobby.
     * @param gameLobby The game lobby associated with the listener.
     */
    protected EventListener(GameLobby gameLobby) {
        this.gameLobby = gameLobby;
    }
    /**
     * Defines the method to be implemented by the subclasses to handle specific events.
     * @param event The event triggered.
     * @param playerNickname The nickname of the player associated with the event.
     * @param newValue The new value or data associated with the event.
     * @throws IOException If an I/O error occurs during event handling.
     */
    public abstract void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException;
    /**
     * Returns the game lobby associated with the event listener.
     * @return The game lobby.
     */
    public GameLobby getGameLobby() {
        return gameLobby;
    }
}