package it.polimi.ingsw.model;

public class PersonalGoalCard {
    //add setter
    private int[] catPosition;
    private int[] bookPosition;
    private int[] gamePosition;
    private int[] framePosition;
    private int[] trophyPosition;
    private int[] plantPosition;

    private boolean[] alreadyScored; //alreadyScored[1] = true se book è scored; ..[0]=true se cat è scored ecc..
    private int numOfScored;


    public int[] getCatPosition() {
        return catPosition;
    }

    public int[] getBookPosition() {
        return bookPosition;
    }

    public int[] getGamePosition() {
        return gamePosition;
    }

    public int[] getFramePosition() {
        return framePosition;
    }

    public int[] getTrophyPosition() {
        return trophyPosition;
    }

    public int[] getPlantPosition() {
        return plantPosition;
    }

    public int getNumOfScored() {
        return numOfScored;
    }

    public boolean[] getAlreadyScored() {
        return alreadyScored;
    }

    public void setAlreadyScored(boolean alreadyScored, int i) {
        this.alreadyScored[i] = alreadyScored;
    }

    public void increaseNumOfScored() {
        this.numOfScored++;
    }
}
