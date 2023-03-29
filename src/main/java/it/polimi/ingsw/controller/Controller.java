package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Controller {
    public int numPlayers;

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public Player turnPlayer;

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public Player firstPlayer;

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public ArrayList<Player> players = new ArrayList<Player>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public boolean finish;

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public ArrayList<CommonGoalCard> CommonGoalCards;

    public Controller() {
    }//genera numbers numeri casuali diversi in un range prefissato da start a end

    //nel caso del gioco start=1;end=12, numbers=2
    //numbers indica il numero di carte obiettivo da generare
    public ArrayList<Integer> generateRandomNumber(int possibleNumbers, int numTime) {
        ArrayList<Integer> uniqueNumbers = new ArrayList<Integer>();
        Random random = new Random();
        //importa i valori da jason

        while (uniqueNumbers.size() < numTime) {
            int newNumber = random.nextInt(possibleNumbers);
            if (!uniqueNumbers.contains(newNumber)) {
                uniqueNumbers.add(newNumber);
            }
        }
        System.out.println(uniqueNumbers);
        return uniqueNumbers;
    }//crea il numero di common goal card prefissato

    public void createCommonGoalCard() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        ArrayList<Integer> numbers = new ArrayList<Integer>();
        numbers = generateRandomNumber(7, 2);

        //Importa da Jason
        ArrayList<Integer> points2Players = new ArrayList<Integer>(Arrays.asList(4, 8));
        ArrayList<Integer> points3Players = new ArrayList<Integer>(Arrays.asList(4, 6, 8));
        ArrayList<Integer> points4Players = new ArrayList<Integer>(Arrays.asList(2, 4, 6, 8));


        Class<?>[] classArray = {CommonGoalCard1.class, CommonGoalCard2.class, CommonGoalCard3.class, CommonGoalCard3.class,
                CommonGoalCard4.class, CommonGoalCard5.class, CommonGoalCard6.class, CommonGoalCard7.class};
        //ArrayList<Class<?>> classArray=new ArrayList<>(Arrays.asList(CommonGoalCard1.class,CommonGoalCard2.class,CommonGoalCard3.class));
        this.CommonGoalCards = new ArrayList<CommonGoalCard>();
        ;

        for (Integer number : numbers) {
            Class<?> classObj = classArray[number];
            Object obj = classObj.getDeclaredConstructor().newInstance();
            CommonGoalCards.add((CommonGoalCard) obj);
        }

        setPointsCommonGoalCards();


    }

    public void setPointsCommonGoalCards() {
        ArrayList<Integer> points2Players = new ArrayList<Integer>(Arrays.asList(4, 8));
        ArrayList<Integer> points3Players = new ArrayList<Integer>(Arrays.asList(4, 6, 8));
        ArrayList<Integer> points4Players = new ArrayList<Integer>(Arrays.asList(2, 4, 6, 8));

        ArrayList<Integer> points = new ArrayList<Integer>();

        switch (numPlayers) {
            case 2:
                points = points2Players;
                break;
            case 3:
                points = points3Players;
                break;
            case 4:
                points = points4Players;
                break;
            default:
                System.err.println("Something wrong");
                break;
        }

        for (CommonGoalCard c : CommonGoalCards) {
            c.setPoints(points);
        }
    }//verrà spostata all'interno del Player controller

    public void createPersonalGoalCard() {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        //Importarle da jason e inserirle tutte e 12
        numbers = generateRandomNumber(4, numPlayers);

        //cambiare i valori associandogli le dodici tessere del gioco
        ArrayList<ArrayList<Integer>> personalGoals = new ArrayList<ArrayList<Integer>>(List.of(new ArrayList<Integer>(List.of(0, 3, 4, 1, 1, 2)), new ArrayList<Integer>(List.of(1, 3, 4, 2)),
                new ArrayList<Integer>(List.of(2, 3, 1, 4, 3, 2)), new ArrayList<Integer>(List.of(1, 2))));
        ArrayList<ArrayList<Type>> types = new ArrayList<ArrayList<Type>>(List.of(new ArrayList<Type>(List.of(Type.CAT, Type.FRAME, Type.BOOK)),
                new ArrayList<Type>(List.of(Type.FRAME, Type.FRAME)), new ArrayList<Type>(List.of(Type.TROPHY, Type.FRAME, Type.BOOK)),
                new ArrayList<Type>(List.of(Type.PLANT))));

        int i = 0;
        for (Player p : players) {
            p.setPersonalGoalCard(new PersonalGoalCard(personalGoals.get(numbers.get(i)), types.get(numbers.get(i++))));
        }
    }
}