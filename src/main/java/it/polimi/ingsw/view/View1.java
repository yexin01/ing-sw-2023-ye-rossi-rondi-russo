package it.polimi.ingsw.view;
import it.polimi.ingsw.model.*;

import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;
public class View1 extends Observable implements  Observer {


    public void setPlayers() {
        System.out.println("Start game write the name of the first player");
        //TODO import from json numMaxPlayers
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
        System.out.println("Enter the name write stop when you have finished entering the players \nIf there are 4 players the game will start automatically");
        String input=scanner.next();
        setChanged();
        notifyObservers(input);
        return input;
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Game){
            if(arg instanceof Boolean){
                askPlayerNickname();
            }
        }

    }
}