package it.polimi.ingsw.view.GUI;


import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.Objects;

public class EndTurnPanel extends BasePanel {
    private ClientView clientView;
    private double x;
    private double y;
    private double z;
    private double w;
    Color[] colors= new Color [] {Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.LIGHTPINK};
    private int counter;


    public EndTurnPanel(ClientView clientView) throws IOException {
        this.clientView = clientView;
        counter = 0;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("base_pagina2.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, true);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        Image banner = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("banner 1386x400px.png")).openStream());
        ImageView bannerView = new ImageView(banner);
        bannerView.setFitHeight(370);
        bannerView.setPreserveRatio(true);
        getChildren().add(bannerView);
        Image title = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Title 2000x618px.png")).openStream());
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
                Button button;
                if (clientView.getBoardView()[i][j].getItemTileView().getTypeView() != null) {
                    button = createBoardButton(clientView, screenBounds.getWidth() * 0.35 / clientView.getBoardView().length, screenBounds.getHeight() * 0.35 / clientView.getBoardView()[0].length, i, j, true);
                } else {
                    button = createBoardButton(clientView, screenBounds.getWidth() * 0.35 / clientView.getBoardView().length, screenBounds.getHeight() * 0.35 / clientView.getBoardView()[0].length, i, j, false);
                }
                board.add(button, j, i);
            }
        }
        board.setMaxSize(screenBounds.getWidth()*0.3, screenBounds.getHeight()*3);
        //board.setMaxSize(x * 9, y * 9);
        //getChildren().add(board);
        //board.setPadding(new Insets(((screenBounds.getHeight() - y * 9)), ((screenBounds.getWidth() - x * 9)), 100, 20));
        GridPane bookshelf = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Button button;
                if (clientView.getBookshelfView()[i][j].getTypeView() != null) {
                    button = createBookshelfButton(clientView, screenBounds.getWidth()*0.32/clientView.getBoardView()[0].length, screenBounds.getHeight()*0.32/clientView.getBoardView().length, i, j, true);
                } else {
                    button = createBookshelfButton(clientView, screenBounds.getWidth()*0.32/clientView.getBoardView()[0].length, screenBounds.getHeight()*0.32/clientView.getBoardView().length, i, j, false);
                }
                button.setPadding(new Insets(6));
                bookshelf.add(button, j+1, i);
            }
        }
        for (int i=0;i<6;i++) {
            Label label = new Label("\n"+Integer.toString(i)); label.setTextFill(Color.WHITE);
            bookshelf.add(label, 0, i);
        }
        for (int i=0;i<5;i++) {
            Label label10 = new Label("      "+Integer.toString(i)); label10.setTextFill(Color.WHITE);
            bookshelf.add(label10, i+1, 6);
        }
        bookshelf.setMaxSize(screenBounds.getWidth()*0.32, screenBounds.getHeight()*0.32);
        //getChildren().add(bookshelf);
        //bookshelf.setMaxSize(z*5, w*6);
        //bookshelf.setPadding(new Insets(screenBounds.getHeight()-w*6, 100, 100, screenBounds.getWidth()-z*5));

        Font font = new Font("Poor Richard", 24);
        Font font1 = new Font("Poor Richard", 22);
        Font font2 = new Font("Poor Richard", 17);
        VBox vBox3 = new VBox();
        vBox3.setMaxSize(450, 200);
        for (int i=(clientView.getPlayerPointsViews().length-1); i>-1; i--) {
            VBox vBox = new VBox();
            Label label = new Label(clientView.getPlayerPointsViews()[i].getNickname() + "   points:    " + clientView.getPlayerPointsViews()[i].getPoints());
            label.setFont(font); label.setTextFill(colors[counter]);
            if (Objects.equals(clientView.getPlayerPointsViews()[i].getNickname(), clientView.getNickname())) {
                int sumToken = 0;
                for (int j =0; j<clientView.getCommonGoalView().length; j++) {
                    sumToken += clientView.getPlayerPointsViews()[i].getPointsToken()[j];
                }
                Label label1 = new Label("Common goal points: "+sumToken+"   Adjacent tiles points: "+clientView.getPlayerPointsViews()[i].getAdjacentPoints()+"   Personal goal points: "+clientView.getPersonalPoints());
                label1.setFont(font2); label1.setTextFill(colors[counter]);
                vBox3.getChildren().addAll(label, label1);
            } else {
                int sumToken = 0;
                for (int j =0; j<clientView.getCommonGoalView().length; j++) {
                    sumToken += clientView.getPlayerPointsViews()[i].getPointsToken()[j];
                }
                Label label1 = new Label("Common goal points: "+sumToken+"   Adjacent tiles points: "+clientView.getPlayerPointsViews()[i].getAdjacentPoints()+"   Personal goal points: ?");
                label1.setFont(font2); label1.setTextFill(colors[counter]);
                vBox3.getChildren().addAll(label, label1);
            }
            vBox3.getChildren().add(vBox);
            counter++;
        }
        //Label label2 = new Label(clientView.getNickname().toUpperCase()+":"); label2.setFont(new Font("Poor Richard", 26)); label2.setTextFill(Color.YELLOW);
        //Label label3 = new Label("Total points:    "+clientView.getPlayerPointsViews().getPoints()); label3.setFont(font); label3.setTextFill(Color.WHITE);
        //Label label4 = new Label("Personal Goal points:    "+clientView.getPlayerPoints().getPersonalGoalPoints()); label4.setFont(font); label4.setTextFill(Color.WHITE);
        //Label label5 = new Label("Adjacent tiles points:    "+clientView.getPlayerPoints().getAdjacentPoints()); label5.setFont(font); label5.setTextFill(Color.WHITE);
        //Label label6 = new Label("Common Goal Card 1 points left:    "+clientView.getCommonGoalView()[1][0]); label6.setFont(font1); label6.setTextFill(Color.LIGHTGREEN);
        //Label label7 = new Label("Common Goal Card 2 points left:    "+clientView.getCommonGoalView()[1][1]); label7.setFont(font1); label7.setTextFill(Color.LIGHTGREEN);
        for (int i = 0; i<clientView.getCommonGoalView().length; i++) {
            Label label = new Label("Common Goal Card "+(i+1)+" points left:    "+clientView.getCommonGoalView()[1][i]); label.setFont(font1); label.setTextFill(Color.LIGHTGREEN);
            vBox3.getChildren().add(label);
        }
        vBox3.setSpacing(10);
        //vBox3.getChildren().addAll(label6, label7);
        vBox3.setAlignment(Pos.CENTER);
        //label2.setAlignment(Pos.TOP_CENTER);
        //label3.setAlignment(Pos.CENTER);
        //label4.setAlignment(Pos.CENTER);
        //label5.setAlignment(Pos.CENTER);
        getChildren().add(vBox3);
        setAlignment(vBox3, Pos.CENTER);
        vBox3.setTranslateY(150);

        Label label = new Label("THE BOARD:"); label.setFont(font); label.setTextFill(Color.YELLOW);
        Label label1 = new Label("YOUR BOOKSHELF:"); label1.setFont(font); label1.setTextFill(Color.YELLOW);
        VBox vBox = new VBox(label, board);
        vBox.setSpacing(15);
        vBox.setMaxSize(getX(), getY()+30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPickOnBounds(false);
        vBox.setTranslateX(30);
        vBox.setTranslateY(-30);
        VBox vBox1 = new VBox(label1, bookshelf);
        vBox1.setSpacing(15);
        vBox1.setMaxSize(getZ(), getW()+30);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setPickOnBounds(false);
        vBox1.setTranslateX(-130);
        vBox1.setTranslateY(-87);
        getChildren().addAll(vBox1, vBox);
        setAlignment(vBox, Pos.BOTTOM_LEFT);
        setAlignment(vBox1, Pos.BOTTOM_RIGHT);

        VBox vBox2 = createCardsBox(clientView, screenBounds);
        getChildren().add(getParquet());
        getChildren().add(getPersonalGoalCardImage());
        getChildren().add(getPng());
        //getChildren().add(getCommonGoalCard1Image());
        //getChildren().add(getCommonGoalCard2Image());
        getChildren().add(getBookshelf());
        getParquet().setVisible(false);
        getPng().setVisible(false);
        getPersonalGoalCardImage().setVisible(false);
        //getCommonGoalCard1Image().setVisible(false);
        //getCommonGoalCard2Image().setVisible(false);
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



