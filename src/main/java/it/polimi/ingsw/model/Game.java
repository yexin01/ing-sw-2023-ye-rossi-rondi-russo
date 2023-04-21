package it.polimi.ingsw.model;


import it.polimi.ingsw.Client;
import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.listeners.*;

import java.util.*;

public class Game {

    private ArrayList<Player> players;
    private Board board;
    private int numPlayers;
    private int turnPlayer;
    private ArrayList<CommonGoalCard> commonGoalCards;
    private boolean endGame;


    public Game(){
        players=new ArrayList<>();
        commonGoalCards=new ArrayList<>();
        board=new Board();

    }
      //PLAYERS


    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
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

    public Player getLastPlayer() {
        return players.get(players.size()-1);
    }



    public Player getTurnPlayer() {
        return players.get(turnPlayer);
    }

    public void setNextPlayer() {
        if(turnPlayer == (players.size() - 1))
            turnPlayer=0;
        else turnPlayer++;
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

/*
    public void addPlayer(String nickname) throws Exception {
        if (players.size() < numPlayers) {
            Player p = new Player(nickname);
            p.addListener(EventType.BOARD_SELECTION, new BoardListener());
            p.addListener(EventType.BOOKSHELF_INSERTION, new BookshelfListener());
            p.addListener(EventType.POINTS, new PointsListener());
            players.add(p);
        }
    }

 */




    public void addPlayers(String nickname,HashMap<String,Client> playerMap) throws Exception {
        if (players.size() < 3) {
            Player p = new Player(nickname);
            p.addListener(EventType.BOARD_SELECTION,new BoardListener(playerMap) );
            //p.addListener(EventType.BOOKSHELF_INSERTION_AND_POINTS, new BookshelfListener(playerMap));
            p.addListener(EventType.BOOKSHELF_INSERTION_AND_POINTS, new PointsListener(playerMap));
            players.add(p);
        }
    }





    /*

    public void addPlayer(String nickname) throws Exception {
        if (players.size() < numPlayers) {
            Player p=new Player(nickname);
            PlayerListener listener=new PlayerListener();
            p.addListener("BoardSelection",new BoardListener());
            p.addListener("BookshelfInsertion",new BookshelfListener());
            p.addListener("Points",new PointsListener());
            players.add(p);
        }
    }

     */

    /**
     *
     * @param nickname
     * @return
     */
    //TODO insertNickname it depends on how we implement the controller in the future it could change
    public boolean insertNickname(String nickname) throws Exception {
        GameRules playersJson = new GameRules();
        int numMaxPlayer = playersJson.getMaxPlayers();
        if(!nickname.equals("stop") && players.size() < numMaxPlayer) {
            if(differentNickname(nickname)) {
                Player player = new Player(nickname);
                players.add(player);
                setNumPlayers(numPlayers + 1);
                if(players.size() == numMaxPlayer) {
                    System.out.println("START THE GAME with " + players.size() + " players");
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Invalid name already used");
            }
        } else {
            System.out.println("START THE GAME with " + players.size() + " players");
            return false;
        }
        return true;
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
        for (Integer number : numbers) {
            String className = gameRules.getCommonGoalCard(number);
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();
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
        ArrayList<Integer> points=gameRules.getCommonGoalPoints(numPlayers);
        for(CommonGoalCard c: commonGoalCards){
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
        ArrayList<Integer> numbers = generateRandomNumber(gameRules.getPossiblePersonalGoalsSize(), numPlayers);
        int rows= gameRules.getRowsBookshelf();
        int columns= gameRules.getColumnsBookshelf();
        int maxSelectableTiles=gameRules.getMaxSelectableTiles();
        int i = 0;
        for (Player p : players) {
            p.setPersonalGoalCard(gameRules.getPersonalGoalCard(numbers.get(i)));
            p.setBookshelf(new Bookshelf(rows,columns, maxSelectableTiles));
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
        getTurnPlayer().setAdjacentPoints(sum);
        return sum;
    }
    public int updatePointsCommonGoals(){
        int points=0;
        for (int i=0;i<getTurnPlayer().getCommonGoalPoints().length;i++){
            if (getTurnPlayer().getCommonGoalPoints()[i]==0 && commonGoalCards.get(i).checkGoal(turnBookshelf().getMatrix())){
                int num=commonGoalCards.get(i).removeToken(getTurnPlayer().getNickname());
                getTurnPlayer().setToken(i,num);
            }
            points=points+getTurnPlayer().getCommonGoalPoints(i);
        }
        return points;
    }
    public int updatePersonalGoalPoints(GameRules gameRules) throws Exception {
        int[] points= gameRules.getPersonalGoalPoints();
        int numScored = 0;
        for (PersonalGoalBox box : turnPersonalGoal().getCells()){
            if (turnBookshelf().getMatrix()[box.getX()][box.getY()].getType() != null && turnBookshelf().getMatrix()[box.getX()][box.getY()].getType().equals(box.getType())){
                numScored++;
            }
        }
        getTurnPlayer().setPersonalGoalPoints(points[numScored]);
        return getTurnPlayer().getPersonalGoalPoints();
    }

    public void updateAllPoints() throws Exception {
        GameRules gameRules=new GameRules();
        getTurnPlayer().setPlayerPoints(updateAdjacentPoints(gameRules)+updatePointsCommonGoals()+ updatePersonalGoalPoints(gameRules));
    }



    public PersonalGoalCard turnPersonalGoal(){return getTurnPlayer().getPersonalGoalCard();}

    public Bookshelf turnBookshelf(){return getTurnPlayer().getBookshelf();}

    public List<Player> checkWinner() {
        //controlla i punteggi dei giocatori e li ordina dal primo all'ultimo
        List<Player> scoreBoard = players.stream().sorted(Comparator.comparingInt(Player::getPlayerPoints).reversed()).toList();
        return scoreBoard;
        //TODO: check exception error when using List instead of ArrayList
        //throw new RuntimeException();
    }

    public void endGame() {
        List<Player> ranking=checkWinner();
        //TODO END LISTENER
        //listeners.firePropertyChange(new PropertyChangeEvent(this, "EndGame", null, ranking));
    }


    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }


}