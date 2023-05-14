package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.message.KeyLobbyPayload;
import it.polimi.ingsw.message.MessageHeader;
import it.polimi.ingsw.message.MessagePayload;
import it.polimi.ingsw.message.MessageType;
import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

public class LobbyDecisionPanel extends BasePanel{

    private String numPlayers;
    private String id;
        public LobbyDecisionPanel(ClientView clientView) {
            MessageHeader header=new MessageHeader(MessageType.LOBBY, clientView.getNickname());

            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Display_5.jpg");
            BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            setBackground(new Background(background));
            Image publisher = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\publisher.png");
            ImageView publisherView = new ImageView(publisher);
            Image title = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Title 2000x618px.png");
            ImageView titleView = new ImageView(title);
            titleView.setFitHeight(250);
            titleView.setPreserveRatio(true);
            getChildren().add(titleView);
            getChildren().add(publisherView);
            setAlignment(titleView, Pos.TOP_CENTER);
            setAlignment(publisherView, Pos.BOTTOM_RIGHT);

            TextField textField = new TextField("Insert players number(2-4)");
            textField.setPrefSize(400, 35);
            Button confirmButton = new Button("Create new Lobby");
            confirmButton.setOnAction(actionEvent -> {
                numPlayers = textField.getText();
                if (numPlayers.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText("No number founded");
                    alert.setContentText("Please insert a number");
                    alert.show();
                } else {
                    MessagePayload payload = new MessagePayload(KeyLobbyPayload.CREATE_GAME_LOBBY);
                    try {
                        clientView.lobby(KeyLobbyPayload.CREATE_GAME_LOBBY, Integer.parseInt(numPlayers));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            HBox hBox = new HBox(textField, confirmButton);

            TextField textField1 = new TextField("Insert ID Lobby");
            textField.setPrefSize(400, 35);
            Button confirmButton1 = new Button("Join specific lobby");
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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            HBox hBox1 = new HBox(textField1, confirmButton1);
            VBox vBox = new VBox(hBox, hBox1);
            hBox.setAlignment(Pos.CENTER);
            hBox1.setAlignment(Pos.CENTER);
            getChildren().add(vBox);
            vBox.setAlignment(Pos.CENTER);
        }

}
