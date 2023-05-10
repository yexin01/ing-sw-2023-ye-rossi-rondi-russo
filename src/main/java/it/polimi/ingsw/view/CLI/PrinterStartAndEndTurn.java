package it.polimi.ingsw.view.CLI;

import it.polimi.ingsw.messages.MessageFromServer;
import it.polimi.ingsw.view.ClientView;

import java.util.Scanner;

public class PrinterStartAndEndTurn {


    public void initialLobby(){
        Colors.colorize(Colors.GAME_INSTRUCTION,"Insert nickname port.... ");
        //TODO verra aggiunto l'inserimento della porta...e il nickname
        System.out.print("PROVA: ");
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print("Inserisci un numero: ");
            int num = input.nextInt();

            if (num == 1) {
                break;
            }
        }
        /*
//TODO creare client socket con la porta ...
        ClientSocket socket=new ClientSocket("username", "ip", 3, "token",this);
        socket.setHandlerUpdater(new HandlerUpdater(socket,this));
        socket.startConnection();

         */

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
