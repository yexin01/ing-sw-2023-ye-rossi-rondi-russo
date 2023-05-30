package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.message.ErrorType;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.Objects;

public class DisconnectionPanel extends BasePanel{

    /**
     * Creates a panel to be shown when the player is the last
     * active player inside a game
     */
    public DisconnectionPanel() throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Display_2.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));

        Label label = new Label(ErrorType.ONLY_PLAYER.getErrorMessage());
        label.setFont(new Font("Poor Richard", 28)); label.setTextFill(Color.RED);
        Label label1 = new Label("Please wait...");
        label1.setFont(new Font("Poor Richard", 60)); label1.setTextFill(Color.RED);
        VBox vBox = new VBox(label, label1);
        vBox.setSpacing(30);
        vBox.setStyle("-fx-background-color: lightgrey; -fx-background-radius: 10px");
        vBox.setMaxSize(800, 200);
        vBox.setAlignment(Pos.CENTER);
        getChildren().add(vBox);
        setAlignment(vBox, Pos.CENTER);
    }
}
