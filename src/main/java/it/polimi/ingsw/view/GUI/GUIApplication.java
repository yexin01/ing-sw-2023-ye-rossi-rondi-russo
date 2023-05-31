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
    private Scene sceneChoice;
    private Rectangle2D screenBounds;
    public static GUIApplication guiApplicationStatic;


    /**
     * Sets up the Gui
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.clientView = new ClientView();
        this.screenBounds = Screen.getPrimary().getVisualBounds();
        guiApplicationStatic = this;
    }

    /**
     * @return clientView
     */
    @Override
    public ClientView getClientView() {
        return clientView;
    }

    /**
     * Sets on stage the end turn panel
     */
    @Override
    public void waitingRoom() {
        Platform.runLater(()-> {
            EndTurnPanel endTurnPanel;
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

    /**
     * Shows a pop-up to notify to the user who won a token and
     * which token did they won
     * @param num : Token value
     * @param nickname : Nickname of the user who won the token
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

    /**
     * Sets on stage the panel where the user can choose the tiles from
     * the board and create the next scene (Tile(s) order)
     */
    @Override
    public void askCoordinates() {
        Platform.runLater(()-> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            ChoicePanel choicePanel;
            try {
                choicePanel = new ChoicePanel(clientView, screenBounds.getWidth()*0.28*(3.0 / Check.MAX_SELECTABLE_TILES));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //this.choicePanel = choicePanel;
            BoardBoxPanel boardBoxPanel;
            try {
                boardBoxPanel = new BoardBoxPanel(clientView, choicePanel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(boardBoxPanel, screenBounds.getWidth(), screenBounds.getHeight());
            Scene scene1 = new Scene(choicePanel, screenBounds.getWidth(), screenBounds.getHeight());
            this.sceneChoice = scene1;
            stage.setScene(scene);
            stage.show();
        });
    }

    /**
     * Sets on stage the previously created scene (Selected tile(s) order)
     */
    @Override
    public void askOrder() {
        Platform.runLater(()-> {
            stage.setScene(sceneChoice);
            stage.show();
        });
    }

    /**
     * Sets on stage the panel in which the user can decide the bookshelf
     * column to insert the selected tiles
     */
    @Override
    public void askColumn() {
        Platform.runLater(()-> {
            BookshelfPanel bookshelfPanel;
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

    /**
     * Shows a pop-up that notify the user about an error
     * @param error : Error description string
     */
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

    /**
     * Shows a pop-up that notify the user mostly about lobby infos
     * @param error : Message description string
     */
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

    /**
     * Sets on stage the panel in which the user can choose their
     * nickname and the preferred connection
     */
    @Override
    public void askNicknameAndConnection() {
        Platform.runLater(()-> {
            LobbyPanel lobbyPanel;
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

    /**
     * Sets on stage the panel in which the user can choose to create a game, or
     * join a specific/random lobby
     */
    @Override
    public void askLobbyDecision() {
        Platform.runLater(()-> {
            LobbyDecisionPanel lobbyDecisionPanel;
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

    /**
     * Sets on stage the panel that shows the final ranking, and gives the
     * possibility to the users to come back to the lobby
     * @param personalPoints : Personal goal points of all the users
     * @param playerBookshelfFull : first one to finish the bookshelf
     */
    @Override
    public void endGame(int[] personalPoints, String playerBookshelfFull) {
        Platform.runLater(()-> {
            FinalRankingPanel finalRankingPanel;
            try {
                finalRankingPanel = new FinalRankingPanel(clientView, personalPoints, playerBookshelfFull);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(finalRankingPanel, screenBounds.getWidth(), screenBounds.getHeight());
            stage.setScene(scene);
            stage.show();
        });
    }

    /**
     * Sets on stage the panel in which the user is informed to be the
     * last active player in the game
     */
    @Override
    public void onlyPlayer() {
        Platform.runLater(()-> {
            DisconnectionPanel disconnectionPanel;
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
