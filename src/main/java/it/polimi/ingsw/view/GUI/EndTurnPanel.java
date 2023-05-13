package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.view.ClientView;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

public class EndTurnPanel extends BasePanel {
    private ClientView clientView;
    private double x;
    private double y;
    private double z;
    private double w;

    public EndTurnPanel(ClientView clientView) {
        this.clientView = clientView;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\misc\\base_pagina2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, false, false);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        Image banner = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\banner 1386x400px.png");
        ImageView bannerView = new ImageView(banner);
        bannerView.setFitHeight(370);
        bannerView.setPreserveRatio(true);
        getChildren().add(bannerView);
        Image title = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Title 2000x618px.png");
        ImageView titleView = new ImageView(title);
        titleView.setFitHeight(250);
        titleView.setPreserveRatio(true);
        getChildren().add(titleView);
        titleView.setTranslateY(33);
        setAlignment(titleView, Pos.TOP_CENTER);
        setAlignment(bannerView, Pos.TOP_CENTER);
        GridPane board = new GridPane();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button = new Button();
                button = createBoardButton(clientView, screenBounds.getWidth() * 0.3/9, screenBounds.getHeight() * 0.3/9, i, j, true);
                board.add(button, j, i);
            }
        }
        //board.setMaxSize(x * 9, y * 9);
        //getChildren().add(board);
        //board.setPadding(new Insets(((screenBounds.getHeight() - y * 9)), ((screenBounds.getWidth() - x * 9)), 100, 20));
        GridPane bookshelf = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Button button = new Button();
                button = createBookshelfButton(clientView, screenBounds.getWidth() * 0.32/5, screenBounds.getHeight() * 0.32/6, i, j,true);
                bookshelf.add(button, j, i);
            }
        }
        //getChildren().add(bookshelf);
        //bookshelf.setMaxSize(z*5, w*6);
        //bookshelf.setPadding(new Insets(screenBounds.getHeight()-w*6, 100, 100, screenBounds.getWidth()-z*5));
        Font font = new Font("Poor Richard", 24);
        Label label = new Label("THE BOARD:"); label.setFont(font); label.setTextFill(Color.YELLOW);
        Label label1 = new Label("YOUR BOOKSHELF:"); label1.setFont(font); label1.setTextFill(Color.YELLOW);
        VBox vBox = new VBox(label, board);
        vBox.setSpacing(15);
        vBox.setMaxSize(getX(), getY()+30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPickOnBounds(false);
        vBox.setTranslateX(50);
        vBox.setTranslateY(-50);
        VBox vBox1 = new VBox(label1, bookshelf);
        vBox1.setSpacing(15);
        vBox1.setMaxSize(getZ(), getW()+30);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPickOnBounds(false);
        vBox1.setTranslateX(-75);
        vBox1.setTranslateY(-57);
        getChildren().addAll(vBox1, vBox);
        setAlignment(vBox, Pos.BOTTOM_LEFT);
        setAlignment(vBox1, Pos.BOTTOM_RIGHT);

        VBox vBox2 = createCardsBox(clientView, screenBounds);
        getChildren().add(getParquet());
        getChildren().add(getPersonalGoalCardImage());
        getChildren().add(getCommonGoalCard1Image());
        getChildren().add(getCommonGoalCard2Image());
        getChildren().add(getBookshelf());
        getParquet().setVisible(false);
        getPersonalGoalCardImage().setVisible(false);
        getCommonGoalCard1Image().setVisible(false);
        getCommonGoalCard2Image().setVisible(false);
        getBookshelf().setVisible(false);
        getBookshelf().setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.TOP_RIGHT);
        vBox2.setPickOnBounds(false);
        getChildren().add(vBox2);
    }

    /*private Button createEmptyBoardButton(Button button) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        button.setOpacity(0);
        Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\BOOK 0.png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(screenBounds.getWidth() * 0.3 / 9);
        imageView.setFitHeight(screenBounds.getHeight() * 0.3 / 9);
        imageView.setPreserveRatio(true);
        x = imageView.getFitWidth();
        y = imageView.getFitHeight();
        button.setGraphic(imageView);
        return button;
    }

    private Button createBoardButton(Button button, int i, int j) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getItemTileView().getTypeView() + " " + clientView.getBoardView()[i][j].getItemTileView().getTileID() % 3 + ".png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(screenBounds.getWidth() * 0.3 / 9);
        imageView.setFitHeight(screenBounds.getHeight() * 0.3 / 9);
        imageView.setPreserveRatio(true);
        x = imageView.getFitWidth();
        y = imageView.getFitHeight();
        button.setGraphic(imageView);
        return button;
    }

    private Button createEmptyBookshelfButton(Button button) {
        button.setOpacity(0);
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image icon = new Image("file:src\\main\\java\\com\\example\\demo1\\Images\\itemTiles\\BOOK0.png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(screenBounds.getWidth()*0.28/5);
        imageView.setFitHeight(screenBounds.getHeight()*0.28/6);
        imageView.setPreserveRatio(true);
        z = imageView.getFitWidth();
        w = imageView.getFitHeight();
        button.setGraphic(imageView);
        button.setDisable(true);;
        return button;
    }

    private Button createBookshelfButton(Button button,int i, int j) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBookshelfView()[i][j].getTypeView() + " " + clientView.getBookshelfView()[i][j].getTileID() % 3 + ".png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(screenBounds.getWidth()*0.28/5);
        imageView.setFitHeight(screenBounds.getHeight()*0.28/6);
        imageView.setPreserveRatio(true);
        z = imageView.getFitWidth();
        w = imageView.getFitHeight();
        button.setGraphic(imageView);
        return button;
    }

     */
}



