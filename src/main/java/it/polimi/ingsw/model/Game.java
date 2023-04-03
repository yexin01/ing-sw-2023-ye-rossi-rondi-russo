package it.polimi.ingsw.model;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {

    public Game(Board board) {
        this.board=board;
    }

    //PLAYERS
    private int numPlayers;

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }
    private ArrayList<Player> players=new ArrayList<Player>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private Player firstPlayer;
    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    private Player turnPlayer;
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }


    //CURRENT BOARD
    private Board board;

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    //COMMON GOAL CARDS
    private ArrayList<CommonGoalCard> CommonGoalCards;
    public ArrayList<CommonGoalCard> getCommonGoalCards() {
        return CommonGoalCards;
    }

    public void setCommonGoalCards(ArrayList<CommonGoalCard> commonGoalCards) {
        CommonGoalCards = commonGoalCards;
    }

    //TODO it will be deleted in the non-deprecated version
    private boolean finish; //used by view1 for inserting players

    public boolean isFinish() {
        return finish;
    }
    public void setFinishopposite() {
        finish = !finish;
        setChanged();
        notifyObservers(finish);
    }
    public void setFinish(boolean finish) {
        this.finish = finish;
        setChanged();
        notifyObservers(finish);
    }




}