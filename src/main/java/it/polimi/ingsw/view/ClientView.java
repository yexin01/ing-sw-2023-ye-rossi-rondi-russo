package it.polimi.ingsw.view;

import it.polimi.ingsw.model.PersonalGoalCard;
import it.polimi.ingsw.model.modelView.*;

public class ClientView {
    private BoardBoxView[][] boardView;
    private CommonGoalView[] commonGoalViews;
    private int[] commonGoalPoints;
    //TODO initialization will be inserted in the method related to the start of the game

    private String[] players;
    private ItemTileView[][] bookshelfView;
    private ItemTileView[] tilesSelected;
    private PlayerPointsView playerPoints;
    private PersonalGoalCard playerPersonalGoal;
    private String nickname;



    public String getNickname(){
        return nickname;
    }


    public BoardBoxView[][] getBoardView() {
        return boardView;
    }


    public void setBoardView(BoardBoxView[][] boardView){
        this.boardView=boardView;
    }

    public CommonGoalView[] getCommonGoalViews() {
        return commonGoalViews;
    }

    public void setCommonGoalViews(CommonGoalView[] commonGoalViews) {
        this.commonGoalViews = commonGoalViews;
    }

    public int[] getCommonGoalPoints() {
        return commonGoalPoints;
    }

    public void setCommonGoalPoints(int[] commonGoalPoints) {
        this.commonGoalPoints = commonGoalPoints;
    }

    public String[] getPlayers() {
        return players;
    }

    public void setPlayers(String[] players) {
        this.players = players;
    }

    public ItemTileView[][] getBookshelfView() {
        return bookshelfView;
    }

    public void setBookshelfView(ItemTileView[][] bookshelfView) {
        this.bookshelfView = bookshelfView;
    }

    public ItemTileView[] getTilesSelected() {
        return tilesSelected;
    }

    public void setTilesSelected(ItemTileView[] tilesSelected) {
        this.tilesSelected = tilesSelected;
    }

    public PlayerPointsView getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(PlayerPointsView playerPoints) {
        this.playerPoints = playerPoints;
    }

    public PersonalGoalCard getPlayerPersonalGoal() {
        return playerPersonalGoal;
    }

    public void setPlayerPersonalGoal(PersonalGoalCard playerPersonalGoal) {
        this.playerPersonalGoal = playerPersonalGoal;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}