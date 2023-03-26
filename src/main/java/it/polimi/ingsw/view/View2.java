package it.polimi.ingsw.view;
import it.polimi.ingsw.model.*;


import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

public class View2 extends Observable implements Observer {

    public View2(){
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
        if(o instanceof Board board){
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
