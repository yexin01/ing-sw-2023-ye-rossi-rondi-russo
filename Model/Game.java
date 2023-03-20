package org.example;

public class Game {
    public static int numplayers;
    public Player player[];
    public commonGoal[]=new CommonGoalCard[2];



    public boolean refillNeeded(){
        /*
        prima controlla che tutte le tessere siano isolate(freeEdge==4)
        se ciò non avviene ritorna falso,
        altrimenti 
        1) aggiorna i contatori di bag
        2)chiama fill di board
        3)ritorna vero
         */

        return false;
    }

    public boolean uniqueNickname(){
        return false;
    }
    public void startGame(){
        /*
        
        crea i due obiettivi comuni...
        fill la board
         */
    }
    /*
    public void createCommonoGoalCards(){
        /*
        estraendo a caso due numeri da 1 a 12,
        Common_goal_card esempiox1=new Common_goal_cardx1;
        staticamente common_goal_card dinamicamente common_goal_cardx1
         */
    }
    */
    public Player nextPlayer(){}
    
    
    public void playTurn(Player player){
        /*
        chiama refill_board
        chiama player.play()
        se ritorna true si chiama end game
         */
    }

    public void end_game(Player player){
        /*
        termina il giro e incrementa di uno il punteggio del
        giocatore che ha vinto chiamando
        player.end_point();
         */
    }


}
