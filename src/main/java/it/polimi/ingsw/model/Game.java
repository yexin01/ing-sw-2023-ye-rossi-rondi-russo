package it.polimi.ingsw.model;



import it.polimi.ingsw.controller.Controller;

import java.lang.reflect.InvocationTargetException;
import java.util.*;


public class Game extends Observable {
    private final Controller controller = new Controller();


    public int getNumPlayers() {
        return controller.getNumPlayers();
    }

    public void setNumPlayers(int numPlayers) {
        this.controller.setNumPlayers(numPlayers);
    }

    public ArrayList<Player> getPlayers() {
        return controller.getPlayers();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.controller.setPlayers(players);
    }

    public boolean isFinish() {
        return controller.isFinish();
    }
    public void setFinishopposite() {
        this.controller.setFinish(!this.controller.isFinish());
        setChanged();
        notifyObservers(controller.isFinish());
    }
    public void setFinish(boolean finish) {
        this.controller.setFinish(finish);
        setChanged();
        notifyObservers(finish);
    }


    public Game() {
    }

    public Player getTurnPlayer() {
        return controller.getTurnPlayer();
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.controller.setTurnPlayer(turnPlayer);
    }

    public Player getFirstPlayer() {
        return controller.getFirstPlayer();
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.controller.setFirstPlayer(firstPlayer);
    }

    //genera numbers numeri casuali diversi in un range prefissato da start a end
    //nel caso del gioco start=1;end=12, numbers=2
    //numbers indica il numero di carte obiettivo da generare
    private ArrayList<Integer> generateRandomNumber(int possibleNumbers,int numTime) {
        //importa i valori da jason

        return controller.generateRandomNumber(possibleNumbers, numTime);
    }
    //crea il numero di common goal card prefissato
    public void createCommonGoalCard() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //Importa da Jason


        //ArrayList<Class<?>> classArray=new ArrayList<>(Arrays.asList(CommonGoalCard1.class,CommonGoalCard2.class,CommonGoalCard3.class));


        controller.createCommonGoalCard();
    }

    private void setPointsCommonGoalCards() {

        controller.setPointsCommonGoalCards();
    }

    //verrà spostata all'interno del Player controller
    public void createPersonalGoalCard(){
        //Importarle da jason e inserirle tutte e 12

        //cambiare i valori associandogli le dodici tessere del gioco

        controller.createPersonalGoalCard();
    }
}