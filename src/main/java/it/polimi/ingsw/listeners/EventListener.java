package it.polimi.ingsw.listeners;


import it.polimi.ingsw.message.KeyAbstractPayload;
import it.polimi.ingsw.message.KeyDataPayload;
import it.polimi.ingsw.network.server.GameLobby;

import java.io.IOException;

public abstract class EventListener {

    private final GameLobby gameLobby;

    protected EventListener(GameLobby gameLobby) {
        this.gameLobby = gameLobby;
    }

    public abstract void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue) throws IOException;

    public GameLobby getGameLobby() {
        return gameLobby;
    }
}