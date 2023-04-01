package it.polimi.ingsw.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

public class Player extends Observable {
    private ArrayList<ItemTile> selectedItems;
    private int playerPoints;
    private int personalGoalPoints;
    private int adjacentPoints;
    private ArrayList<Integer> commonGoalsPoints; //one node for each commonGoalCards, initially set to 0

    private PersonalGoalCard personalGoalCard;
    private Bookshelf bookshelf;
    private String nickname;
    public Player(String nickname) {
        this.nickname = nickname;
        selectedItems=new ArrayList<>();
        commonGoalsPoints = new ArrayList<>();
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
    public Bookshelf getBookshelf() {
        return bookshelf;
    }
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;
    }

    public PersonalGoalCard getPersonalGoalCard() {return personalGoalCard;
    }

    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard=personalGoalCard;
    }

    public ArrayList<Integer> getCommonGoalsPoints() {
        return commonGoalsPoints;
    }

    public void setCommonGoalsPoints(ArrayList<Integer> commonGoalsPoints) {
        this.commonGoalsPoints = commonGoalsPoints;
    }

    public int getPlayerPoints() {
        return playerPoints;
    }

    public void setPlayerPoints(int playerPoints) {
        this.playerPoints = playerPoints;
    }

    public int getPersonalGoalPoints() {
        return personalGoalPoints;
    }

    public void setPersonalGoalPoints(int personalGoalPoints) {
        this.personalGoalPoints = personalGoalPoints;
    }

    public int getAdjacentPoints() {
        return adjacentPoints;
    }

    public void setAdjacentPoints(int adjacentPoints) {
        this.adjacentPoints = adjacentPoints;
    }

    //TODO: import from json or use a map to increase flexibility
    public void setAdjacentPoints(){
        int sum=0;
        for(int groupSize : bookshelf.findAdjacentTilesGroups()){
            if (groupSize < 3) continue;
            sum += (
                    switch (groupSize){
                        case 3 -> 2;
                        case 4 -> 3;
                        case 5 -> 5;
                        default -> 8;
                    }
                    );
        }
        adjacentPoints = sum;
    }

    //TODO: remove parameter numCommonGoal from method checkGoal
    public void checkCommonGoals(ArrayList<CommonGoalCard> commonGoalCards){
        // this.playerPoints = scoringToken1.getpoints + scoringToken2.getpoints + personalGoalPoints+ computeAdjacent+game_end
        for (CommonGoalCard card: commonGoalCards){
            if (commonGoalsPoints.get(commonGoalCards.indexOf(card))==0 && card.checkGoal()){
                //...
            }
        }
    }
}
/*
    public void updatePlayerPoints(){
        checkPersonalGoal();
        setPersonalGoalPoints();
        checkCommonGoals(game.getCommonGoalCards()[0],game.getCommonGoalCards()[1]);
        setAdjacentPoints();
        playerPoints = personalGoalPoints+commonGoalPoints+adjacentPoints;
    }
*/


    /*
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
/*
    public void checkCommonGoals(CommonGoalCard commonGoal1, CommonGoalCard commonGoal2){
        // this.playerPoints = scoringToken1.getpoints + scoringToken2.getpoints + personalGoalPoints+ computeAdjacent+game_end
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


    public int getPersonalGoalPoints() {
        return personalGoalPoints;
    }

    public void setPersonalGoalPoints(int personalGoalPoints) {
        this.personalGoalPoints = personalGoalPoints;
    }


    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }
}
*/
/*
    public void updatePlayerPoints(){
        checkPersonalGoal();
        setPersonalGoalPoints();
        checkCommonGoals(game.getCommonGoalCards()[0],game.getCommonGoalCards()[1]);
        setAdjacentPoints();
        playerPoints = personalGoalPoints+commonGoalPoints+adjacentPoints;
    }
*/


    /*
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
/*
    public void checkCommonGoals(CommonGoalCard commonGoal1, CommonGoalCard commonGoal2){
        // this.playerPoints = scoringToken1.getpoints + scoringToken2.getpoints + personalGoalPoints+ computeAdjacent+game_end
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


    public int getPersonalGoalPoints() {
        return personalGoalPoints;
    }

    public void setPersonalGoalPoints(int personalGoalPoints) {
        this.personalGoalPoints = personalGoalPoints;
    }


    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }
*/



