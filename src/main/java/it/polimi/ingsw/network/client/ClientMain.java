package it.polimi.ingsw.network.client;

import it.polimi.ingsw.Start;
import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.CLI.Colors;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Main class of the client that starts the client application and asks the user if he wants to play with CLI or GUI.
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        int n = Start.getInput("\nEnter: 0 for CLI, 1 for GUI  >> ");
        if (n == 0) {
            new CLI().askNicknameAndConnection();
        } else {
            GUIApplication guiApplication = new GUIApplication();
            Platform.startup(() -> {
                try {
                    guiApplication.start(new Stage());
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {
                    guiApplication.askNicknameAndConnection();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
