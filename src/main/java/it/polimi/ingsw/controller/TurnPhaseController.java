package it.polimi.ingsw.controller;

//SE IL TURNO é finiTo SETTA il giocatore successivo se finisce il turno
//cambia la fase del turno

public class TurnPhaseController {
    private TurnPhase currentPhase;

    public void changePhase() {
        currentPhase = TurnPhase.values()[(currentPhase.ordinal() + 1)];
    }

    public TurnPhase getCurrentPhase() {
        return currentPhase;
    }
    public void setCurrentPhase(TurnPhase currentPhase) {
        this.currentPhase=currentPhase;
    }





}
