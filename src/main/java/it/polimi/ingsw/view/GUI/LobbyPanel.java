package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LobbyPanel extends BasePanel{

    private String nickname;
    public LobbyPanel(ClientView clientView) {
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
        TextField textField = new TextField("Insert your nickname here");
        textField.setPrefSize(400, 35);
        Button confirmButton = new Button("Join");
        confirmButton.setOnAction(actionEvent -> {
            nickname = textField.getText();
            if (nickname.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("No nickname founded");
                alert.setContentText("Please insert a nickname");
                alert.show();
            } else {
                clientView.setNickname(nickname);
            }
        });
        HBox hBox = new HBox(textField, confirmButton);
        getChildren().add(hBox);
        hBox.setAlignment(Pos.CENTER);
    }
}
