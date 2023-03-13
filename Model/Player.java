package org.example;

public class Player {
    public String nickname;
    private int playerPoints;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalcard;
    private boolean commonGoal1Token;
    private boolean commonGoal2Token;
    private Item_tile selected_items[]= new Item_tile[3];


    public void column(){
        /*
        controlla quali colonne possono essere selezionate dal'utente
        confrontando il numero di celle libere che deve essere
        maggiore di 3 oppure meggiore del numero di tessere selezionate
        ritornando il puntatore ad un array che contiene le colonne selezionabili
        numerate a partire da 1 da sinistra
         */
    }
    public void takeToken(){
        /*
        1)prima controlla che gli obiettivi non siano già stati raggiunti,
        tramite i due attributi booleani
        2)chiama metodo check_goal() dei due obiettivi
        comuni e se l'obiettivo viene raggiunto aggiorna il punteggio ritornato
        se ritorna -1 significa che l'obiettivo non é stato raggiunto
         */


    }

    public void update_player_points(){
       /*
    somma le varie tipologie di ottenimento punti:

    1) obiettivo personale
    personalGoal.checkPoints();
    2) carte adiacenti
    bookshelf.computeAdjacent();

    i punti dell'obiettivo comuni vengono aggiornati dal metodo take token
    */
    }

    public void insert(){
        /*
        controlla che la colonna selezionata dall'utente sia selezionabile
        mediante il metodo colums, le inserisce a partire dall'ultima cella
        libera chiedendo all'utente l'ordine
         e aggiornando il numero di celle libere nella colonna
         */
    }


    public void pick(Board board){
        /*
        int[] coordinate=new int[2];
        inizializzare selected_items a null
        1) chiedere all'utente di selezionare una tessera
             memorizza in un array le due coordinate
         board.selectable_1(coordinate[0],coordinate[1])
         se ritorna true chiede all'utente se vuole selezionare un'altra carta
           se true inserisce la tessera in selected_items e va al punto 2
           se ritorna false  riesegue il punto 1
         2)chiede di selezionare una tessera inserisce nell'array le coordinate
         board.selectable_3(coordinate[0],coordinate[1])..
        3)...board.selectable_3(coordinate[0],coordinate[1])..
        4) chiama board.check_final_board


         */


    }
    public boolean play_turn_player(){
        /*
        chiama:pick,insert,update_player_points,takeToken ,
        e ritorna bookshelf.shelf_full()

         */
        return false;
    }

    public void end_point(){
        playerPoints++;
    }




}
