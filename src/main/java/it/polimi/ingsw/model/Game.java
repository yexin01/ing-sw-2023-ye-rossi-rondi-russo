package it.polimi.ingsw.model;


import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private int numPlayers;
    private ArrayList<Player> players;
    private CommonGoalCard[] commonGoalCards;

    public void addPlayer(Player player){
        players.add(player);
    }

    public void setnumPlayers(int x){
        numPlayers=x;
    }
    public int getnumPlayers(){
        return numPlayers;
    }

    public CommonGoalCard[] getCommonGoalCards() {
        return commonGoalCards;
    }

    private void setPlayers(){
        numPlayers=0;
        players=new ArrayList<>();
        Scanner input = new Scanner(System.in);
        String nome = "";
        //IMPORTA da jason
        int max=4;
        for(int i =0;i<max && !nome.equals("stop");i++){
            System.out.print("Inserisci un nome (scrivi stop per uscire): ");
            nome = input.nextLine();
            if (!nome.equals("stop")){ //inserire nella condizine la funzione che deve essere diverso dagli altri in and
                Player player=new Player();
                player.setNickname(nome);
                players.add(player);
                numPlayers++;
            }else i--;
        }
    }
    public void start(Board board){
        setPlayers();
        board.initialize();
        int matrice2gioc[][] = {
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        int matrice3gioc[][] ={
                {0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0}
        };
        int matrice4gioc[][] = {
                {0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0}
        };

        switch (numPlayers) {
            case 2:
                board.fill(matrice2gioc);
                break;
            case 3:
                board.fill(matrice3gioc);
                break;
            case 4:
                board.fill(matrice4gioc);
                break;
            default:
                board.fill(matrice2gioc);
                break;
        }

    }
    public void play(Board board){
        boolean endgame=false;
        do{
            for (Player p : players) {
                if(p.playTurn(board))
                    endgame=true;
                if(board.checkRefill()){
                    board.refill();
                }
            }
        }while(!endgame);
        System.out.print("FINE PARTITA CALCOLARE TUTTI I PUNTEGGI: ");
    }
}

