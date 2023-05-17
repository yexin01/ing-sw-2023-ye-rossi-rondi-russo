package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.model.PersonalGoalCard;

import java.lang.reflect.Array;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ModelView {

    private int turnPlayer;

    private int[][] commonGoalView;
    private int[] token;

    private BoardBoxView[][] boardView;

    private TurnPhase turnPhase;

    private ItemTileView[][][] bookshelfView;
    private PlayerPointsView[] playerPoints;
    private PersonalGoalCard[] playerPersonalGoal;
    private ItemTileView[] selectedItems;

    public ModelView(int numPlayers,GameRules gameRules){
        //pointsLeftCommon=new int[gameRules.getNumOfCommonGoals()];
        bookshelfView=new ItemTileView[numPlayers][gameRules.getRowsBookshelf()][gameRules.getColumnsBookshelf()];
        playerPoints=new PlayerPointsView[numPlayers];
        playerPersonalGoal=new PersonalGoalCard[numPlayers];
        commonGoalView =new int[gameRules.getNumOfCommonGoals()][2];
        token=new int[gameRules.getNumOfCommonGoals()];
    }

    public int getIntegerValue(String nickname) {
        OptionalInt index = IntStream.range(0, playerPoints.length)
                .filter(i -> playerPoints[i].getNickname().equals(nickname))
                .findFirst();
        return index.orElse(-1);
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

        //playersOrder.remove(nickname);
        return index;

    }



    public ItemTileView[][] getBookshelfView(String nickname) {
        return bookshelfView[getIntegerValue(nickname)];
    }

    public void setBookshelfView(ItemTileView[][] bookshelfView,String nickname) {
        this.bookshelfView[getIntegerValue(nickname)] = bookshelfView;
    }

    public PlayerPointsView[] getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(PlayerPointsView playerPoints,int index) {
        this.playerPoints[index] = playerPoints;
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

    public void setSelectedItems(ItemTileView[] selectedItems) {
        token=new int [commonGoalView[0].length];
        this.selectedItems = selectedItems;
    }

    public BoardBoxView[][] getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardBoxView[][] boardView) {
        this.boardView = boardView;
    }


    public TurnPhase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(TurnPhase turnPhase) {
        this.turnPhase = turnPhase;
    }




    public int[][] getCommonGoalView() {
        return commonGoalView;
    }

    public void setIdCommon(int row,int index,int pointsLeft) {
        this.commonGoalView[row][index]= pointsLeft;
    }





    public int getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(int turnPlayer) {
        this.turnPlayer = turnPlayer;
    }


    public void setPlayersPoints(PlayerPointsView[] playerPoints){
        this.playerPoints=playerPoints;
    }
    public void setPlayerPersonalGoal(PersonalGoalCard[] playerPersonalGoal){
        this.playerPersonalGoal=playerPersonalGoal;
    }


    public int[] getToken() {
        return token;
    }

    public void setToken(int[] token) {
        this.token = token;
    }
}

