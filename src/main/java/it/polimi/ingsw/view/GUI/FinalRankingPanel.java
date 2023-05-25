package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
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

public class FinalRankingPanel extends BasePanel{

    Color[] colors= new Color [] {Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.LIGHTPINK};
    String [] color = new String[] {"YELLOW", "SILVER", "SANDYBROWN", "LIGHTPINK"};
    private int counter;
    private Border border = new Border(new BorderStroke(Color.BLACK,
            BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    public FinalRankingPanel(ClientView clientView, int[] personalPoints) throws IOException {
        counter = 0;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("base_pagina2.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, true);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        Font font4 = new Font("Poor Richard", 60);
        //Font font2 = new Font("Poor Richard", 65);
        Label label4 = new Label("FINAL RANKING"); label4.setFont(font4); label4.setTextFill(Color.YELLOW);
        getChildren().add(label4); setAlignment(label4, Pos.TOP_CENTER); label4.setTranslateY(80);
        /*VBox box = new VBox();
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

         */

        Font font = new Font("Poor Richard", 33);
        Font font1 = new Font("Poor Richard", 40);
        Font font2 = new Font("Poor Richard", 24);
        VBox vBox3 = new VBox();
        vBox3.setSpacing(40);
        vBox3.setAlignment(Pos.CENTER);
        for (int i=(clientView.getPlayerPointsViews().length-1); i>-1; i--) {
            VBox vBox = new VBox();
            Label label = new Label(clientView.getPlayerPointsViews()[i].getNickname());
            label.setFont(font1); label.setTextFill(Color.BLACK);
            Label label5 = new Label( clientView.getPlayerPointsViews()[i].getPoints()+"Points");
            label5.setFont(font); label5.setTextFill(Color.BLACK);
            int sumToken = 0;
            for (int j =0; j<clientView.getCommonGoalView().length; j++) {
                sumToken += clientView.getPlayerPointsViews()[i].getPointsToken()[j];
            }
            Label label1 = new Label("Common goals: "+sumToken+"   Adjacent tiles: "+clientView.getPlayerPointsViews()[i].getAdjacentPoints()+"   Personal goal: "+(clientView.getPlayerPointsViews()[i].getPoints()-(sumToken+clientView.getPlayerPointsViews()[i].getAdjacentPoints())));
            label1.setFont(font2); label1.setTextFill(Color.BLACK);
            vBox.getChildren().addAll(label5,label1);
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);
            Label label2 = new Label((counter+1)+""); label2.setFont(font1); label2.setTextFill(colors[counter]);
            HBox hBox = new HBox(label2, label, vBox);
            hBox.setMaxSize(700, 400);
            hBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: beige; -fx-background-radius: 8px; -fx-smooth: true");
            vBox3.getChildren().addAll(hBox);
            counter++;
        }
        getChildren().add(vBox3);
        setAlignment(vBox3, Pos.CENTER);
    }
}
