package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
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
    public boolean insertNickname(String nickname) throws IllegalArgumentException {
        //TODO importing MAXnumPlayers from json
        int numMaxPlayer=4;
        if(!nickname.equals("stop") && game.getPlayers().size()<4){
            if(differentNickname(nickname)){
                Bookshelf bookshelf=new Bookshelf();
                Player player=new Player(nickname,bookshelf);
                game.getPlayers().add(player);
                game.setNumPlayers(game.getNumPlayers() + 1);
                if(game.getPlayers().size()==4){
                    System.out.println("START THE GAME with "+game.getPlayers().size()+" players");
                    return false;
                }
            }else{
                throw new IllegalArgumentException("Invalid name already used");
            }
        }else {
            System.out.println("START THE GAME with "+game.getPlayers().size()+" players");
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
        //importa i valori da jason
        int possibleCommonGoals=12;
        int numbersCommonGoal=2;

        while (uniqueNumbers.size() < numbersCommonGoal) {
            int newNumber = random.nextInt(possibleCommonGoals);
            if (!uniqueNumbers.contains(newNumber)) {
                uniqueNumbers.add(newNumber);
            }
        }
        return uniqueNumbers;
    }

    /**
     *instantiate CommonGoals
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public void createCommonGoalCard() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        //TODO importing from json numofCommonGoals and numCommonGoals
        int numOfCommonGoals=2;
        int CommonGoals=12;
        ArrayList<Integer> numbers=new ArrayList<Integer>();
        numbers=generateRandomNumber(CommonGoals,numOfCommonGoals);
        //TODO importing Arraylist from json
        Class<?>[] classArray = {CommonGoalCard1.class,CommonGoalCard2.class,CommonGoalCard3.class,CommonGoalCard3.class,
                CommonGoalCard4.class,CommonGoalCard5.class,CommonGoalCard6.class,CommonGoalCard7.class,
                CommonGoalCard8.class,CommonGoalCard9.class,CommonGoalCard10.class,CommonGoalCard11.class,CommonGoalCard12.class};

        game.setCommonGoalCards(new ArrayList<CommonGoalCard>());;

        for(Integer number: numbers){
            Class<?> classObj = classArray[number];
            Object obj = classObj.getDeclaredConstructor().newInstance();
            game.getCommonGoalCards().add((CommonGoalCard) obj);
        }

        setCommonGoalCardsPoints();


    }

    /**
     * Match arraylist of scores based on number of players
     */
    private void setCommonGoalCardsPoints() {
        //TODO importing from json
        ArrayList<Integer> points2Players = new ArrayList<Integer>(Arrays.asList(4,8));
        ArrayList<Integer> points3Players = new ArrayList<Integer>(Arrays.asList(4,6,8));
        ArrayList<Integer> points4Players = new ArrayList<Integer>(Arrays.asList(2,4,6,8));

        ArrayList<Integer> points=new ArrayList<Integer>();
        //TODO change switch importing from json,basing the choice on the number of players
        //TODO alternatively set a default matrix for more than 4 players
        switch(game.getNumPlayers()){
            case 2:
                points=points2Players;
                break;
            case 3:
                points=points3Players;
                break;
            case 4:
                points=points4Players;
                break;
            default:System.err.println("Something wrong");
                break;
        }

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

    public boolean insertBookshelf(int column) {
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

        return false;
    }
    public ArrayList<ItemTile> selectedTiles(){return game.getTurnPlayer().getSelectedItems();}


    public Bookshelf turnBookshelf(){return game.getTurnPlayer().getBookshelf();}

    /**
     *instantiates personalGoalCard based on the number of players
     */
    public void createPersonalGoalCard(){

        ArrayList<Integer> numbers=new ArrayList<Integer>();
        //TODO personalGoalCards importing from json and insert all
        //TODO change numPersonalGoal range: 12 (importing from json)
        int numPersonalGoal=4;
        numbers=generateRandomNumber(4,game.getNumPlayers());


        ArrayList<ArrayList<Integer>> personalGoals = new ArrayList<>(List.of(new ArrayList<>(List.of(0,3,4,1,1,2)),new ArrayList<>(List.of(1,3,4,2)),
                new ArrayList<>(List.of(2,3,1,4,3,2)),new ArrayList<>(List.of(1,2))));
        ArrayList<ArrayList<Type>> types=new ArrayList<>(List.of(new ArrayList<>(List.of(Type.CAT,Type.FRAME,Type.BOOK)),
                new ArrayList<>(List.of(Type.FRAME,Type.FRAME)),new ArrayList<>(List.of(Type.TROPHY,Type.FRAME,Type.BOOK)),
                new ArrayList<>(List.of(Type.PLANT))));

        int i=0;
        for(Player p:game.getPlayers()){
            p.getPersonalGoalCard().add(new PersonalGoalCard(personalGoals.get(numbers.get(i)),types.get(numbers.get(i++))));
        }
    }
}