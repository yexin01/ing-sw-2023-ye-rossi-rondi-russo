package it.polimi.ingsw;


import it.polimi.ingsw.network.client.ClientMain;
import it.polimi.ingsw.network.server.Server;
import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.CLI.Colors;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;
import java.util.Scanner;

import java.util.Scanner;

/**
 *
 */
public class Start {
    public static void main(String[] args) throws Exception {
        int n = getInput("\nEnter: 0 for Server, 1 for Client >> ");
        if (n == 0) {
            Server.main(args);
        } else {
            ClientMain.main(args);
        }
    }

    public static int getInput(String message) {
        Scanner scanner = new Scanner(System.in);
        int n;
        while (true) {
            Colors.colorize(Colors.GAME_INSTRUCTION, message);
            String input = scanner.nextLine();

            if (input.equals("0") || input.equals("1")) {
                n = Integer.parseInt(input);
                break;
            } else {
                Colors.colorize(Colors.ERROR_MESSAGE, "\nInvalid input. Please enter either 0 or 1.\n");
            }
        }
        return n;
    }
}
