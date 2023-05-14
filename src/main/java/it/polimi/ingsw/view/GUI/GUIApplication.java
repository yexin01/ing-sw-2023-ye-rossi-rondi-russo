package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIApplication extends Application implements ClientInterface {
    private Stage stage;
    private ClientView clientView;
    private ChoicePanel choicePanel;
    private int column;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.clientView = new ClientView();
        //ClientMain.semaphore.release();
    }



    @Override
    public ClientView getClientView() {
        return clientView;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void stop() throws Exception {

    }

    @Override
    public void askCoordinates() throws Exception {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ChoicePanel choicePanel = new ChoicePanel(clientView, screenBounds.getWidth()*0.28);
        this.choicePanel = choicePanel;
        BoardBoxPanel boardBoxPanel = new BoardBoxPanel(clientView, choicePanel);
        Scene scene = new Scene(boardBoxPanel);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void askOrder() throws Exception {
        Scene scene = new Scene(choicePanel);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void askColumn() throws Exception {
        BookshelfPanel bookshelfPanel = new BookshelfPanel(clientView);
        Scene scene = new Scene(bookshelfPanel);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void displayError(String error) {
        DisconnectionPanel disconnectionPanel = new DisconnectionPanel();
        Scene scene = new Scene(disconnectionPanel);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void displayMessage(String error) {

    }

    @Override
    public void askNicknameAndConnection() throws Exception {
        LobbyPanel lobbyPanel = new LobbyPanel(clientView);
        Scene scene = new Scene(lobbyPanel);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void askLobbyDecision() throws Exception {
    }

    @Override
    public boolean endGame() {
        return false;
    }

    @Override
    public void Setup() {

    }
}
