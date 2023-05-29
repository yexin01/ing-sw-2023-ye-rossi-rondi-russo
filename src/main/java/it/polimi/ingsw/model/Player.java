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


    //private GameInfo gameInfo;
    private String nickname;


    private ModelView modelView;

    /**
     * Constructorwhich instantiates the player with the current game's modelView and nickname;
     * @param nickname:player nickname;
     * @param modelView:modelView of the game;
     * @throws Exception
     */

    public Player (String nickname, ModelView modelView,GameRules gameRules) throws Exception {
        this.modelView=modelView;
        selectedItems=new ArrayList<>();
        commonGoalPoints=new int[gameRules.getNumOfCommonGoals()];
        this.nickname = nickname;
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


    /**
     * Check that:1)the order array is the same length as the selectedItems,2)there are no repeated numbers or
     * 3)invalid numbers (outside the range between 0 and order.length-1).
     * @param order:array indicating the player selected order;
     * @return ErrorType;
     * @throws Error
     */

    public ErrorType checkPermuteSelection(int[] order) throws Error {
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

    public void insertBookshelf(int column) throws Error {
        bookshelf.insertTiles(selectedItems,column);
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
    public void setSelectedItems(ArrayList<ItemTile> selectedItems){
        this.selectedItems=selectedItems;
    }

    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }
}


