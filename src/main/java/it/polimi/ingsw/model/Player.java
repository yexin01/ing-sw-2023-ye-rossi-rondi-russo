package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.model.modelView.*;


import java.util.ArrayList;
import java.util.Objects;

public class Player {


    //private GameInfo gameInfo;
    private String nickname;


    private ModelView modelView;

    public Player (String nickname, ModelView modelView) throws Exception {
        this.modelView=modelView;
        selectedItems=new ArrayList<>();
        //TODO change pass gameRules as a parameter
        GameRules gameRules=new GameRules();
        commonGoalPoints=new int[gameRules.getNumOfCommonGoals()];
        this.nickname = nickname;
        //this.listenerManager = new ListenerManager();

        //this.listenerManager=new ListenerManager(serverView);
    }

  /*
    public <T> void addListener(EventType eventType, EventListener<T> listener) { // Aggiungi il parametro generico T
        listenerManager.addListener(eventType, listener);
    }

    public <T> void removeListener(EventType eventType, EventListener<T> listener) { // Aggiungi il parametro generico T
        listenerManager.removeListener(eventType, listener);
    }

  */
/*
    public void addListener(EventType eventType, EventListener listener) {
        this.listenerManager.addListener(eventType,listener);
    }

    public void removeListener(EventType eventType, EventListener listener) {
        this.listenerManager.removeListener(eventType, listener);
    }


    public ListenerManager getListenerManager(){
        return listenerManager;
    }

     */



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


    public void selection(Board board) {
        selectedItems=board.selected();
        ItemTileView[] selectedItem=new ItemTileView[selectedItems.size()];
        int j=0;
        for(ItemTile i:selectedItems){
            selectedItem[j++]=new ItemTileView(i.getType(), i.getTileID());
        }
        modelView.setSelectedItems(selectedItem);
    }

    public ErrorType checkPermuteSelection(int[] order) throws Error {
        int maxIndex = selectedItems.size() - 1;
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

    public void permuteSelection(int[] order){
        ArrayList<ItemTile> temp = new ArrayList<>();
        for(int i : order){
            temp.add(selectedItems.get(i));
        }
        selectedItems = temp;
    }


    //SUM OF ALL POINTS



    public void setPlayerPoints() {
        modelView.setBookshelfView(bookshelf.cloneBookshelf(),nickname);
        PlayerPointsView playerPointsView =new PlayerPointsView(commonGoalPoints,adjacentPoints, nickname);
        modelView.setPlayerPoints(playerPointsView,modelView.getIntegerValue(nickname));
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



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player player)) return false;
        return Objects.equals(nickname, player.nickname);
    }

/*
    public void setListenerManager(ListenerManager listenerManager) {
        this.listenerManager = listenerManager;
    }
 */



    public void setModelView(ModelView modelView) {
        this.modelView = modelView;
    }
}


