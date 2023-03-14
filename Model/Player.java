package org.example;

public class Player {
    public String nickname;
    private int playerPoints;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalcard;
    private boolean commonGoal1Token;
    private boolean commonGoal2Token;
    //ALTERNATIVA utilizzare un arraylist che ogni volta viene cancellato
    private Item_tile selected_items[]= new Item_tile[3];


    public void column(){
        /*
        controlla quali colonne possono essere selezionate dal'utente
        confrontando il numero di celle libere che deve essere
        maggiore di 3 oppure maggiore del numero di tessere selezionate
        ritornando il puntatore ad un array che contiene le colonne selezionabili
        numerate a partire da 1 da sinistra
         */
    }
    public void takeToken1(){
        /*
        1)prima controlla che l'obiettivo non sia già stato raggiunto,
        tramite l'attributo booleano
        2)chiama metodo check_goal() del'obiettivo
        comune 1 e se l'obiettivo viene raggiunto aggiorna il punteggio del giocatore
         con il valore ritornato
        se ritorna -1 significa che l'obiettivo non é stato raggiunto
         */


    }
    public void takeToken2(){
        /*
        uguale a taketoken1 
         */


    }

    public void update_player_points(){
       /*
    somma le varie tipologie di ottenimento punti:

    1) obiettivo personale
    personalGoal.checkPoints();
    2) carte adiacenti
    bookshelf.computeAdjacent();

    i punti dell'obiettivo comuni vengono aggiornati dal metodo taketoken 1/2
    */
    }

    public void insert(){
        /*
        DA RIGUARDARE
        controlla che la colonna selezionata dall'utente sia selezionabile
        mediante il metodo column,

        Questo potrebbe farlo il metodo insert_column di bookshelf
        le inserisce a partire dall'ultima cella
        libera chiedendo all'utente l'ordine

         e aggiornando il numero di celle libere nella colonna(decrementandoil contatore del numero di celle scelte),
         occupa la cella
         bookshelf.matrix[x][y].changeOccupied()
         */
    }


    public void pick(Board board){
        /*
        
        int[] coordinate=new int[2];
        inizializzare selected_items a null
        in base al numero di celle libere indica all'utente il numero max di celle
         selezionabili
        1) chiedere all'utente di selezionare una tessera
             memorizza in un array le due coordinate della board
         board.selectable_1(coordinate[0],coordinate[1])
         2)se il metodo ritorna true e il numero di tessere selezionabili dall'utente é >1 
         e l'utente vuole selezionare un'altra carta 
         chiama possible_selectable_tile1(coordinate[0],coordinate[1])
           e inserisce la tessera 1 in selected_items
           se ritorna false  riesegue il punto 1
         3).. inserisce nell'array le coordinate
         board.selectable_2(coordinate[0],coordinate[1])..
         ..possible_selectable_tile2(coordinate[0],coordinate[1])..
        4)...board.selectable_3(coordinate[0],coordinate[1])..
        5) chiama board.check_final_board 

         */


    }
    public boolean play_turn_player(){
        /*
        chiama:pick,insert,update_player_points,takeToken1/2 ,
        e ritorna bookshelf.shelf_full()

         */
        return false;
    }

    public void end_point(){
        playerPoints++;
    }




}
