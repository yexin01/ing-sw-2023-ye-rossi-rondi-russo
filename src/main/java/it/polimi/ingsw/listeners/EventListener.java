package it.polimi.ingsw.listeners;


import it.polimi.ingsw.message.KeyAbstractPayload;
import it.polimi.ingsw.message.KeyDataPayload;
import it.polimi.ingsw.network.server.GameLobby;

public abstract class EventListener {

    private final GameLobby gameLobby;

    protected EventListener(GameLobby gameLobby) {
        this.gameLobby = gameLobby;
    }

    public abstract void fireEvent(KeyAbstractPayload event, String playerNickname, Object newValue);

    public GameLobby getGameLobby() {
        return gameLobby;
    }
}