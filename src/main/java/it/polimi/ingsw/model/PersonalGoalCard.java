package it.polimi.ingsw.model;

import java.util.ArrayList;

public class PersonalGoalCard {

    private ArrayList<Integer> coordinates;
    private ArrayList<Type> types;


    public PersonalGoalCard(ArrayList<Integer> coordinates, ArrayList<Type> types) {
        this.coordinates = coordinates;
        this.types = types;
    }

    public ArrayList<Integer> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Integer> coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<Type> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<Type> types) {
        this.types = types;
    }

    /*
    private ArrayList<Integer> coordinates;
    private ArrayList<Type> types;
    private int[] catPosition;
    private int[] bookPosition;
    private int[] gamePosition;
    private int[] framePosition;
    private int[] trophyPosition;
    private int[] plantPosition;

    private boolean[] alreadyScored; //alreadyScored[1] = true se book è scored; ..[0]=true se cat è scored ecc..
    private int numOfScored;

    public PersonalGoalCard(ArrayList<Integer> coordinates, ArrayList<Type> types) {
        this.coordinates = coordinates;
        this.types = types;
    }


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

    public void setCatPosition(int x, int y) {
        this.catPosition[0] = x;
        this.catPosition[1] = y;
    }
    public void setBookPosition(int x, int y) {
        this.bookPosition[0] = x;
        this.bookPosition[1] = y;
    }
    public void setGamePosition(int x, int y) {
        this.gamePosition[0] = x;
        this.gamePosition[1] = y;
    }
    public void setFramePosition(int x, int y) {
        this.framePosition[0] = x;
        this.framePosition[1] = y;
    }
    public void setTrophyPosition(int x, int y) {
        this.trophyPosition[0] = x;
        this.trophyPosition[1] = y;
    }
    public void setPlantPosition(int x, int y) {
        this.plantPosition[0] = x;
        this.plantPosition[1] = y;
    }

*/
}
