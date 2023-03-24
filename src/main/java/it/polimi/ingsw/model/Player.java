package it.polimi.ingsw.model;



import java.util.ArrayList;
import java.util.Observable;

public class Player extends Observable {
    private ArrayList<ItemTile> selectedItems;

    private String nickname;
    public Player(String nickname) {
        this.nickname = nickname;

    }

    public ArrayList<ItemTile> getSelectedItems() {
        return selectedItems;
    }
    public void setSelectedItems(ArrayList<ItemTile> selectedItems) {
        this.selectedItems = selectedItems;
        setChanged();
        notifyObservers(selectedItems);
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
