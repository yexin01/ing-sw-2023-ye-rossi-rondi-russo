package it.polimi.ingsw.model;

public abstract class CommonGoalCard {
    //private ScoringToken[] tokens;

    abstract boolean checkGoal();

    /* abstract ScoringToken pullToken();
     una volta con gli stack deve solo chiamare pull dallo stack (la collection di oggetti scoringtokens)
        e toglie il primo di questi */
}