package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.listeners.EventListener;
import it.polimi.ingsw.listeners.EventType;
import it.polimi.ingsw.listeners.ListenerManager;
import it.polimi.ingsw.messages.*;

import java.util.Scanner;

//TODO this class will changed, it defines how to handle message from Server once received
public class Client {
    private ListenerManager listenerManager;
    private final String nickname;

    private Scanner scanner;
    public void addListener(EventType eventType, EventListener listener) {
        this.listenerManager.addListener(eventType,listener);
    }

    public void removeListener(EventType eventType, EventListener listener) {
        this.listenerManager.removeListener(eventType, listener);
    }

    public Client(String nickname) {
        this.nickname = nickname;
        //this.nickname = nickname;
        this.scanner=new Scanner(System.in);
        this.listenerManager=new ListenerManager();

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
