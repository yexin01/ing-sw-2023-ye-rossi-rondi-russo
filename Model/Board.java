package org.example;

public class Board {
    public Board_box matrix[][]= new Board_box[9][9];

    public int position_selected[]=new int[6];
    public void initialize(){

    }
    public void fill(){

    }

    public boolean selectable_1(int[] selezionate){
        /*
        inizializza position_selected a null
        Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge==true

        Se é selezionabile
        1)inserisce le coordinate in position_selected in
        posizione 0 e 1

         2) ritorna true
        */

    }
    public boolean selectable_2(int[] selezionate){
        /*
        1)Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge==true
        2) Controlla che o la riga o la colonna siano uguali
        3)  Se é selezionabile inserisce le coordinate
        in position_selected in
        posizione 2 e 3
        4) il flag occupata e ritorna true
        */

    }
    public boolean selectable_3(int[] selezionate){
        /*
        1)Controlla che almeno un bordo della casella sia libero
        matrix[selezionate[0]][selezionate[1]].freeEdge==true
        2) Controlla che o le righe o le colonne di tutte tre le tessere
        siano uguali(posizioni pari o dispari di position selected)
        3)  Se é selezionabile inserisce le coordinate
        in position_selected in
        posizione 4 e 5
        4  ritorna true
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
    public void final_check_board(int[] selezionate){
        /*
        Resetta a 0 il flag di quelle selezionate
        riesegue un controllo per i bordi liberi di quelle vicine a quelle selezionate
        */
    }


}
