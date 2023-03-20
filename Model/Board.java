package org.example;

public class Board {
    public matrix[][]= new Board_box[9][9];
    public BoardBox[] selectedPosition;
    public selectedPosition[]=new BoardBox[3];
    public selectable[]=new BoardBox[4];
    
    
    
    public void initialize(){

    }
    public void fill(){
        

    }

    public boolean isSelectable1(BoardBox tile1){
        /*
        inizializza position_selected a null
        1) Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge>0
        2) se >0 restituisce true e inserisce le coordinate in position_selected
        nelle posizioni 0 e 1
            
            altrimenti restituisce false
       
        */

    }
    public boolean isSelectable2(BoardBox tile2){
        /*
        1)Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge>0
        2) Controlla che o la riga o la colonna siano uguali alla prima
            x==position_selected[0] or y==position_selected[1]
        3)  Se é selezionabile inserisce le coordinate
        in position_selected in
        posizione 2 e 3
       4) se é selezionabile ritorna true
       
       chiama possible_selectable_tile1(position_selected[0]+1,position_selected[0]+1)
        */

    }
    public boolean isSelectable3(BoardBox tile3){
        /*
        1)Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge>0
        2) Controlla che o le righe o le colonne di tutte tre le tessere
        siano uguali(posizioni pari o dispari di position selected)
        3)  Se é selezionabile inserisce le coordinate
        in position_selected in
        posizione 4 e 5
        4)  ritorna true
        chiama possible_selectable_tile2(selezionate[0],selezionate[1])
        */

    }
  /*  
    public int[] possible_selectable_tile1(int x, int y){
        /*
        Controlla nelle 4 direzioni della prima tessera:
        le tessera deve avere 
        matrix[selezionate[0]][selezionate[1]].freeEdge>0
        
        1)Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge>0
        2) Controlla che o la riga o la colonna siano uguali alla prima
            x==position_selected[0] or y==position_selected[1]
        3)  Se é selezionabile inserisce le coordinate
        in position_selected in
        posizione 2 e 3
       4) se é selezionabile ritorna true
       chiama possible_selectable_tile1(selezionate[0],selezionate[1])
        
        il metodo ritorna il puntatore ad un array che contiene le coordinate
        delle carte selezionabili, verifica in tutte 4 le direzioni della tessera e se 
        la tessera é occupata e presenta almeno un bordo libero inserisce le coordinate nel vettore ritornato
        
    }
    public int[] possible_selectable_tile2(int x, int y){
        /*

         */   
        /*
        il metodo ritorna il puntatore ad un array che contiene le coordinate
        delle carte selezionabili, varifica se le prime due carte si trovano sulla stessa
        riga o sulla stessa colonna, di conseguenza verifica se l'unica carta selezionabile
        ha il bordo libero ed é occupata da una tessera
         
    }
*/
    public void updateBoard(){
        /*
        Resetta a 0 il flag di quelle selezionate
        riesegue un controllo per i bordi liberi di quelle vicine a quelle selezionate
        */
    }

    /* ALTERNATIVA a selectable 1/2/3
    //in selezionate sono presenti le coordinate delle tessere scelte
    public boolean selectable(int[] selezionate){

        Controlla che le carte selezionate dal giocatore siano
        selezionabili:
            Si devono trovare sulla stessa riga o colonna
            controllare o che le posizioni pari dell'array siano uguali
            o che le posizioni dispare siano uguali
            devono avere tutte un bordo libero

            (matrix[selezionate[0]][selezionate[1]].freeEdge==true
            matrix[selezionate[2]][selezionate[3]].freeEdge==true)
            ... il numero di carte selezionate dipende dalla lunghezza
            del vettore selezionate

    }
    */



}
