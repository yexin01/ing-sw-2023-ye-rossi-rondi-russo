package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.json.GameRules;

import it.polimi.ingsw.model.PersonalGoalCard;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;
import java.util.OptionalInt;
import java.util.stream.IntStream;

public class ModelView {

    private int turnPlayer;
    private boolean[] activePlayers;
    private int MAX_SELECTABLE_TILES;

    private int[][] commonGoalView;
    private int[] token;
    private int[] personalPoints;

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
        activePlayers=new boolean[numPlayers];
        Arrays.fill(activePlayers, true);
        commonGoalView =new int[gameRules.getNumOfCommonGoals()][2];
        token=new int[gameRules.getNumOfCommonGoals()];
        personalPoints=new int[numPlayers];
        MAX_SELECTABLE_TILES= gameRules.getMaxSelectableTiles();
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
    public void winnerEndGame() {
        Integer[] indices = new Integer[playerPoints.length];
        for (int i = 0; i < playerPoints.length; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(index -> playerPoints[index].getPoints() + personalPoints[index]));

        PlayerPointsView[] sortedPlayerPoints = new PlayerPointsView[playerPoints.length];
        int[] sortedPersonalPoints = new int[personalPoints.length];

        for (int i = 0; i < playerPoints.length; i++) {
            sortedPlayerPoints[i] = playerPoints[indices[i]];
            sortedPersonalPoints[i] = personalPoints[indices[i]];
        }

        playerPoints = sortedPlayerPoints;
        personalPoints = sortedPersonalPoints;
        int i=0;
        for (PlayerPointsView obj : playerPoints) {
            System.out.println(obj.getPoints()+"  "+personalPoints[i++]+" "+obj.getNickname());
        }
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

    public int getPersonalPoint(String nickname) {
        return personalPoints[getIntegerValue(nickname)];
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

    public PlayerPointsView[] checkWinner() {
        PlayerPointsView[] sortedObjects = Arrays.copyOf(playerPoints, playerPoints.length);

        Arrays.sort(sortedObjects, Comparator.comparingInt(PlayerPointsView::getPoints));


        for (PlayerPointsView obj : playerPoints) {
            System.out.println(obj.getPoints()+" "+obj.getNickname());
        }
        for (PlayerPointsView obj : sortedObjects) {
            System.out.println(obj.getPoints()+" "+obj.getNickname());
        }
        return sortedObjects;
    }
    public String getTurnNickname(){
        return playerPoints[turnPlayer].getNickname();
    }




    public ItemTileView[] getSelectedItems() {
        return selectedItems;
    }

    public void setSelectedItems(ItemTileView[] selectedItems) {
        this.token=new int[commonGoalView[0].length];
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

    public int[] getPersonalPoints() {
        return personalPoints;
    }

    public void setPersonalPoints(int[] personalPoints) {
        this.personalPoints = personalPoints;
    }
    public boolean[] getActivePlayers() {
        return activePlayers;
    }

    public void setActivePlayers(boolean[] activePlayers) {
        this.activePlayers = activePlayers;
    }

    public int getMAX_SELECTABLE_TILES() {
        return MAX_SELECTABLE_TILES;
    }

    public void setMAX_SELECTABLE_TILES(int MAX_SELECTABLE_TILES) {
        this.MAX_SELECTABLE_TILES = MAX_SELECTABLE_TILES;
    }
}

