package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.listeners.ListenerManager;
import it.polimi.ingsw.messages.EventType;
import it.polimi.ingsw.model.PersonalGoalCard;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

public class ModelView {

    private ListenerManager listenerManager;
    private int indexRemoveToken;
    private HashMap<String, Integer> playersId;

    private BoardBoxView[][] boardView;
    private CommonGoalView[] commonGoalViews;

    private ItemTileView[][][] bookshelfView;
    private PlayerPointsView[] playerPoints;
    private PersonalGoalCard[] playerPersonalGoal;
    private ItemTileView[] selectedItems;

    public ModelView(HashMap<String, Integer> playersId, GameRules gameRules){
        int numPlayers=playersId.keySet().size();
        this.playersId=playersId;
        listenerManager=new ListenerManager();
        commonGoalViews=new CommonGoalView[gameRules.getNumOfCommonGoals()];
        bookshelfView=new ItemTileView[numPlayers][gameRules.getRowsBookshelf()][gameRules.getColumnsBookshelf()];
        playerPoints=new PlayerPointsView[numPlayers];
        playerPersonalGoal=new PersonalGoalCard[numPlayers];


    }

    public int getIntegerValue(String key) {
        if (playersId.containsKey(key)) {
            return playersId.get(key);
        } else {
            return 0;
        }
    }
    public <T> T[] deleteObjectByIndex(T[] array, int indexToDelete) {
        int length = array.length;
        if (indexToDelete < 0 || indexToDelete >= length) {
            return array;
        }
        T[] newArray = (T[]) Array.newInstance(array.getClass().getComponentType(), length-1);
        int counter = 0;
        for (int i = 0; i < length; i++) {
            if (i != indexToDelete) {
                newArray[counter++] = array[i];
            }
        }
        return newArray;
    }

    public int deleteAllObjectByIndex(String nickname){
        int index=getIntegerValue(nickname);
        playerPoints = deleteObjectByIndex(playerPoints, index);
        playerPersonalGoal=deleteObjectByIndex(playerPersonalGoal,index);
        bookshelfView=deleteObjectByIndex(bookshelfView,index);
        playersId.remove(nickname);
        for(Map.Entry<String, Integer> entry : playersId.entrySet()) {
            if(entry.getValue() >= index) {
                entry.setValue(entry.getValue() - 1);
            }
        }
        return index;

    }




    public CommonGoalView[] getCommonGoalViews() {
        return commonGoalViews;
    }

    public void setCommonGoalViews(CommonGoalView commonGoalViews,int index,String nickname) {
        this.commonGoalViews[index] = commonGoalViews;
        listenerManager.fireEvent(EventType.WIN_TOKEN,nickname,this);
    }




    public ItemTileView[][] getBookshelfView(String nickname) {
        return bookshelfView[getIntegerValue(nickname)];
    }

    public void setBookshelfView(ItemTileView[][] bookshelfView,String nickname) {
        this.bookshelfView[getIntegerValue(nickname)] = bookshelfView;
    }

    public PlayerPointsView getPlayerPoints(String nickname) {
        return playerPoints[getIntegerValue(nickname)];
    }

    public void setPlayerPoints(PlayerPointsView playerPersonalGoal,String nickname) {
        this.playerPoints[getIntegerValue(nickname)] = playerPersonalGoal;
        listenerManager.fireEvent(EventType.END_TURN,nickname,this);
    }




    public PersonalGoalCard getPlayerPersonalGoal(String nickname) {
        return playerPersonalGoal[getIntegerValue(nickname)];
    }
    public void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal, String nickname) {
        this.playerPersonalGoal[getIntegerValue(nickname)] = playerPersonalGoal;
    }

    public PersonalGoalCard getPlayerPersonal(String nickname) {
        return playerPersonalGoal[getIntegerValue(nickname)] ;
    }



    public ItemTileView[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ItemTileView[] selectedItems,String nickname) {
        this.selectedItems = selectedItems;
        listenerManager.fireEvent(EventType.TILES_SELECTED,nickname,this);
    }
    public void addListener(EventType eventType, EventListener listener) {
        this.listenerManager.addListener(eventType,listener);
    }

    public void removeListener(EventType eventType, EventListener listener) {
        this.listenerManager.removeListener(eventType, listener);
    }


    public ListenerManager getListenerManager(){
        return listenerManager;
    }

    public int getIndexRemoveToken() {
        return indexRemoveToken;
    }

    public void setIndexRemoveToken(int indexRemoveToken) {
        this.indexRemoveToken = indexRemoveToken;
    }

    public BoardBoxView[][] getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardBoxView[][] boardView) {
        this.boardView = boardView;
    }
}


/*
public class GameListener {

    private ServerView serverView;
    private MessagePayload boardView;
    private MessagePayload[] commonGoalViews;
    private String[] playerNicknames;
    private MessagePayload bookshelfAndPoints;
    private MessagePayload playerPersonalGoal;


    public ServerView getServerView() {
        return serverView;
    }

    public void setServerView(ServerView serverView) {
        this.serverView = serverView;
    }


    public int getPlayerByNickname(String nickname) {
        int num=0;
        for (String p: playerNicknames) {
            if (p.equals(nickname)) return num;
            num++;
        }
        return -1;
    }

    public MessagePayload getBoardView() {
        return boardView;
    }

    public void setBoardView(MessagePayload boardView) {
        this.boardView = boardView;
    }

    public MessagePayload[] getCommonGoalViews() {
        return commonGoalViews;
    }

    public void setCommonGoalViews(MessagePayload commonGoalView,int index) {
        this.commonGoalViews[index] = commonGoalView;
    }

    public MessagePayload getBookshelfAndPoints() {
        return bookshelfAndPoints;
    }

    public void setBookshelfAndPoints(MessagePayload bookshelfAndPoints) {
        this.bookshelfAndPoints = bookshelfAndPoints;
    }

    public MessagePayload getPlayerPersonalGoal() {
        return playerPersonalGoal;
    }

    public void setPlayerPersonalGoal(MessagePayload playerPersonalGoal) {
        this.playerPersonalGoal = playerPersonalGoal;
    }
}

 */
