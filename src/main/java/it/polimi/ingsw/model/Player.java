package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.*;


import java.util.ArrayList;
import java.util.Objects;

/**
 *Player: Class that contains all the information related to the player during the game.
 */
public class Player {


    private String nickname;


    private ModelView modelView;

    /**

     Constructs a Player object with the specified nickname, model view, and game rules.
     @param nickname The nickname of the player.
     @param modelView The model view object containing the game state.
     @param gameRules The game rules object defining the rules and settings of the game.
     */
    public Player (String nickname, ModelView modelView,GameRules gameRules) {
        this.modelView=modelView;
        selectedItems=new ArrayList<>();
        commonGoalPoints=new int[gameRules.getNumOfCommonGoals()];
        this.nickname = nickname;
    }
    /**
     * Returns the nickname of the player.
     * @return The nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }
    /**
     * Sets the nickname of the player.
     * @param nickname The nickname to set.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private ArrayList<ItemTile> selectedItems;
    /**
     * Returns the list of selected item tiles by the player.
     * @return The list of selected item tiles.
     */
    public ArrayList<ItemTile> getSelectedItems() {
        return selectedItems;
    }


    /**
     * Check that:
     * 1)the order array is the same length as the selectedItems,
     * 2)there are no repeated numbers or
     * 3)invalid numbers (outside the range between 0 and order.length-1).
     * @param order:array indicating the player selected order;
     * @return ErrorType;
     */

    public ErrorType checkPermuteSelection(int[] order) {
        int maxIndex = selectedItems.size() - 1;
        if(order.length!=selectedItems.size()){
            return ErrorType.INVALID_INPUT;
        }
        for (int i = 0; i < order.length; i++) {
            int curIndex = order[i];
            if (curIndex > maxIndex || curIndex < 0) {
                return ErrorType.INVALID_ORDER_TILE_NUMBER;
            }
            for (int j = i + 1; j < order.length; j++) {
                if (order[j] == curIndex) {
                    return ErrorType.INVALID_ORDER_TILE_REPETITION;
                }
            }
        }
        return null;
    }

    /**
     * Associates the selected tiles to the selectedItems attribute, sets the modelView arrayList
     * with a new ItemTileView array.
     *
     */
    public void cloneTilesSelected(){
        ItemTileView[] selectedItem=new ItemTileView[selectedItems.size()];
        int j=0;
        for(ItemTile i:selectedItems){
            selectedItem[j++]=new ItemTileView(i.getType(), i.getTileID());
        }
        modelView.setSelectedItems(selectedItem);
    }

    /**
     *permute tile order based on the order chosen by the player
     * @param order
     */
    public void permuteSelection(int[] order){
        ArrayList<ItemTile> temp = new ArrayList<>();
        for(int i : order){
            temp.add(selectedItems.get(i));
        }
        selectedItems = temp;
        cloneTilesSelected();
    }


    //SUM OF ALL POINTS


    //PERSONALGOAL
    private int personalGoalPoints;
    /**
     * Returns the personal goal points of the player.
     * @return The personal goal points.
     */
    public int getPersonalGoalPoints() {
        return personalGoalPoints;
    }
    /**
     * Sets the personal goal points of the player.
     * @param personalGoalPoints The personal goal points to set.
     */
    public void setPersonalGoalPoints(int personalGoalPoints) {
        this.personalGoalPoints = personalGoalPoints;
    }
    private PersonalGoalCard personalGoalCard;
    /**
     * Returns the personal goal card of the player.
     * @return The personal goal card.
     */
    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }
    /**
     * Sets the personal goal card of the player.
     * @param personalGoalCard The personal goal card to set.
     */
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }
    //BOOKSHELF AND POINTS
    private Bookshelf bookshelf;
    /**
     * Returns the bookshelf of the player.
     * @return The bookshelf.
     */
    public Bookshelf getBookshelf() {
        return bookshelf;
    }
    /**
     * Sets the bookshelf of the player.
     * @param bookshelf The bookshelf to set.
     */
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }
    /**
     * Inserts the selected items into the bookshelf at the specified column.
     * @param column The column index.
     * @throws Error if an error occurs during the insertion.
     */
    public void insertBookshelf(int column) {
        bookshelf.insertTiles(selectedItems,column);
    }
    private int adjacentPoints;
    /**
     * Returns the adjacent points of the player.
     * @return The adjacent points.
     */
    public int getAdjacentPoints() {
        return adjacentPoints;
    }
    /**
     * Sets the adjacent points of the player.
     * @param adjacentPoints The adjacent points to set.
     */
    public void setAdjacentPoints(int adjacentPoints) {
        this.adjacentPoints = adjacentPoints;
    }
    //COMMONGOALPOINTS
    private int[] commonGoalPoints;
    /**
     * Returns the array of common goal points of the player.
     * @return The array of common goal points.
     */
    public int[] getCommonGoalPoints() {
        return commonGoalPoints;
    }
    /**
     * Sets the array of common goal points of the player.
     * @param commonGoalPoints The array of common goal points to set.
     */
    public void setCommonGoalPoints(int[] commonGoalPoints) {
        this.commonGoalPoints = commonGoalPoints;
    }
    /**
     * Sets the token at the specified index with the specified points.
     * @param index The index of the token.
     * @param points The points to set.
     */
    public void setToken(int index, int points) {
        this.commonGoalPoints[index] = points;
    }
    /**
     * Sets the selected items of the player.
     * @param selectedItems The list of selected items to set.
     */
    public void setSelectedItems(ArrayList<ItemTile> selectedItems){
        this.selectedItems=selectedItems;
    }

    /**
     * Sets the model view of the game.
     * @param modelView:model view of the game.
     */
    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }
}


