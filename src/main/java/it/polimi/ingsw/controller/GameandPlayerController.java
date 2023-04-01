package it.polimi.ingsw.controller;

import it.polimi.ingsw.json.GameRules;
import it.polimi.ingsw.model.*;

import java.util.*;

public class GameandPlayerController {

    private Game game;


    public GameandPlayerController(Game game) {
        this.game=game;
    }

    public Game getGame() {
        return game;
    }

    /**
     * sets turnPlayer and firstPlayer to the first player
     */
    public void firstPlayer() {
        game.setFirstPlayer(game.getPlayers().get(0));
        game.setTurnPlayer(game.getPlayers().get(0));
    }

    /**
     *
     * @param nickname
     * @return
     */
    //TODO insertNickname it depends on how we implement the controller in the future it could change
    public boolean insertNickname(String nickname) throws Exception {
        GameRules playersJson = new GameRules();
        int numMaxPlayer = playersJson.getMaxPlayers();
        if(!nickname.equals("stop") && game.getPlayers().size() < numMaxPlayer) {
            if(differentNickname(nickname)) {
                Player player = new Player(nickname);
                game.getPlayers().add(player);
                game.setNumPlayers(game.getNumPlayers() + 1);
                if(game.getPlayers().size() == numMaxPlayer) {
                    System.out.println("START THE GAME with " + game.getPlayers().size() + " players");
                    return false;
                }
            } else {
                throw new IllegalArgumentException("Invalid name already used");
            }
        } else {
            System.out.println("START THE GAME with " + game.getPlayers().size() + " players");
            return false;
        }
        return true;
    }
    public boolean differentNickname(String nickname) {
        if (game.getPlayers().isEmpty()) {
            return true;
        } else {
            Set<String> usedNames = new HashSet<>();
            for (Player p : game.getPlayers()) {
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
     * change the player to the next one
     * @param player
     */
    public void setNextPlayer(Player player){
        for(int i=0;i<game.getPlayers().size();i++){
            if(game.getPlayers().get(i).getNickname().equals(player.getNickname())){
                if(i<game.getPlayers().size()-1){
                    game.setTurnPlayer(game.getPlayers().get(i+1));
                }
                else {
                    game.setTurnPlayer(game.getPlayers().get(0));
                }
            }

        }
    }

    /**
     *generates different random numbers in a fixed range and a number of times numOfgenerated
     * @param range
     * @param numOfGenerated
     * @return
     */
    private ArrayList<Integer> generateRandomNumber(int range, int numOfGenerated) {
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
    public void createCommonGoalCard(GameRules gameRules) throws Exception {

        int numOfCommonGoals = gameRules.getNumOfCommonGoals();
        int numOfPossibleCommonGoalsCards = gameRules.getCommonGoalCardsSize();
        ArrayList<Integer> numbers = generateRandomNumber(numOfPossibleCommonGoalsCards, numOfCommonGoals);

        game.setCommonGoalCards(new ArrayList<CommonGoalCard>());
        for (Integer number : numbers) {
            String className = gameRules.getCommonGoalCard(number);
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.getDeclaredConstructor().newInstance();
            game.getCommonGoalCards().add((CommonGoalCard) obj);
        }

        setCommonGoalCardsPoints(gameRules);
    }

       /*

        Class<?> clazz = Class.forName(className);
        for (int i = 0; i < commonGoals; i++) {
            classArray[i] = Class.forName(classNames.get(i));
        }



        Class<?>[] classArray = new Class<?>[commonGoals];
        for (int i = 0; i < commonGoals; i++) {
            classArray[i] = Class.forName(classNames.get(i));
        }

        game.setCommonGoalCards(new ArrayList<CommonGoalCard>());

        for (Integer number : numbers) {
            Class<?> classObj = classArray[number];
            Object obj = classObj.getDeclaredConstructor().newInstance();
            game.getCommonGoalCards().add((CommonGoalCard) obj);
        }

        */




    /**
     * Match arraylist of scores based on number of players
     */
    private void setCommonGoalCardsPoints(GameRules gameRules) throws Exception {
        //TODO importing from json
        ArrayList<Integer> points=gameRules.getCommonGoalPoints(game.getNumPlayers());
        for(CommonGoalCard c: game.getCommonGoalCards()){
            c.setPoints(points);
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
    public boolean insertBookshelf(int column) {
        try {
            if (column < 0 || column >= turnBookshelf().getMatrix().length-1) {
                throw new IllegalArgumentException("Column value must be between 0 and " + (turnBookshelf().getMatrix()[0].length - 1));
            }
            if (turnBookshelf().getMaxTilesColumn(column) < selectedTiles().size()) {
                return false;
            }
            if (selectedTiles().size() <= turnBookshelf().getMaxTilesColumn(column)) {
                int j = 0;
                for (int i = turnBookshelf().getMatrix().length - 1; j < selectedTiles().size(); i--) {
                    if (turnBookshelf().getMatrix()[i][column].getTileID() == -1) {
                        turnBookshelf().setTile(selectedTiles().get(j++), i, column);
                        System.out.println("Inserted tile of type " + turnBookshelf().getTileType(i, column) + " in column " + column + ", row " + i);
                    }
                }
                return true;
            }
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid column value: " + e.getMessage());
        }
        return false;
    }
 */

    public boolean insertAsSelected(int column) {
        if (checkBookshelf(column)) {
                int j = 0;
                for (int i = turnBookshelf().getMatrix().length - 1; j < selectedTiles().size(); i--) {
                    if (turnBookshelf().getMatrix()[i][column].getTileID() == -1) {
                        turnBookshelf().setTile(selectedTiles().get(j++), i, column);
                        System.out.println("Inserted tile of type " + turnBookshelf().getTileType(i, column) + " in column " + column + ", row " + i);
                    }
                }
                return true;
            }
        return false;
    }
    public boolean checkBookshelf(int column){
        try {
            if (column < -1 || column > turnBookshelf().getMatrix()[0].length-1) {
                throw new IllegalArgumentException(" value must be between 0 and  "+(turnBookshelf().getMatrix()[0].length-1));
            }
            if (selectedTiles().size() <= turnBookshelf().getMaxTilesColumn(column)) return true;
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid bookshelf column:Rewrite the column" + e.getMessage());
            return false;
        }

        return false;
    }

    public void insertOnceATime(int numberTile,int column) {

            for (int i = turnBookshelf().getMatrix().length - 1; i>=0; i--) {
                if (turnBookshelf().getMatrix()[i][column].getTileID() == -1) {
                    turnBookshelf().setTile(selectedTiles().get(numberTile), i, column);
                    break;
                  }
            }
        selectedTiles().remove(numberTile);

        }



    public ArrayList<ItemTile> selectedTiles(){return game.getTurnPlayer().getSelectedItems();}


    //TODO common goal Card function
  /*  public void pointsCommonGoal(){
        for(CommonGoalCard c:game.getCommonGoalCards()){
            c.checkGoal(turnBookshelf());
        }


    }

   */
    public Bookshelf turnBookshelf(){return game.getTurnPlayer().getBookshelf();}

    /**
     *instantiates personalGoalCard based on the number of players
     */

    /*
    public void createPersonalGoalCard(GameRules gameRules) {
        ArrayList<Integer> numbers = generateRandomNumber(gameRules.getPossiblePersonalGoalsSize(), game.getNumPlayers());
        int rows= gameRules.getRowsBookshelf();
        int columns= gameRules.getColumnsBookshelf();
        int maxSelectableTiles=gameRules.getMaxSelectableTiles();
        int i = 0;
        for (Player p : game.getPlayers()) {
            p.setPersonalGoalCard(new PersonalGoalCard(gameRules.getPersonalGoalCardCoordinates(numbers.get(i)), gameRules.getPersonalGoalCardTypes(numbers.get(i))));
            p.setBookshelf(new Bookshelf(rows,columns, maxSelectableTiles));
            i++;
        }
    }

     */
    public void createPersonalGoalCard(GameRules gameRules) {
        ArrayList<Integer> numbers = generateRandomNumber(gameRules.getPossiblePersonalGoalsSize(), game.getNumPlayers());
        int rows= gameRules.getRowsBookshelf();
        int columns= gameRules.getColumnsBookshelf();
        int maxSelectableTiles=gameRules.getMaxSelectableTiles();
        int i = 0;
        for (Player p : game.getPlayers()) {
            p.setPersonalGoalCard(gameRules.getPersonalGoalCard(numbers.get(i)));
            p.setBookshelf(new Bookshelf(rows,columns, maxSelectableTiles));
            i++;
        }
    }


}