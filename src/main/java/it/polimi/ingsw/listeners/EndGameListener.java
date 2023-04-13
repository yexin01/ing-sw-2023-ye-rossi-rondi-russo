package it.polimi.ingsw.listeners;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.messages.MessagePayload;
import it.polimi.ingsw.messages.ServerMessageHeader;
import it.polimi.ingsw.messages.ServerMessageType;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.server.GameLobby;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class EndGameListener implements PropertyChangeListener {

    private final GameLobby lobby;

    public EndGameListener(GameLobby lobby) {
        this.lobby = lobby;
    }


    //TODO endgame listener
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        List<Player> ranking = ((ArrayList<Player>) evt.getNewValue());
        winner(ranking.get(0).getNickname());
        ranking.remove(0);
        notWinner(ranking);
    }

    private void winner(String winner) {
        MessagePayload payload = new MessagePayload();
        payload.setAttribute("Winner", winner);
        ServerMessageHeader header = new ServerMessageHeader("EndGameWinner", ServerMessageType.GAME_UPDATE);
        notifyLobby(new MessageFromServer(header, payload));
    }

    private void notWinner(List<Player> notWinner) {
        MessagePayload payload = new MessagePayload();
        payload.setAttribute("NotWinner", notWinner);
        ServerMessageHeader header = new ServerMessageHeader("EndGameNotWinner", ServerMessageType.GAME_UPDATE);
        notifyLobby(new MessageFromServer(header, payload));
    }

    private void notifyLobby(MessageFromServer message) {
        lobby.broadcast(message);
        lobby.doEndGameOperations();
    }


}
