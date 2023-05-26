package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

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
    public void waitingRoom() throws Exception {
        Platform.runLater(()-> {
            EndTurnPanel endTurnPanel = null;
            try {
                endTurnPanel = new EndTurnPanel(clientView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(endTurnPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    /*@Override
    public void start() throws Exception {
        Platform.runLater(()-> {
            EndTurnPanel endTurnPanel = new EndTurnPanel(clientView);
            Scene scene = new Scene(endTurnPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

     */

    @Override
    public void displayToken(int num, String nickname) {
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ATTENTION");
            alert.setHeaderText("Token won");
            //Image image = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\scoring tokens\\scoring_"+num+".jpg");
            Image image;
            try {
                image = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("scoring_"+num+".jpg")).openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            VBox content = new VBox(10);
            content.getChildren().addAll(imageView, new Label(nickname+" won "+num+" points token"));
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setContent(content);
            alert.show();
        });
    }


    @Override
    public void stop() throws Exception {

    }

    @Override
    public void askCoordinates() throws Exception {
        Platform.runLater(()-> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            ChoicePanel choicePanel = null;
            try {
                choicePanel = new ChoicePanel(clientView, screenBounds.getWidth()*0.28*(3.0 / Check.MAX_SELECTABLE_TILES));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            this.choicePanel = choicePanel;
            BoardBoxPanel boardBoxPanel = null;
            try {
                boardBoxPanel = new BoardBoxPanel(clientView, choicePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(boardBoxPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void askOrder() throws Exception {
        Platform.runLater(()-> {
            Scene scene = new Scene(choicePanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void askColumn() throws Exception {
        Platform.runLater(()-> {
            BookshelfPanel bookshelfPanel = null;
            try {
                bookshelfPanel = new BookshelfPanel(clientView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(bookshelfPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void displayError(String error) {
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Attention");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.show();
        });
    }

    @Override
    public void displayMessage(String error) {
        Platform.runLater(()-> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.show();
        });
    }

    @Override
    public void askNicknameAndConnection() throws Exception {
        Platform.runLater(()-> {
            LobbyPanel lobbyPanel = null;
            try {
                lobbyPanel = new LobbyPanel(clientView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(lobbyPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void askLobbyDecision() throws Exception {
        Platform.runLater(()-> {
            LobbyDecisionPanel lobbyDecisionPanel = null;
            try {
                lobbyDecisionPanel = new LobbyDecisionPanel(clientView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(lobbyDecisionPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    @Override
    public void endGame(int[] personalPoints) {
        Platform.runLater(()-> {
            FinalRankingPanel finalRankingPanel = null;
            try {
                finalRankingPanel = new FinalRankingPanel(clientView, personalPoints);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(finalRankingPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("FINAL RANKING");
            alert.setHeaderText("WINNER");
            Image image;
            try {
                image = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("end game.jpg")).openStream());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(200);
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            VBox content = new VBox(10);
            content.getChildren().addAll(imageView, new Label("The winner is: "+clientView.getPlayerPointsViews()[0].getNickname()));
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setContent(content);
            alert.show();
        });
    }

    @Override
    public void onlyPlayer() {
        Platform.runLater(()-> {
            DisconnectionPanel disconnectionPanel = null;
            try {
                disconnectionPanel = new DisconnectionPanel();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(disconnectionPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }
}
