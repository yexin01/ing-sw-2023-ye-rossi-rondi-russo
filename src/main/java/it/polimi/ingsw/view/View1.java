package it.polimi.ingsw.view;
import it.polimi.ingsw.model.*;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
public class View1 extends Observable implements  Observer {
/*
    @Override
    public synchronized void run() {
        System.out.println("INIZIO partita inserimento giocatori");
        int max = 4;
        int i = 0;
        String nickname;
        do{
            nickname = askPlayerNickname();
            setChanged();
            notifyObservers(nickname);
            i++;
        } while(i < max && !nickname.equals("Stop"));

    }
*/

    public void setPlayers() {
        System.out.println("INIZIO partita inserimento giocatori");
        //IMPORTA DA JSON max giocatori
        int max = 4;
        int i = 0;
        String nickname;
        do{
            nickname = askPlayerNickname();
            setChanged();
            notifyObservers(nickname);
            i++;
        } while(i < max && !nickname.equals("Stop"));

    }

    public String askPlayerNickname() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("Inserisci il nome scrivi stop per uscire");
        String input=scanner.next();
        setChanged();
        notifyObservers(input);
        return input;
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Board){
            if(arg instanceof Boolean){
                askPlayerNickname();
            }
        }

    }
}