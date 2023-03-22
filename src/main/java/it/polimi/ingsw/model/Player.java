package it.polimi.ingsw.model;


import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private ArrayList<ItemTile> selectedItems;
    private String nickname;

    public String getNickname(){
        return nickname;
    }
    public void setNickname(String nickname){
        this.nickname=nickname;
    }

    public boolean playTurn(Board board){
        //chiama la funzione di player che ricava il max di
        //selezionabili
        int x=3;
        int num1;
        int num2;
        Scanner scanner = new Scanner(System.in);
        for(int i=0;i<x;i++){
            do{
                System.out.print("Enter first number: ");
                num1 = scanner.nextInt();
                System.out.print("Enter second number: ");
                num2 = scanner.nextInt();
            } while(!board.isSelectable(board.getBoardBox(num1,num2),i));
        }
        selectedItems=new ArrayList<ItemTile>();
        selectedItems=board.Selected();

        //aggiorna punteggi .... ritorna un booleano vero se la bookshelf é piena
        return false;
    }




}
