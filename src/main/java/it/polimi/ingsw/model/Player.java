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














/*
public class Player {
    public String nickname;
    private Game game;
    private int playerPoints;
    private int personalGoalPoints;
    private int commonGoalPoints;
    private int adjacentPoints;
    private boolean[] scoringTokens; //useless
    private ScoringToken scoringToken1;
    private ScoringToken scoringToken2;
    private ItemTile[] selectedItems;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;

    //getter e setter
    public int getPlayerPoints() {
        return playerPoints;
    }

    public boolean[] getScoringTokens() {
        return scoringTokens;
    }

    public ItemTile[] getSelectedItems() {
        return selectedItems;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    // functions
    public void pick (){

    }

    public void updatePlayerPoints(){
        checkPersonalGoal();
        setPersonalGoalPoints();
        checkCommonGoals(game.getCommonGoalCards()[0],game.getCommonGoalCards()[1]);
        setAdjacentPoints();
        playerPoints = personalGoalPoints+commonGoalPoints+adjacentPoints;
    }


    public void checkPersonalGoal(){
        if(!getPersonalGoalCard().getAlreadyScored()[0] && getBookshelf().getMatrix()[getPersonalGoalCard().getCatPosition()[0]][getPersonalGoalCard().getCatPosition()[1]].getItemTile().getType()==Type.CAT){
            getPersonalGoalCard().setAlreadyScored(true, 0);
            getPersonalGoalCard().increaseNumOfScored();
        }
        if(!getPersonalGoalCard().getAlreadyScored()[1] && getBookshelf().getMatrix()[getPersonalGoalCard().getBookPosition()[0]][getPersonalGoalCard().getBookPosition()[1]].getItemTile().getType()==Type.BOOK){
            getPersonalGoalCard().setAlreadyScored(true, 1);
            getPersonalGoalCard().increaseNumOfScored();
        }
        if(!getPersonalGoalCard().getAlreadyScored()[2] && getBookshelf().getMatrix()[getPersonalGoalCard().getGamePosition()[0]][getPersonalGoalCard().getGamePosition()[1]].getItemTile().getType()==Type.GAME){
            getPersonalGoalCard().setAlreadyScored(true, 2);
            getPersonalGoalCard().increaseNumOfScored();
        }
        if(!getPersonalGoalCard().getAlreadyScored()[3] && getBookshelf().getMatrix()[getPersonalGoalCard().getFramePosition()[0]][getPersonalGoalCard().getFramePosition()[1]].getItemTile().getType()==Type.FRAME){
            getPersonalGoalCard().setAlreadyScored(true, 3);
            getPersonalGoalCard().increaseNumOfScored();
        }
        if(!getPersonalGoalCard().getAlreadyScored()[4] && getBookshelf().getMatrix()[getPersonalGoalCard().getTrophyPosition()[0]][getPersonalGoalCard().getTrophyPosition()[1]].getItemTile().getType()==Type.TROPHY){
            getPersonalGoalCard().setAlreadyScored(true, 4);
            getPersonalGoalCard().increaseNumOfScored();
        }
        if(!getPersonalGoalCard().getAlreadyScored()[5] && getBookshelf().getMatrix()[getPersonalGoalCard().getPlantPosition()[0]][getPersonalGoalCard().getPlantPosition()[1]].getItemTile().getType()==Type.PLANT){
            getPersonalGoalCard().setAlreadyScored(true, 5);
            getPersonalGoalCard().increaseNumOfScored();
        }
    }

    public void setPersonalGoalPoints(){
        switch (getPersonalGoalCard().getNumOfScored()){
            case 0:
                personalGoalPoints = 0;
                break;
            case 1:
                personalGoalPoints = 1;
                break;
            case 2:
                personalGoalPoints = 2;
                break;
            case 3:
                personalGoalPoints = 4;
                break;
            case 4:
                personalGoalPoints = 6;
                break;
            case 5:
                personalGoalPoints = 9;
                break;
            case 6:
                personalGoalPoints = 12;
                break;
            default:
                System.out.println("Something went wrong while setting personalGoalPoints!");
        }
    }

    //metti for loop estensibile
    public void checkCommonGoals(CommonGoalCard commonGoal1, CommonGoalCard commonGoal2){
        if (scoringToken1==null && commonGoal1.checkGoal()){
            scoringToken1 = commonGoal1.pullToken();
        }
        if (scoringToken2==null && commonGoal2.checkGoal()){
            scoringToken2 = commonGoal2.pullToken();
        }
        int token1Points = scoringToken1 != null ? scoringToken1.getTokenPoints() : 0;
        int token2Points = scoringToken2 != null ? scoringToken2.getTokenPoints() : 0;
        commonGoalPoints = token1Points + token2Points;
    }

    public void setAdjacentPoints(){
        int sum=0;
        for(int groupSize : bookshelf.findAdjacentTilesGroups()){
            sum+=groupSize;
        }
        adjacentPoints = sum;
    }



    public boolean playTurn(Board board) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
*/