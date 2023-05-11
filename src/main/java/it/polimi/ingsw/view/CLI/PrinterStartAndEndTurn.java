package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.view.ClientView;

import java.io.PrintStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import static java.lang.System.out;

public class PrinterStartAndEndTurn extends ClientHandler {
    private final Scanner in;
    private static PrintStream out;
    public PrinterStartAndEndTurn(){
       super();

       this.in = new Scanner(System.in);
       this.out = new PrintStream(System.out,true);
    }


    public void endGame(ClientView clientView){
        //TODO stampare la classifica e nel caso aggiugo altri dati che servono nel messaggio



    }
    public void finishTurn(){
        //TODO magari stampare un disegno di fine turno che puo vedere il giocatore in attesa, comunque lascio la possibilita all utente
        //TODO di guardare le regole, la bookshelf....
    }
    public void rulesGame(){
        //TODO stampere tutte le regole del gioco quindi i punteggi delle Personal, regole di selezione delle tessere...
    }




}
