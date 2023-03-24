package it.polimi.ingsw.view;

import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View2 extends Observable implements Observer {

    public View2(){
    }



    public synchronized void turn() {
        System.out.println("Start turn");
        // while(true){
        System.out.println("Inserisci primo");
        Integer row= askPlayer();
        setChanged();
        notifyObservers(row);
        System.out.println("Inserisci secondo");
        Integer column=askPlayer();
        setChanged();
        notifyObservers(column);
        System.out.println("Inserisci numero tessera");
        Integer ntessera=askPlayer();
        setChanged();
        notifyObservers(ntessera);
        // }
    }

    public Integer askPlayer(){
        int input;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number: ");
        input = scanner.nextInt();
        setChanged();
        notifyObservers(input);
        return input;
    }
    @Override
    public void update(Observable o, Object arg) {
        if(o instanceof Game game){
            if(arg instanceof Boolean){
                askPlayer();
            }
            if(o instanceof Player p){
                if(arg instanceof ItemTile){
                    System.out.println("Le tessere scelte sono corrette");
                    printTiles(p);
                }
            }
        }
    }
    private void printTiles(Player p) {
        ArrayList<ItemTile> selectedTiles = p.getSelectedItems();
        if(selectedTiles==null){
            return;
        }
        for(ItemTile s: selectedTiles){
            System.out.println(s.getType()+" "+s.getValue());
        }
    }



}