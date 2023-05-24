package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.Objects;

public class FinalRankingPanel extends BasePanel{

    Color[] colors= new Color [] {Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.LIGHTPINK};
    String [] color = new String[] {"YELLOW", "SILVER", "SANDYBROWN", "LIGHTPINK"};
    private int counter;
    private Border border = new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    public FinalRankingPanel(ClientView clientView, int[] personalPoints) throws IOException {
        counter = 0;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Display_1.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, false, true);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        Font font = new Font("Poor Richard", 80);
        Font font2 = new Font("Poor Richard", 65);
        Label label1 = new Label("FINAL RANKING"); label1.setFont(font); label1.setTextFill(Color.YELLOW);
        getChildren().add(label1); setAlignment(label1, Pos.TOP_CENTER); label1.setTranslateY(80);
        VBox box = new VBox();
        HBox hBox1 = new HBox();
        Label label3 = new Label("PLAYERS"); label3.setFont(font2); label3.setTextFill(Color.BLACK);
        label3.setMinWidth(500);
        label3.setBorder(border);
        label3.setStyle("-fx-background-color: white");
        Label label4 = new Label("TOTAL Pts"); label4.setFont(font2); label4.setTextFill(Color.BLACK);
        label4.setMinWidth(300);
        label4.setBorder(border);
        label4.setStyle("-fx-background-color: white");
        hBox1.getChildren().addAll(label3, label4);
        //label3.setStyle("-fx-background-color: "+color[counter]);
        box.getChildren().add(hBox1);
        hBox1.setAlignment(Pos.CENTER);
        //box.setMaxSize(500, 200);
        for ( int i=(clientView.getPlayerPointsViews().length-1); i >-1; i--) {
            HBox hBox = new HBox();
            Label label = new Label(clientView.getPlayerPointsViews()[i].getNickname()); label.setFont(font2); label.setTextFill(Color.BLACK);
            label.setMinWidth(500);
            label.setBorder(border);
            Label label2 = new Label(clientView.getPlayerPointsViews()[i].getPoints()+" Points"); label2.setFont(font2); label2.setTextFill(colors[counter]);
            label2.setAlignment(Pos.CENTER);
            label2.setBorder(border);
            label2.setMinWidth(300);
            hBox.getChildren().addAll(label, label2);
            label.setStyle("-fx-background-color: "+color[counter]);
            box.getChildren().add(hBox);
            hBox.setAlignment(Pos.CENTER);
            counter++;
        }
        Button button = new Button("Return to Lobby");
        getChildren().add(button);
        button.setTranslateY(screenBounds.getHeight()/3);
        button.setOnAction(actionEvent -> {
            Platform.runLater(()-> {
                try {
                    clientView.receiveEndGame();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
        box.setAlignment(Pos.CENTER);
        getChildren().addAll(box);
        setAlignment(box, Pos.CENTER);
    }
}
