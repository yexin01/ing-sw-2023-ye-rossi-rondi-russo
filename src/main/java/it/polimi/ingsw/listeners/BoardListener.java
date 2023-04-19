package it.polimi.ingsw.listeners;


import it.polimi.ingsw.model.Board;


public class BoardListener implements EventListener{
    @Override
    public void onEvent(EventType eventType, Object newValue, String nickname) {
        Board newBoard=(Board) newValue;
        System.out.println(nickname +" changed Board");
        newBoard.printMatrix();

    }
}

