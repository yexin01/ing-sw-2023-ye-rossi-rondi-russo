package it.polimi.ingsw.model.modelView;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Arrays;

public class ModelView {
    private int numPlayers;
    private BoardView boardView;
    private CommonGoalView[] commonGoalViews;
    private String[] players;
    private ItemTileView[][][] bookshelfView;
    private PlayerPointsView[] playerPoints;
    private PersonalGoalCard[] playerPersonalGoal;

    public ModelView(){
        /*
        this.numPlayers=numPlayers;
        commonGoalViews=new CommonGoalView[gameRules.getNumOfCommonGoals()];
        bookshelfView=new ItemTileView[numPlayers][gameRules.getRowsBookshelf()][gameRules.getColumnsBookshelf()];
        playerPoints=new PlayerPointsView[numPlayers];
        playerPersonalGoal=new PersonalGoalCard[numPlayers];
        players=new String[numPlayers];

         */
    }
    public void setAll(GameRules gameRules,int numPlayers){
        this.numPlayers=numPlayers;
        commonGoalViews=new CommonGoalView[gameRules.getNumOfCommonGoals()];
        bookshelfView=new ItemTileView[numPlayers][gameRules.getRowsBookshelf()][gameRules.getColumnsBookshelf()];
        playerPoints=new PlayerPointsView[numPlayers];
        playerPersonalGoal=new PersonalGoalCard[numPlayers];
        players=new String[numPlayers];
    }


    public int getPlayerByNickname(String nickname) {
        int num=0;
        for (String p: players) {
            if (p.equals(nickname)) return num;
            num++;
        }
        return -1;
    }


    public BoardView getBoardView() {
        return boardView;
    }

    public void setBoardView(BoardView boardView) {
        this.boardView = boardView;
    }

    public CommonGoalView[] getCommonGoalViews() {
        return commonGoalViews;
    }

    public void setCommonGoalViews(CommonGoalView commonGoalViews,int index) {
        this.commonGoalViews[index] = commonGoalViews;
    }




    public ItemTileView[][] getBookshelfView(int index) {
        return bookshelfView[index];
    }

    public void setBookshelfView(ItemTileView[][] bookshelfView,int index) {
        this.bookshelfView[index] = bookshelfView;
    }

    public PlayerPointsView[] getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(PlayerPointsView playerPersonalGoal, int index) {
        this.playerPoints[index] = playerPersonalGoal;
    }




    public PersonalGoalCard[] getPlayerPersonalGoal() {
        return playerPersonalGoal;
    }
    public void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal, int index) {
        this.playerPersonalGoal[index] = playerPersonalGoal;
    }
    public PlayerPointsView getPlayerPoints(int index) {
        return playerPoints[index] ;
    }
    public PersonalGoalCard getPlayerPersonal(int index) {
        return playerPersonalGoal[index] ;
    }

    public void setPlayers(String players,int index) {
        this.players[index] =players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
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
