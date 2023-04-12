package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
//SE IL TURNO é finiTo SETTA il giocatore successivo se finisce il turno
//cambia la fase del turno

public class TurnController {
    private TurnPhase currentPhase;
    private Game game;

    //passa alla fase successiva
    public void changePhase() {
        currentPhase = TurnPhase.values()[(currentPhase.ordinal() + 1) % TurnPhase.values().length];
    }

 //inserito nel gameController
/*

    //se il turno é finito restituisce true
    public boolean endTurn() {
        if (currentPhase == TurnPhase.END_TURN) {
            game.setNextPlayer();
            return true;
        }
        return false;
    }
    public Player getTurnPlayer() {
        return game.getTurnPlayer();
    }

 */
    public boolean checkIfTurnIsEnded() {
        return currentPhase == TurnPhase.END_TURN;
    }
    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }
    public void setCurrentPhase(TurnPhase currentPhase) {
        this.currentPhase=currentPhase;
    }
    public Player getTurnPlayer() {
        return game.getTurnPlayer();
    }

    public boolean endgame() {
        game.setNextPlayer();
        if (game.isEndGame() && game.getTurnPlayer().equals(game.getFirstPlayer())) {
            return true;
        }
        return false;
    }


}
