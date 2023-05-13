package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class FinalRankingPanel extends BasePanel{

    public FinalRankingPanel(ClientView clientView) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\misc\\base_pagina2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, false, false);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        Font font = new Font("Poor Richard", 60);
        Font font2 = new Font("Poor Richard", 45);
        Label label1 = new Label("FINAL RANKING"); label1.setFont(font); label1.setTextFill(Color.YELLOW);
        getChildren().add(label1); setAlignment(label1, Pos.TOP_CENTER); label1.setTranslateY(80);
        VBox box = new VBox();
        //box.setMaxSize(500, 200);
        for ( int i=0; i < clientView.getPlayers().length; i++) {
            Label label = new Label(clientView.getPlayers()[i]); label.setFont(font2); label.setTextFill(Color.YELLOW);
            box.getChildren().add(label);
        }
        box.setSpacing(60);
        box.setAlignment(Pos.CENTER);
        getChildren().addAll(box);
        setAlignment(box, Pos.CENTER);
    }
}
