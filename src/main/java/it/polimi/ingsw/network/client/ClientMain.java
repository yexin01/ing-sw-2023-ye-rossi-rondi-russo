package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.CLI.CLI;

public class ClientMain {
    public static void main(String[] args) throws Exception {
        new Cli().start();
        //CLI c=new CLI();
        //new ClientHandler(c);
        //c.askNicknameAndConnection();
    }
}
