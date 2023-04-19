package it.polimi.ingsw.model;

import it.polimi.ingsw.exceptions.InvalidColumn;
import it.polimi.ingsw.exceptions.NotEnoughFreeCellsColumn;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.listeners.ListenerManager;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    private String nickname;
    //TODO final
    private ListenerManager listenerManager;

    public Player () throws Exception {
        selectedItems=new ArrayList<>();

        //TODO change pass gameRules as a parameter
        GameRules gameRules=new GameRules();
        commonGoalPoints=new int[gameRules.getNumOfCommonGoals()];
    }
    public Player(String nickname) throws Exception {
        this();
        this.nickname = nickname;
        this.listenerManager = new ListenerManager();
    }

    public void addListener(String eventName, EventListener listener) {
        this.listenerManager.addListener(eventName, listener);
    }

    public void removeListener(String eventName, EventListener listener) {
        this.listenerManager.removeListener(eventName, listener);
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
        listenerManager.fireEvent("BoardSelection",board,nickname);
    }
    //SUM OF ALL POINTS
    private int playerPoints;
    public int getPlayerPoints() {
        return playerPoints;
    }
    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
        listenerManager.fireEvent("Points",playerPoints,nickname);
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
        listenerManager.fireEvent("BookshelfInsertion",bookshelf,nickname);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(nickname, player.nickname);
    }


}


