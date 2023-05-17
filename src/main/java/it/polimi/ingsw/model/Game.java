package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.CommonGoalView;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;

import java.util.*;
//TODO CAMBIARE LA CREAZIONE DELLE PERSONAL GIAL DIRETTAMENTE DI ITEMTILEVIEW
public class Game {
    //private GameInfo gameInfo;
    private ArrayList<Player> players;
    private final ModelView modelview;
    private Board board;
    private int numMaxPlayers;
    private int turnPlayer;
    private ArrayList<CommonGoalCard> commonGoalCards;
    private boolean endGame;


    public Game(GameRules gameRules,int numMaxPlayers,ModelView modelview){
        //players=new ArrayList<>();
        commonGoalCards=new ArrayList<>();
        this.modelview=modelview;
        board=new Board(modelview);
    }
      //PLAYERS


    public int getNumMaxPlayers() {
        return numMaxPlayers;
    }

    public void setNumMaxPlayersNumPlayers(int numMaxPlayers) {
        this.numMaxPlayers = numMaxPlayers;
    }


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
        System.out.println("TURN PLAYER :"+players.get(turnPlayer).getNickname());
        return players.get(turnPlayer);
    }
    public int getIntByNickname(String nickname) {
        return modelview.getIntegerValue(nickname);
    }
    public int getTurnPlayer() {
        return turnPlayer;
    }
    public void setNextPlayer(boolean[] activePlayers) {
        // in case a player abandons the game and is the last one, index is > of players.size()-1
        System.out.println(activePlayers[getTurnPlayer()]);
        do{
            System.out.println("DENTRO "+activePlayers[getTurnPlayer()]);
            if(turnPlayer >= (players.size() - 1))
                turnPlayer=0;
            else turnPlayer++;
        }while(!activePlayers[getTurnPlayer()]);
        modelview.setTurnPlayer(players.get(getTurnPlayer()).getNickname());
        System.out.println("Il prossimo giocatore che deve giocare ed é attivo é: "+players.get(getTurnPlayer()).getNickname());
    }
    public boolean[] disconnectionAndReconnectionPlayer(boolean[] activePlayers,String nickname,boolean discOrRec) {
        System.out.println("PRIMA" +nickname);
        int j=0;
        for(Player p:players){
            System.out.println(p.getNickname()+" "+activePlayers[j++]);
        }
        for(int i=0;i<players.size();i++){
            System.out.println("il giocatore che sto esaminando "+players.get(i).getNickname());
            if(players.get(i).getNickname().equals(nickname)){
                activePlayers[i]=discOrRec;
                break;
            }
        }
        System.out.println("DOPO");
        j=0;
        for(Player p:players){
            System.out.println(p.getNickname()+" "+activePlayers[j++]);
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
            modelview.getPlayersOrder().add(nickname);
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
            ((CommonGoalCard) obj).setModelView(modelview);
            modelview.getIdCommon()[i++]=number;
            commonGoalCards.add((CommonGoalCard) obj);
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
        CommonGoalView common;
        int numCommonGoal=0;
        for(CommonGoalCard c: commonGoalCards){
            int[] pointsView= points.stream().mapToInt(Integer::intValue).toArray();
            common=new CommonGoalView(null, 0, pointsView);
            modelview.setCommonGoalViews(common,numCommonGoal++,null);
            //serverView.setCommonGoalViews(common,numCommonGoal++);
            for(int i=0;i<points.size();i++){
                c.getPoints().add(points.get(i));
            }
        }
    }

    /**
     *
     * inserts into the player's bookshelf if the tiles are less than the maximum
     * number of insertable tiles and returns true
     * @param
     * @return
     */




/*
    public void insertOnceATime(int numberTile,int column) {

        for (int i = turnBookshelf().getMatrix().length - 1; i>=0; i--) {
            if (turnBookshelf().getMatrix()[i][column].getTileID() == -1) {
                turnBookshelf().setTile(selectedTiles().get(numberTile), i, column);
                break;
            }
        }
        selectedTiles().remove(numberTile);

    }

 */



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
            PersonalGoalCard turnPersonal=gameRules.getPersonalGoalCard(numbers.get(i));
            p.setPersonalGoalCard(turnPersonal);
            modelview.setPlayerPersonalGoal(turnPersonal,p.getNickname());
            Bookshelf bookshelf=new Bookshelf(rows,columns, maxSelectableTiles);
            p.setBookshelf(bookshelf);
            modelview.setBookshelfView(bookshelf.cloneBookshelf(),p.getNickname());
            commonGoalsSetup=new int[commonGoalCards.size()];
            modelview.getIdPersonal()[i]=numbers.get(i);
            //Arrays.setAll(commonGoalsSetup, num -> 0);
            Arrays.fill(commonGoalsSetup, 0);
            PlayerPointsView setupPoints=new PlayerPointsView(0,commonGoalsSetup,0, 0);
            modelview.setPlayerPoints(setupPoints,p.getNickname());
            i++;
        }
    }

    public Player getPlayerByNickname(String nickname) {
        for (Player p: players) {
            if (p.getNickname().equals(nickname)) return p;
        }
        return null;
    }


    public int updateAdjacentPoints(GameRules gameRules) throws Exception{
        int[] points= gameRules.getAdjacentArray();
        List<Integer> adjacent= turnBookshelf().findAdjacentTilesGroups();
        if(adjacent.isEmpty()){
            return 0;
        }
        int sum=0;
        for(int groupSize : adjacent){
            if (groupSize<2) continue;
            if((groupSize)>points.length){
                groupSize=points.length+1;
            }
            sum += points[groupSize-2];
        }
        getTurnPlayerOfTheGame().setAdjacentPoints(sum);
        return sum;
    }
    public int updatePointsCommonGoals(){
        int points=0;
        for (int i = 0; i< getTurnPlayerOfTheGame().getCommonGoalPoints().length; i++){
            if (getTurnPlayerOfTheGame().getCommonGoalPoints()[i]==0 && commonGoalCards.get(i).checkGoal(turnBookshelf().getMatrix())){
                int num=commonGoalCards.get(i).removeToken(getTurnPlayerOfTheGame().getNickname(),i);
                getTurnPlayerOfTheGame().setToken(i,num);
            }
            points=points+ getTurnPlayerOfTheGame().getCommonGoalPoints(i);
        }
        return points;
    }
    public int updatePersonalGoalPoints(GameRules gameRules) throws Exception {
        int[] points= gameRules.getPersonalGoalPoints();
        int numScored = 0;
        for (PersonalGoalBox box : turnPersonalGoal().getCells()){
            if (turnBookshelf().getMatrix()[box.getX()][box.getY()].getType() != null && turnBookshelf().getMatrix()[box.getX()][box.getY()].getType().equals(box.getTypePersonal())){
                numScored++;
            }
        }
        getTurnPlayerOfTheGame().setPersonalGoalPoints(points[numScored]);
        return getTurnPlayerOfTheGame().getPersonalGoalPoints();
    }

    public void updateAllPoints() throws Exception {
        GameRules gameRules=new GameRules();
        getTurnPlayerOfTheGame().setPlayerPoints(updateAdjacentPoints(gameRules)+updatePointsCommonGoals()+ updatePersonalGoalPoints(gameRules));
    }



    public PersonalGoalCard turnPersonalGoal(){return getTurnPlayerOfTheGame().getPersonalGoalCard();}

    public Bookshelf turnBookshelf(){return getTurnPlayerOfTheGame().getBookshelf();}

    public List<String> checkWinner() {
        List<String> ranking = Collections.unmodifiableList(
                players.stream()
                        .sorted(Comparator.comparingInt(Player::getPlayerPoints).reversed())
                        .map(Player::getNickname)
                        .toList());

        return ranking;
        //TODO: check exception error when using List instead of ArrayList
        //throw new RuntimeException();
    }
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
    public void setTurnPlayer(int num) {
        this.turnPlayer=num;
    }

    public ModelView getModelView() {
        return modelview;
    }


}