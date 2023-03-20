package org.example;

public class Player {
    public String nickname;
    private int playerPoints;
    private Bookshelf bookshelf;
    private PersonalGoalCard personalGoalcard;
    private boolean ScoringToken1;
    private boolean ScoringToken2;
    //ALTERNATIVA utilizzare un arraylist che ogni volta viene cancellato
    private selectedItems[]= new ItemTile[3];

/*
    public void column(){
        /*
        controlla quali colonne possono essere selezionate dal'utente
        confrontando il numero di celle libere che deve essere
        maggiore di 3 oppure maggiore del numero di tessere selezionate
        ritornando il puntatore ad un array che contiene le colonne selezionabili
        numerate a partire da 1 da sinistra
         
    }
    */
    /*
    public void takeToken1(){
        


    }
    public void takeToken2(){
        /*
        uguale a taketoken1 
         


    }
*/
    public void updatePlayerPoints(){
       /*
      
       
        somma le varie tipologie di ottenimento punti:
       a) obiettivi comuni
        Per entrambi gli obiettivi comuni:
        1)prima controlla che l'obiettivo non sia già stato raggiuto,
        tramite l'attributo booleano ScoringTokenX
        2)chiama metodo check_goal() dell'obiettivo
        comune e se l'obiettivo viene raggiunto aggiorna il punteggio del giocatore
        il punteggio da aggiungere viene ritornato dalla funzione game.CommonGoal[0].changePoints()
   
    b) obiettivo personale
    personalGoal.checkPoints();
    c) carte adiacenti
    bookshelf.computeAdjacent();

    */
    }

    public void insert(int column){
        /*
        
       1)Controlla che la colonna selezionata dall'utente sia corretta
       altrimenti richiede all'utente la collona
       lo Esegu
       2) chiede all'utente la prima tessera da inserire in bookshelf
       inserisce la tessera bookshelf.matrix[[freeShelves[column]--][column-1]
       aggiornando direttamente il contatore
       
        
        
        
        
        ordinato=new item...[3];
       while(! bookshelf.checkColumn(colonna)){};
       chiedi all'utente quale vuole selezionare =x
       bookshelf.insert(colonna,selected[x])
       
       
       
       
        e cambia l'ordine in selected_items
        while(!)
        bookshelf.insert(column,selected I..[]);
        
        
        
        
        
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


    public void pick(){
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
        5) chiama board.update_board 

         */


    }
    public boolean play(){
        /*
        chiama:pick,bookshelf.insert,update_player_points,takeToken1/2 ,
        e ritorna bookshelf.shelf_full()

         */
        return false;
    }
/*
    public void end_point(){
        playerPoints++;
    }

*/


}
