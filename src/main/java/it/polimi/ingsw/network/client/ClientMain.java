package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.CLI.CLI;

import java.io.IOException;

/**
 * This class is the main class of the client, it starts the client in CLI or GUI mode depending on the arguments passed to it at runtime (CLI or GUI)
 */

//TODO
public class ClientMain {
    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
            new CLI().start();
        } else {
            //Application.launch(Gui.class);
        }
    }
}


