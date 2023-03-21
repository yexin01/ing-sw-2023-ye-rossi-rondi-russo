package org.example;

public class Game {
    private int numPlayers;



    public void setnumPlayers(int x){
        numPlayers=x;
    }
    public int getnumPlayers(){
        return numPlayers;
    }



    //private Player player[];

    //public Common_goal_card common_goal[]=new Common_goal_card[2];


/*
    public boolean refill(){
        /*
        prima controlla che tutte le tessere siano isolate(freeEdge==4)
        se ciò non avviene ritorna falso,
        altrimenti chiama fill di board   e ritorna vero


        return false;
    }

    public boolean unique_nickname(){
        return false;
    }
    public void start_game(){
    }
    public void create_public_goals(){
        /*
        estraendo a caso due numeri da 1 a 12,
        Common_goal_card esempiox1=new Common_goal_cardx1;
        staticamente common_goal_card dinamicamente common_goal_cardx1

    }
    public void play_turn(Player player){
        /*
        chiama refill_board
        chiama player.play_turn_player()
        se ritorna false si passa al prossimo giocatore
        altrimenti si chiama end game

    }

    public void end_game(Player player){
        /*
        termina il giro e incrementa di uno il punteggio del
        giocatore che ha vinto
        player.end_point();

    }

*/
}
