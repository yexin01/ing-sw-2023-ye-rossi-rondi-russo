package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PlayerController {

    private Game game;

    private ArrayList<BookshelfController> bookshelfControllers=new ArrayList<>();

    public PlayerController(Game game) {
        this.game=game;
    }

    public Game getGame() {
        return game;
    }
    public void firstPlayer() {
        game.setFirstPlayer(game.getPlayers().get(0));
        game.setTurnPlayer(game.getPlayers().get(0));
    }
   public boolean insertNickname(String nickname) {
        //IMPORTA il 4 dajason sarebbe numero di giocatori possibili meno uno
        if(true && !nickname.equals("stop")){//aggiunta la condizione che il nome deve essere diverso dgli altri
            Player player=new Player(nickname);
            game.getPlayers().add(player);
            game.setNumPlayers(game.getNumPlayers() + 1);
            bookshelfControllers.add(new BookshelfController(new Bookshelf()));
            if(game.getPlayers().size()==4)  return false;
            return true;
        }

        return false;
    }
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
    public int maxFreeShelves(){
        return 2;
    }
    //genera numbers numeri casuali diversi in un range prefissato da start a end
    //nel caso del gioco start=1;end=12, numbers=2
    //numbers indica il numero di carte obiettivo da generare
    private ArrayList<Integer> generateRandomNumber() {
        ArrayList<Integer> uniqueNumbers = new ArrayList<>();
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
    //crea il numero di common goal card prefissato
    public void createCommonGoalCard() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        ArrayList<Integer> numbers=new ArrayList<>();
        numbers=generateRandomNumber();

        //Importa da Jason
        ArrayList<Integer> points2Players = new ArrayList<>(Arrays.asList(4,8));
        ArrayList<Integer> points3Players = new ArrayList<>(Arrays.asList(4,6,8));
        ArrayList<Integer> points4Players = new ArrayList<>(Arrays.asList(2,4,6,8));



        Class<?>[] classArray = {CommonGoalCard1.class,CommonGoalCard2.class,CommonGoalCard3.class,CommonGoalCard3.class,
                CommonGoalCard4.class,CommonGoalCard5.class,CommonGoalCard6.class,CommonGoalCard7.class,
                CommonGoalCard8.class,CommonGoalCard9.class,CommonGoalCard10.class,CommonGoalCard11.class,CommonGoalCard12.class};
        //ArrayList<Class<?>> classArray=new ArrayList<>(Arrays.asList(CommonGoalCard1.class,CommonGoalCard2.class,CommonGoalCard3.class));
        game.setCommonGoalCards(new ArrayList<>());;

        for(Integer number: numbers){
            Class<?> classObj = classArray[number];
            Object obj = classObj.getDeclaredConstructor().newInstance();
            game.getCommonGoalCards().add((CommonGoalCard) obj);
        }

        setCommonGoalCardsPoints();


    }
    private void setCommonGoalCardsPoints() {
        ArrayList<Integer> points2Players = new ArrayList<>(Arrays.asList(4,8));
        ArrayList<Integer> points3Players = new ArrayList<>(Arrays.asList(4,6,8));
        ArrayList<Integer> points4Players = new ArrayList<>(Arrays.asList(2,4,6,8));

        ArrayList<Integer> points=new ArrayList<>();

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


}
