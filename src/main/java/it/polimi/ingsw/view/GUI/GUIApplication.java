package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class GUIApplication extends Application implements ClientInterface {
    private Stage stage;
    private ClientView clientView;
    private ChoicePanel choicePanel;
    private Rectangle2D screenBounds;
    public static GUIApplication guiApplicationStatic;


    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.clientView = new ClientView();
        this.screenBounds = Screen.getPrimary().getVisualBounds();
        guiApplicationStatic = this;
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
        Platform.runLater(()-> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            ChoicePanel choicePanel = new ChoicePanel(clientView, screenBounds.getWidth()*0.28);
            this.choicePanel = choicePanel;
            BoardBoxPanel boardBoxPanel = new BoardBoxPanel(clientView, choicePanel);
            Scene scene = new Scene(boardBoxPanel);
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void askOrder() throws Exception {
        Platform.runLater(()-> {
            Scene scene = new Scene(choicePanel);
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void askColumn() throws Exception {
        Platform.runLater(()-> {
            BookshelfPanel bookshelfPanel = new BookshelfPanel(clientView);
            Scene scene = new Scene(bookshelfPanel);
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void displayError(String error) {
        Platform.runLater(()-> {
            DisconnectionPanel disconnectionPanel = new DisconnectionPanel();
            Scene scene = new Scene(disconnectionPanel);
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void displayMessage(String error) {

    }

    @Override
    public void askNicknameAndConnection() throws Exception {
        Platform.runLater(()-> {
            LobbyPanel lobbyPanel = new LobbyPanel(clientView);
            Scene scene = new Scene(lobbyPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void askLobbyDecision() throws Exception {
        Platform.runLater(()-> {
            LobbyDecisionPanel lobbyDecisionPanel = new LobbyDecisionPanel(clientView);
            Scene scene = new Scene(lobbyDecisionPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public boolean endGame() {
        return false;
    }

    @Override
    public void Setup() {

    }
}
