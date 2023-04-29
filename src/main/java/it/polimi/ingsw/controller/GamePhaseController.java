package it.polimi.ingsw.controller;

public class GamePhaseController {
    private GamePhase currentPhase;

    public void changeGamePhase() {
        currentPhase = GamePhase.values()[(currentPhase.ordinal() + 1)];
    }

    public GamePhase getCurrentGamePhase() {
        return currentPhase;
    }
    public void setCurrentGamePhase(GamePhase currentPhase) {
        this.currentPhase=currentPhase;
    }




}
