package org.example;

public class PersonalGoalCard {
    private int[] catPosition=new int[2];
    private int[] bookPosition=new int[2];
    private int[] gamePosition=new int[2];
    private int[] framePosition=new int[2];
    private int[] trophiePosition=new int[2];
    private int[] plantPosition=new int[2];
    private boolean[] alreadyScored=new boolean[6];

    public int checkPoints(Bookshelf bookshelf,Player player){
        
        /*
        controlla prima che il giocatore non abbia già raggiungo l'obiettivo medianto un controllo su already scored
        
        Per quelli non ancora raggiunti 
        
        Esempio con catposition:
        bookshelf.matrix[catPosition[0]][catPosition[1]].Item_tile.type deve essere uguale a CATS 
        
        se raggiunge l'obiettivo incrementa il punteggio del giocatore in base a quante ne ha fatte giuste
         */
        
        return 0;
    }


}
