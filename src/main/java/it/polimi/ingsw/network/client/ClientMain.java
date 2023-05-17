package it.polimi.ingsw.network.client;

import it.polimi.ingsw.view.CLI.CLI;
import it.polimi.ingsw.view.GUI.GUIApplication;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.Scanner;

/**
 * Main class of the client that starts the client application and asks the user if he wants to play with CLI or GUI
 * and then asks the nickname and the connection type (socket or RMI) to the user
 * and starts the client application with the chosen type of connection and the chosen nickname
 */
public class ClientMain {
    public static void main(String[] args) throws Exception {
        //
        Scanner scanner = new Scanner(System.in);

        System.out.print("CLI 0 GUI 1: ");
        int n = scanner.nextInt();
        scanner.nextLine();
        if (n==0) {
            new CLI().askNicknameAndConnection();
        }/* else {
            GUIApplication guiApplication=new GUIApplication();
            Platform.startup(()->{
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
        */

    }
}
