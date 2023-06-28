package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.message.KeyLobbyPayload;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LobbyDecisionPanel extends BasePanel{

    private String numPlayers;
    private String id;
        public LobbyDecisionPanel(ClientView clientView) throws IOException {
            setBackground(new Background(getLobbyBackground()));

            getChildren().addAll(getTitle(), getPublisher());
            setAlignment(getChildren().get(0), Pos.TOP_CENTER);
            setAlignment(getChildren().get(1), Pos.BOTTOM_RIGHT);

            TextField textField = new TextField("Insert players number(2-4)");
            textField.setOnMouseClicked(mouseEvent -> textField.selectAll());
            textField.setPrefSize(400, 35);
            Button confirmButton = new Button("Create new Lobby");
            Button confirmButton1 = new Button("Join specific lobby");
            Button random = new Button("Join random game");
            Button quit = new Button("Quit server");
            confirmButton.setOnAction(actionEvent -> {
                numPlayers = textField.getText();
                if (numPlayers.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("No number founded");
                    alert.setContentText("Please insert a number");
                    alert.show();
                } else {
                    try {
                        clientView.lobby(KeyLobbyPayload.CREATE_GAME_LOBBY, Integer.parseInt(numPlayers));
                        confirmButton.setDisable(true);
                        confirmButton1.setDisable(true);
                        random.setDisable(true);
                        quit.setDisable(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            HBox hBox = new HBox(textField, confirmButton);

            TextField textField1 = new TextField("Insert ID Lobby");
            textField1.setOnMouseClicked(mouseEvent -> textField1.selectAll());
            textField.setPrefSize(400, 35);
            confirmButton1.setOnAction(actionEvent -> {
                id = textField1.getText();
                if (id.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("No id lobby founded");
                    alert.setContentText("Please insert an id lobby");
                    alert.show();
                } else {
                    try {
                        clientView.lobby(KeyLobbyPayload.JOIN_SPECIFIC_GAME_LOBBY, Integer.parseInt(id));
                        confirmButton.setDisable(true);
                        confirmButton1.setDisable(true);
                        random.setDisable(true);
                        quit.setDisable(true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            HBox hBox1 = new HBox(textField1, confirmButton1);

            random.setOnMouseClicked(mouseEvent -> {
                try {
                    clientView.lobby(KeyLobbyPayload.JOIN_RANDOM_GAME_LOBBY,-1);
                    confirmButton.setDisable(true);
                    confirmButton1.setDisable(true);
                    random.setDisable(true);
                    quit.setDisable(true);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });


            quit.setOnMouseClicked(mouseEvent -> {
                try {
                    clientView.lobby(KeyLobbyPayload.QUIT_SERVER,-1);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Platform.exit();
                System.exit(0);
            });

            HBox hBox2 = new HBox(random, quit);

            VBox vBox = new VBox(hBox, hBox1, hBox2);
            vBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBox1.setAlignment(Pos.CENTER);
            hBox2.setAlignment(Pos.CENTER);
            getChildren().add(vBox);
            vBox.setAlignment(Pos.CENTER);
        }

}
