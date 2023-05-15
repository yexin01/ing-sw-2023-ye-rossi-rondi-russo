package it.polimi.ingsw.view.GUI;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class DisconnectionPanel extends BasePanel{
    public DisconnectionPanel() {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Display_2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));
        Label label = new Label("Connection error between server and client.\n Wait until the connection is trying to setup again");
        label.setFont(new Font("Poor Richard", 40)); label.setTextFill(Color.YELLOW);
        getChildren().add(label);
        setAlignment(label, Pos.CENTER);
    }
}
