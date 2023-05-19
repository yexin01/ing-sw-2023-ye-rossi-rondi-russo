package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;

import java.util.*;
//TODO verrà cambiata la gestione degli active players, l'array verrà letto direttamente dalla modelView
public class Game {
    private ArrayList<Player> players;
    private final ModelView modelview;
    private Board board;

    private ArrayList<CommonGoalCard> commonGoalCards;
    private boolean endGame;

//TODO cambiare creazione senza numMaxPlayer controllo gia effettuato prima
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

    public String getLastPlayer(boolean[] activePlayers) {
        int i;
        for(i=players.size()-1; !activePlayers[i];i--){
        }
        return players.get(i).getNickname();
    }
    public Player getTurnPlayerOfTheGame() {
       // System.out.println("TURN PLAYER :"+players.get(turnPlayerInt()).getNickname());
        return players.get(turnPlayerInt());
    }
    public int getIntByNickname(String nickname) {
        return modelview.getIntegerValue(nickname);
    }

    public void setNextPlayer(boolean[] activePlayers) {
        // in case a player abandons the game and is the last one, index is > of players.size()-1
        do{
            if(turnPlayerInt() >= (players.size() - 1))
                setTurnPlayer(0);
            else setTurnPlayer(turnPlayerInt()+1);
        }while(!activePlayers[turnPlayerInt()]);
        System.out.println("Il prossimo giocatore che deve giocare ed é attivo é: "+players.get(turnPlayerInt()).getNickname());
    }
    public boolean[] disconnectionAndReconnectionPlayer(boolean[] activePlayers,String nickname,boolean discOrRec) {
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

    public void addPlayers(ArrayList<String> nicknames) throws Exception {
        ArrayList<Integer> orderPlayers=generateRandomNumber(nicknames.size(), nicknames.size());
        players = new ArrayList<Player>();
        for(Integer player:orderPlayers){
            String nickname=nicknames.get(player);
            players.add(new Player(nickname,modelview));
            //modelview.getPlayersOrder().add(nickname);
        }
        //modelview.setPlayersOrder(players);


    }

    /**
     * check that the string passed as a parameter is not already present in the usedNames set
     * @param nickname if it is different from the present nicknames: add it and return true, otherwise return false
     * @return
     */
    //TODO change just scroll the arraylist and check that there is not an equal one
    public boolean differentNickname(String nickname) {
        if (players.isEmpty()) {
            return true;
        } else {
            Set<String> usedNames = new HashSet<>();
            for (Player p : players) {
                usedNames.add(p.getNickname());
            }
            if (usedNames.contains(nickname)) {
                return false;
            } else {
                return true;
            }
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
    public void createCommonGoalCard(GameRules gameRules) throws Exception {

        int numOfCommonGoals = gameRules.getNumOfCommonGoals();
        int numOfPossibleCommonGoalsCards = gameRules.getCommonGoalCardsSize();
        ArrayList<Integer> numbers = generateRandomNumber(numOfPossibleCommonGoalsCards, numOfCommonGoals);
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
        createCommonGoalPlayer(gameRules);
    }


    public void createCommonGoalPlayer(GameRules gameRules){
        int numCommonGoalCards=gameRules.getNumOfCommonGoals();
        for(Player p:players){
            p.setCommonGoalPoints(new int[numCommonGoalCards]);

        }
    }


    /**
     * Match arraylist of scores based on number of players
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
     *instantiates personalGoalCard based on the number of players
     */

    public void createPersonalGoalCard(GameRules gameRules) {
        ArrayList<Integer> numbers = generateRandomNumber(gameRules.getPossiblePersonalGoalsSize(), players.size());
        int rows= gameRules.getRowsBookshelf();
        int columns= gameRules.getColumnsBookshelf();
        int maxSelectableTiles=gameRules.getMaxSelectableTiles();
        int[] commonGoalsSetup;
        int i = 0;
        for (Player p : players) {
            commonGoalsSetup=new int[commonGoalCards.size()];
            PlayerPointsView setupPoints=new PlayerPointsView(commonGoalsSetup,0,p.getNickname());
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
    public void updatePointsCommonGoals(){
        for (int i = 0; i< getTurnPlayerOfTheGame().getCommonGoalPoints().length; i++){
            CommonGoalCard c=commonGoalCards.get(i);
            if (getTurnPlayerOfTheGame().getCommonGoalPoints()[i]==0 && true/*c).checkGoal(turnBookshelf().getMatrix())*/){
                int pointsWon=commonGoalCards.get(i).removeToken(i);
                modelview.getToken()[i]=pointsWon;
                modelview.setIdCommon(1,i,c.getLastPoint());
                getTurnPlayerOfTheGame().setToken(i,pointsWon);
            }
        }
    }
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

    public void updateAllPoints() throws Exception {
        GameRules gameRules=new GameRules();
        updateAdjacentPoints(gameRules);
        updatePointsCommonGoals();
        updatePersonalGoalPoints(gameRules);
        Player player=getTurnPlayerOfTheGame();
        PlayerPointsView playerPoints=new PlayerPointsView(player.getCommonGoalPoints(),player.getAdjacentPoints(),player.getNickname());
        modelview.getPersonalPoints()[modelview.getTurnPlayer()]=player.getPersonalGoalPoints();
        modelview.getPlayerPoints()[modelview.getTurnPlayer()]=playerPoints;
    }

    public PersonalGoalCard turnPersonalGoal(){return getTurnPlayerOfTheGame().getPersonalGoalCard();}

    public Bookshelf turnBookshelf(){return getTurnPlayerOfTheGame().getBookshelf();}


    public int deletePlayer(String nickname) {
        int index=modelview.deleteAllObjectByIndex(nickname);
        players.remove(index);
        return index;
    }



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