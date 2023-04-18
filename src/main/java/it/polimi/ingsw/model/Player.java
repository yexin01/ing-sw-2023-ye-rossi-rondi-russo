package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidColumn;
import it.polimi.ingsw.exceptions.NotEnoughFreeCellsColumn;
import it.polimi.ingsw.json.GameRules;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class Player implements PropertyChangeListener {
    private String nickname;
    //TODO final
    private transient PropertyChangeSupport listeners;

    public Player () throws Exception {
        selectedItems=new ArrayList<>();

        //TODO change pass gameRules as a parameter
        GameRules gameRules=new GameRules();
        commonGoalPoints=new int[gameRules.getNumOfCommonGoals()];
    }
    public Player(String nickname) throws Exception {
        this();
        this.nickname = nickname;
        listeners=new PropertyChangeSupport(this);
    }
    public void addListener(PropertyChangeListener listener) {
        listeners.addPropertyChangeListener("BoardSelection",listener);
        listeners.addPropertyChangeListener("BookshelfInsertion",listener);
        listeners.addPropertyChangeListener("Points",listener);
        //listeners.addPropertyChangeListener(listener);
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private ArrayList<ItemTile> selectedItems;
    public ArrayList<ItemTile> getSelectedItems() {
        return selectedItems;
    }
    /*
    public void setSelectedItems(ArrayList<ItemTile> selectedItems) {
        ArrayList<ItemTile> oldItems=this.selectedItems;
        this.selectedItems = selectedItems;
        listeners.firePropertyChange(new PropertyChangeEvent(nickname, "BoardSelection", oldItems, this.selectedItems));
    }

     */
 // 2,1,0 : 10 11 12 --> 12 10 11
    public void permuteSelection(int[] order){
        ArrayList<ItemTile> temp = new ArrayList<>();
        for(int i : order){
            temp.add(selectedItems.get(i));
        }
        selectedItems = temp;
    }

    public void selection(Board board) {
        selectedItems=board.selected();
        listeners.firePropertyChange(new PropertyChangeEvent(nickname, "BoardSelection", null, board));
    }
    //SUM OF ALL POINTS
    private int playerPoints;
    public int getPlayerPoints() {
        return playerPoints;
    }
    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
        listeners.firePropertyChange(new PropertyChangeEvent(nickname, "Points", null, playerPoints));
    }
    //PERSONALGOAL
    private int personalGoalPoints;
    public int getPersonalGoalPoints() {
        return personalGoalPoints;
    }
    public void setPersonalGoalPoints(int personalGoalPoints) {
        this.personalGoalPoints = personalGoalPoints;
    }
    private PersonalGoalCard personalGoalCard;
    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }
    //BOOKSHELF AND POINTS
    private Bookshelf bookshelf;
    public Bookshelf getBookshelf() {
        return bookshelf;
    }
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public void insertBookshelf() throws InvalidColumn, NotEnoughFreeCellsColumn {
        bookshelf.insertAsSelected(selectedItems);
        listeners.firePropertyChange(new PropertyChangeEvent(nickname, "BookshelfInsertion", null, bookshelf));
    }
    private int adjacentPoints;
    public int getAdjacentPoints() {
        return adjacentPoints;
    }
    public void setAdjacentPoints(int adjacentPoints) {
        this.adjacentPoints = adjacentPoints;
    }
    //COMMONGOALPOINTS
    private int[] commonGoalPoints;
    public int[] getCommonGoalPoints() {
        return commonGoalPoints;
    }
    public void setCommonGoalPoints(int[] commonGoalPoints) {
        this.commonGoalPoints = commonGoalPoints;
    }
    public int getCommonGoalPoints(int index) throws IndexOutOfBoundsException {
        return commonGoalPoints[index];
    }
    public void setCommonGoalPoints(int index, int num) throws IndexOutOfBoundsException {
        commonGoalPoints[index] = num;
    }
    public void setToken(int index, int points) {
        this.commonGoalPoints[index] = points;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        listeners.firePropertyChange(evt.getPropertyName(),evt.getOldValue(),evt.getNewValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(nickname, player.nickname);
    }


}


