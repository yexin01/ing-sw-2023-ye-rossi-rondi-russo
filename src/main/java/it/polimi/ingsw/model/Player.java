package it.polimi.ingsw.model;

public class Player {
    public String nickname;
    private int playerPoints;
    private int personalGoalPoints;
    private boolean[] scoringTokens;
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

    public void updatePlayerPoints(){
        // this.playerPoints = scoringToken1.getpoints + scoringToken2.getpoints + personalGoalPoints+ computeAdjacent+game_end
    }


    public boolean playTurn(Board board) {
        return false;
    }
}
