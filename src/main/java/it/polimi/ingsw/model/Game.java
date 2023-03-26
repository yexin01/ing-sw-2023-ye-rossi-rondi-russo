package it.polimi.ingsw.model;



import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.Random;


public class Game extends Observable {
    private int numPlayers;
    private Player turnPlayer;

    private Player firstPlayer;


    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    private ArrayList<Player> players=new ArrayList<Player>();

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    private boolean finish;

    public boolean isFinish() {
        return finish;
    }
    public void setFinishopposite() {
        this.finish = !this.finish;
        setChanged();
        notifyObservers(finish);
    }
    public void setFinish(boolean finish) {
        this.finish = finish;
        setChanged();
        notifyObservers(finish);
    }


    public Game() {
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    private ArrayList<CommonGoalCard> CommonGoalCards;

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
        System.out.println(uniqueNumbers);
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
                CommonGoalCard4.class,CommonGoalCard5.class,CommonGoalCard6.class,CommonGoalCard7.class,CommonGoalCard8.class,
                CommonGoalCard9.class,CommonGoalCard10.class,CommonGoalCard11.class,CommonGoalCard12.class};
       this.CommonGoalCards= new ArrayList<>();;

        for(Integer number: numbers){
            Class<?> classObj = classArray[number];
            Object obj = classObj.getDeclaredConstructor().newInstance();
            CommonGoalCards.add((CommonGoalCard) obj);
        }

        setPointsCommonGoalCards();


    }

    private void setPointsCommonGoalCards() {
        ArrayList<Integer> points2Players = new ArrayList<>(Arrays.asList(4,8));
        ArrayList<Integer> points3Players = new ArrayList<>(Arrays.asList(4,6,8));
        ArrayList<Integer> points4Players = new ArrayList<>(Arrays.asList(2,4,6,8));

        ArrayList<Integer> points=new ArrayList<>();

        switch(numPlayers){
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

        for(CommonGoalCard c: CommonGoalCards){
            c.setPoints(points);
        }
    }
}