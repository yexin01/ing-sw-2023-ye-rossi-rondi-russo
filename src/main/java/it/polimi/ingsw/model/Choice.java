package it.polimi.ingsw.model;

import java.util.ArrayList;

public class Choice {
    private Board board;
    private ArrayList<BoardBox> playerChoice=new ArrayList<>();

    public ArrayList<BoardBox> getPlayerChoice() {
        return playerChoice;
    }

    public void setPlayerChoice(ArrayList<BoardBox> playerChoice) {
        this.playerChoice = playerChoice;
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }
}
