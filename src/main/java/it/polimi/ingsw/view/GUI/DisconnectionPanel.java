package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class DisconnectionPanel extends BasePanel{
    public DisconnectionPanel(String error) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Display_2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNING!");
        alert.setHeaderText(null);
        alert.setContentText(error);
        alert.show();
        Label label = new Label("Please wait...");
        label.setFont(new Font("Poor Richard", 60)); label.setTextFill(Color.RED);
        getChildren().add(label);
        setAlignment(label, Pos.CENTER);
    }
}
