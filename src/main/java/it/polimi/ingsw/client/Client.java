package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.*;

import java.util.Scanner;

//TODO this class will changed, it defines how to handle message from Server once received
public class Client {

    private final String nickname;

    private Scanner scanner;


    public Client(String nickname) {
        this.nickname = nickname;
        //this.nickname = nickname;
        this.scanner=new Scanner(System.in);
        //this.listenerManager=new ListenerManager(serverView);

    }


    public int ask(){
        int i=1;
        for(DataClientType element : DataClientType.values()) {
            System.out.print(i+" "+element+"  ");
            i++;
        }
        System.out.println("");
        //System.err.println(gameController.getTurnController().getCurrentPhase()+" controller phase: ");
        int num = scanner.nextInt();
        return num;

    }
    public int number(){
        return scanner.nextInt();
    }


    public String getNickname() {
        return nickname;
    }


}
