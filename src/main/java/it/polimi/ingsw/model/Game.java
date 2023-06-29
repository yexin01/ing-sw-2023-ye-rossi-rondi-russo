package it.polimi.ingsw.model;


import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.modelView.ModelView;
import it.polimi.ingsw.model.modelView.PlayerPointsView;

import java.util.*;
/**
 * The Game class represents a game instance.
 */
public class Game {
    private ArrayList<Player> players;
    private final ModelView modelview;
    private Board board;

    private ArrayList<CommonGoalCard> commonGoalCards;
    private boolean endGame;


    /**
     * Initializes a new Game instance using the provided ModelView.
     * Creates an empty list for common goal cards, assigns the given ModelView to the game's modelview field,
     * and initializes a new Board instance based on the provided ModelView.
     * @param modelview The ModelView object representing the game state.
     */

    public Game(ModelView modelview){
        commonGoalCards=new ArrayList<>();
        this.modelview=modelview;
        board=new Board(modelview);
    }

      //PLAYERS
    /**
     * Returns the list of players in the game.
     * @return The list of players.
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    /**
     * Sets the list of players in the game.
     * @param players The list of players to set.
     */
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * @return nickname of the last connected player
     */
    public String getLastPlayer() {
        Boolean[] activePlayers= modelview.getActivePlayers();
        int i;
        for(i=players.size()-1; !activePlayers[i];i--){
        }
        return players.get(i).getNickname();
    }
    /**
     * Returns the player whose turn it is in the game.
     * @return The player whose turn it is.
     */
    public Player getTurnPlayerOfTheGame() {
        return players.get(modelview.getTurnPlayer());
    }


    //CURRENT BOARD

    /**
     * Returns the board of the game.
     * @return The game board.
     */
    public Board getBoard() {
        return board;
    }
    /**
     * Sets the board of the game.
     * @param board The game board to set.
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    //COMMON GOAL CARDS
    /**
     * Returns the list of common goal cards in the game.
     * @return The list of common goal cards.
     */
    public ArrayList<CommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }
    /**
     * Sets the list of common goal cards in the game.
     * @param commonGoalCards The list of common goal cards to set.
     */
    public void setCommonGoalCards(ArrayList<CommonGoalCard> commonGoalCards) {
        this.commonGoalCards = commonGoalCards;
    }

    /**
     * Adds players to the game with randomly determined play order.
     * @param nicknames An ArrayList of player nicknames.
     * @throws Exception If the file is not found or there is an error reading the file.
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
     * Generates a specified number of unique random numbers within a given range.
     * @param range The range of numbers from which to generate random numbers.
     * @param numOfGenerated The number of unique random numbers to generate.
     * @return An ArrayList containing the generated unique random numbers.
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
     * Reads from the JSON file the number of objects to instantiate (numOfCommonGoals) and the number of possible entities
     * to instantiate (numOfPossibleCommonGoalsCards). Using an array of classes, it instantiates only the subclasses of
     * CommonGoalCard at the positions specified by the numbers in the ArrayList.
     * @param gameRules The game rules containing the mapping of numbers to class names.
     * @param numbers The ArrayList of numbers indicating the positions of subclasses to instantiate.
     * @throws Exception If the file is not found or there is an error reading the file.
     */
    public void createCommonGoalCard(GameRules gameRules,ArrayList<Integer> numbers ) throws Exception {
        setCommonGoalCards(new ArrayList<CommonGoalCard>());
        int i=0;
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
     * Sets the scores for the CommonGoalCards based on the number of players in the game. The scores are read from the JSON file.
     * @param gameRules The game rules containing the scores for the CommonGoalCards.
     * @throws Exception If the file is not found or there is an error reading the file.
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


    /**
     * Instantiates the personal goal cards for each player based on the specified positions in the numbers list.
     * Additionally, it sets up the reference bookshelf and common goal points for each player.
     * @param gameRules The game rules containing the personal goal card information.
     * @param numbers The list of positions indicating which personal goal cards to instantiate for each player.
     */
    public void createPersonalGoalCard(GameRules gameRules, ArrayList<Integer> numbers ) {

        int rows= gameRules.getRowsBookshelf();
        int columns= gameRules.getColumnsBookshelf();
        int i = 0;
        for (Player p : players) {
            p.setCommonGoalPoints(new int[commonGoalCards.size()]);
            PlayerPointsView setupPoints=new PlayerPointsView(p.getCommonGoalPoints(),0,p.getNickname());
            modelview.setPlayerPoints(setupPoints,i);

            PersonalGoalCard turnPersonal=gameRules.getPersonalGoalCard(numbers.get(i));
            p.setPersonalGoalCard(turnPersonal);
            modelview.setPlayerPersonalGoal(turnPersonal,p.getNickname());

            Bookshelf bookshelf=new Bookshelf();
            bookshelf.matrix(rows,columns);
            p.setBookshelf(bookshelf);
            modelview.setBookshelfView(bookshelf.cloneBookshelf(),p.getNickname());
            i++;
        }
    }



    /**
     * Updates the adjacent points of the current player by reading the associated scores from the JSON file.
     * @param gameRules The game rules containing the scores for adjacent tile groups.
     */
    public void updateAdjacentPoints(GameRules gameRules) {
        int[] points= gameRules.getAdjacentArray();
        List<Integer> adjacent= getTurnPlayerOfTheGame().getBookshelf().findAdjacentTilesGroups();
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
     * Updates the common goal points of the current player and updates the modelView if a token is reached.
     */

    public void updatePointsCommonGoals(){
        for (int i = 0; i< getTurnPlayerOfTheGame().getCommonGoalPoints().length; i++){
            CommonGoalCard c=commonGoalCards.get(i);
            if (getTurnPlayerOfTheGame().getCommonGoalPoints()[i]==0 && commonGoalCards.get(i).checkGoal(getTurnPlayerOfTheGame().getBookshelf().getMatrix())){
                int pointsWon=commonGoalCards.get(i).removeToken();
                modelview.getToken()[i]=pointsWon;
                modelview.setIdCommon(1,i,c.getLastPoint());
                getTurnPlayerOfTheGame().setToken(i,pointsWon);
            }
        }
    }

    /**
     * Updates the personal goal points of the current player based on the associated score read from the JSON file.
     * @param gameRules The game rules containing the scores for personal goals.
     */
     public void updatePersonalGoalPoints(GameRules gameRules) {
        int[] points= gameRules.getPersonalGoalPoints();
        int numScored = 0;
        for (PersonalGoalBox box : getTurnPlayerOfTheGame().getPersonalGoalCard().getCells()){
            if (getTurnPlayerOfTheGame().getBookshelf().getMatrix()[box.getX()][box.getY()].getType() != null && getTurnPlayerOfTheGame().getBookshelf().getMatrix()[box.getX()][box.getY()].getType().equals(box.getTypePersonal())){
                numScored++;
            }
        }
        getTurnPlayerOfTheGame().setPersonalGoalPoints(points[numScored]);
    }

    /**
     * Updates all the points of the current player and sets the updated scores in the modelView.
     * @throws Exception If the file is not found or there is an error reading the file.
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

    /**
     * Retrieves the value of the endGame flag.
     * @return The value of the endGame flag.
     */
    public boolean isEndGame() {
        return endGame;
    }
    /**
     * Sets the end game status.
     * @param endGame true if a player has completed their bookshelf, indicating the end of the game; false otherwise.
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * Retrieves the modelView associated with the game.
     * @return The ModelView object representing the game state.
     */
    public ModelView getModelView() {
        return modelview;
    }


}