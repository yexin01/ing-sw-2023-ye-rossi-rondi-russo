package it.polimi.ingsw.view.GUI;
import it.polimi.ingsw.App;
import it.polimi.ingsw.controller.TurnPhase;
import it.polimi.ingsw.message.Message;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class GUIApplication extends Application implements ClientInterface {
    private Stage stage;
    private ClientView clientView;
    private ChoicePanel choicePanel;
    private int column;
    Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.clientView = new ClientView();
    }

    @Override
    public TurnPhase getTurnPhase() {
        return null;
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
