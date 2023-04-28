package it.polimi.ingsw.network.client;

import javafx.application.Application;
import view.cli.Cli;
import view.gui.Gui;

/**
 * This class is the main class of the client, it starts the client in CLI or GUI mode depending on the arguments passed to it at runtime (CLI or GUI)
 */

//TODO
public class ClientMain {
    public static void main(String[] args) {
        if (args.length > 0 && args[0].equalsIgnoreCase("CLI")) {
            new Cli().start();
        } else {
            Application.launch(Gui.class);
        }
    }
}