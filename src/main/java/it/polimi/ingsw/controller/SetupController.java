package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

//will be gameLobby

public class SetupController implements Serializable {
    public static final int MAX_USERNAME_LENGTH = 15;

    @Serial
    private static final long serialVersionUID = 6254508020115118835L;

    private ArrayList<String> waitingPlayers;

    public SetupController() {
        waitingPlayers = new ArrayList<>();
    }

    public ArrayList<String> getWaitingPlayers() {
        return waitingPlayers;
    }

    public boolean isPlayerInWaitingPlayers(String nickname) {
        return waitingPlayers.contains(nickname);
    }

    //TODO: while NicKname invalid keep asking in CLI/GUI, then add player
    public boolean checkNickname(String nickname) {
        return waitingPlayers.contains(nickname) && nickname.length() > 1 && nickname.length() <= MAX_USERNAME_LENGTH;
    }

    public void addPlayer(String nickname) {
        waitingPlayers.add(nickname);
    }

    public void removePlayer(String nickname) { //for when a player disconnects
        waitingPlayers.remove(nickname);
    }

    public boolean isFull() throws Exception {
        GameRules gameRules=new GameRules();
        int maxPlayers=gameRules.getMaxPlayers();
        return waitingPlayers.size() == maxPlayers;
    }


}


