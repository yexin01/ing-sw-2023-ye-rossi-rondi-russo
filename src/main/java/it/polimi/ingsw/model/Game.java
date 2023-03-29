package it.polimi.ingsw.model;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {
    private int numPlayers;
    private Player turnPlayer;
    private ArrayList<CommonGoalCard> CommonGoalCards;
    private Player firstPlayer;


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
    private boolean finishGame;

    private boolean finish;

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


    public Game() {
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public ArrayList<CommonGoalCard> getCommonGoalCards() {
        return CommonGoalCards;
    }

    public void setCommonGoalCards(ArrayList<CommonGoalCard> commonGoalCards) {
        CommonGoalCards = commonGoalCards;
    }

    public boolean isFinishGame() {
        return finishGame;
    }

    public void setFinishGame(boolean finishGame) {
        this.finishGame = finishGame;
    }
    public void setFinishGameopposite() {
        finishGame = !finishGame;
        setChanged();
        notifyObservers(finish);
    }
}