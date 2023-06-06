package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;

import java.util.*;

public class Game {
    private ArrayList<Player> players;
    private final ModelView modelview;
    private Board board;

    private ArrayList<CommonGoalCard> commonGoalCards;
    private boolean endGame;

    /**
     * Constructor Game:set the modelView and associate a new Board;
     * @param modelview:modelView of the current game;
     */

    public Game(ModelView modelview){
        commonGoalCards=new ArrayList<>();
        this.modelview=modelview;
        board=new Board(modelview);
    }

      //PLAYERS

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }


    public Player getFirstPlayer() {
        return players.get(0);
    }

    /**
     * @return:nickname of the last connected player
     */
    public String getLastPlayer() {
        Boolean[] activePlayers= modelview.getActivePlayers();
        int i;
        for(i=players.size()-1; !activePlayers[i];i--){
        }
        return players.get(i).getNickname();
    }
    public Player getTurnPlayerOfTheGame() {
        return players.get(turnPlayerInt());
    }
    public int getIntByNickname(String nickname) {
        return modelview.getIntegerValue(nickname);
    }

    /**
     * sets the player true or false depending on whether he is reconnected or disconnected;
     * @param nickname:player who has reconnected or disconnected;
     * @param discOrRec:reconnected true, disconnected false
     * @return
     */

    public Boolean[] disconnectionAndReconnectionPlayer(String nickname,boolean discOrRec) {
        Boolean[] activePlayers=modelview.getActivePlayers();
        for(int i=0;i<players.size();i++){
            if(players.get(i).getNickname().equals(nickname)){
                activePlayers[i]=discOrRec;
                break;
            }
        }
        return activePlayers;
    }

    //CURRENT BOARD

    public Board getBoard() {
        return board;
    }
    public void setBoard(Board board) {
        this.board = board;
    }

    //COMMON GOAL CARDS

    public ArrayList<CommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }

    public void setCommonGoalCards(ArrayList<CommonGoalCard> commonGoalCards) {
        this.commonGoalCards = commonGoalCards;
    }

    /**
     *randomly extracts players' play order and instantiates them.
     * @param nicknames:ArrayList of player nicknames
     * @throws Exception
     */
    public void addPlayers(ArrayList<String> nicknames) throws Exception {
        ArrayList<Integer> orderPlayers=generateRandomNumber(nicknames.size(), nicknames.size());
        GameRules gameRules=new GameRules();
        players = new ArrayList<Player>();
        for(Integer player:orderPlayers){
            String nickname=nicknames.get(player);
            players.add(new Player(nickname,modelview,gameRules));
        }
    }


    /**
     *generates different random numbers in a fixed range and a number of times numOfgenerated
     * @param range
     * @param numOfGenerated
     * @return
     */
    public ArrayList<Integer> generateRandomNumber(int range, int numOfGenerated) {
        ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
        Random random = new Random();


        while (uniqueNumbers.size() < numOfGenerated) {
            int newNumber = random.nextInt(range);
            if (!uniqueNumbers.contains(newNumber)) {
                uniqueNumbers.add(newNumber);
            }
        }
        return uniqueNumbers;
    }

    /**
     * Read from the json file how many objects to instantiate (numOfCommonGoals) and how many possible entities to instantiate
     * (numOfPossibleCommonGoalsCards). Using an array of classes, instantiate only the subclasses of CommonGoalCard
     * in the  positions number of the Integer  arrayList numbers.
     * @param gameRules
     * @throws Exception
     */
    public void createCommonGoalCard(GameRules gameRules,ArrayList<Integer> numbers ) throws Exception {

        //ArrayList<Integer> numbers = generateRandomNumber(numOfPossibleCommonGoalsCards, numOfCommonGoals);
        setCommonGoalCards(new ArrayList<CommonGoalCard>());
        int i=0;
        //TokenListener tokenListener=new TokenListener(sendMessages);
        for (Integer number : numbers) {
            String className = gameRules.getCommonGoalCard(number);
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            modelview.setIdCommon(0,i,number);
            commonGoalCards.add((CommonGoalCard) obj);
            i++;
        }

        setCommonGoalCardsPoints(gameRules);
    }

    /**
     *sets the CommonGoalCards scores, based on the number of players in the game, reading the scores from the json file
     * @param gameRules: to read from json file;
     * @throws Exception
     */
    public void setCommonGoalCardsPoints(GameRules gameRules) throws Exception {
        ArrayList<Integer> points=gameRules.getCommonGoalPoints(players.size());
        int numCommonGoal=0;
        for(CommonGoalCard c: commonGoalCards){
            for(int i=0;i<points.size();i++){
                c.getPoints().add(points.get(i));
            }
            modelview.setIdCommon(1,numCommonGoal,commonGoalCards.get(numCommonGoal).getLastPoint());
            numCommonGoal++;
        }
    }
    public int turnPlayerInt(){
        return modelview.getTurnPlayer();
    }
    public void setTurnPlayer(int index){
        modelview.setTurnPlayer(index);
    }

    /**
     *instantiates (reading from the json file) only the personalGoalCards present in the positions of the arrayList numbers.
     * Positions were extracted randomly.
     *For each player it instantiates: the reference bookshelf and commonGoalPoints,
     *set on the modelView PlayerPointsView , personalGoal id and the player's bookshelf.
     * @param gameRules: to read from json file;
     * @param numbers
     */
    public void createPersonalGoalCard(GameRules gameRules, ArrayList<Integer> numbers ) {

        int rows= gameRules.getRowsBookshelf();
        int columns= gameRules.getColumnsBookshelf();
        int maxSelectableTiles=gameRules.getMaxSelectableTiles();
        int i = 0;
        for (Player p : players) {
            p.setCommonGoalPoints(new int[commonGoalCards.size()]);
            PlayerPointsView setupPoints=new PlayerPointsView(p.getCommonGoalPoints(),0,p.getNickname());
            modelview.setPlayerPoints(setupPoints,i);

            PersonalGoalCard turnPersonal=gameRules.getPersonalGoalCard(numbers.get(i));
            p.setPersonalGoalCard(turnPersonal);
            modelview.setPlayerPersonalGoal(turnPersonal,p.getNickname());

            Bookshelf bookshelf=new Bookshelf(rows,columns, maxSelectableTiles);
            p.setBookshelf(bookshelf);
            modelview.setBookshelfView(bookshelf.cloneBookshelf(),p.getNickname());
            i++;
        }
    }

    public Player getPlayerByNickname(String nickname) {
        for (Player p: players) {
            if (p.getNickname().equals(nickname)) return p;
        }
        return null;
    }

    /**
     *updates the adjacent points of the current player by reading the associated
     * score from the json file
     * @param gameRules: to read from json file;
     * @throws Exception
     */
    public void updateAdjacentPoints(GameRules gameRules) throws Exception{
        int[] points= gameRules.getAdjacentArray();
        List<Integer> adjacent= turnBookshelf().findAdjacentTilesGroups();
        if(adjacent.isEmpty()){
            getTurnPlayerOfTheGame().setAdjacentPoints(0);
        }else{
            int sum=0;
            for(int groupSize : adjacent){
                if (groupSize<2) continue;
                if((groupSize)>points.length){
                    groupSize=points.length+1;
                }
                sum += points[groupSize-2];
            }
            getTurnPlayerOfTheGame().setAdjacentPoints(sum);
        }

    }
    /**
     *updates the commonGoalPoints points of the current player.Update the modelView
     * if a token is reached.
     * @throws Exception
     */
    public void updatePointsCommonGoals(){
        for (int i = 0; i< getTurnPlayerOfTheGame().getCommonGoalPoints().length; i++){
            CommonGoalCard c=commonGoalCards.get(i);
            if (getTurnPlayerOfTheGame().getCommonGoalPoints()[i]==0 && true/*c).checkGoal(turnBookshelf().getMatrix())*/){
                int pointsWon=commonGoalCards.get(i).removeToken();
                modelview.getToken()[i]=pointsWon;
                modelview.setIdCommon(1,i,c.getLastPoint());
                getTurnPlayerOfTheGame().setToken(i,pointsWon);
            }
        }
    }
    /**
     *updates the personalPoints of the current player by reading the associated
     * score from the json file
     * @param gameRules: to read from json file;
     * @throws Exception
     */
    public void updatePersonalGoalPoints(GameRules gameRules) throws Exception {
        int[] points= gameRules.getPersonalGoalPoints();
        int numScored = 0;
        for (PersonalGoalBox box : turnPersonalGoal().getCells()){
            if (turnBookshelf().getMatrix()[box.getX()][box.getY()].getType() != null && turnBookshelf().getMatrix()[box.getX()][box.getY()].getType().equals(box.getTypePersonal())){
                numScored++;
            }
        }
        getTurnPlayerOfTheGame().setPersonalGoalPoints(points[numScored]);
    }

    /**
     * after calling the various methods that update the scores of the current player,
     *sets the modelView with the updated scores.
     * @throws Exception
     */

    public void updateAllPoints() throws Exception {
        GameRules gameRules=new GameRules();
        updateAdjacentPoints(gameRules);
        updatePointsCommonGoals();
        updatePersonalGoalPoints(gameRules);
        Player player=getTurnPlayerOfTheGame();
        PlayerPointsView playerPoints=new PlayerPointsView(player.getCommonGoalPoints(),player.getAdjacentPoints(),player.getNickname());
        modelview.getPersonalPoints()[modelview.getTurnPlayer()]=player.getPersonalGoalPoints();
        modelview.getPlayerPoints()[modelview.getTurnPlayer()]=playerPoints;
        modelview.setBookshelfView(player.getBookshelf().cloneBookshelf(),player.getNickname());
    }
    public boolean getEndGame(){
        return endGame;
    }

    public PersonalGoalCard turnPersonalGoal(){return getTurnPlayerOfTheGame().getPersonalGoalCard();}

    public Bookshelf turnBookshelf(){return getTurnPlayerOfTheGame().getBookshelf();}

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }


    public ModelView getModelView() {
        return modelview;
    }


}