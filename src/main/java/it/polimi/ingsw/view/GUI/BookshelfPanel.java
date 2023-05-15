package it.polimi.ingsw.view.GUI;


import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientView;
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

public class BookshelfPanel extends BasePanel {
    private ClientView clientView;
    private double x;
    private double y;

    public BookshelfPanel(ClientView clientView) {
        this.clientView = clientView;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\misc\\base_pagina2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, false, false);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Button button;
                if (clientView.getBookshelfView()[i][j].getTypeView() != null) {
                    button = createBookshelfButton(clientView, screenBounds.getWidth()*0.58/5, screenBounds.getHeight()*0.58/6, i, j, true);
                } else {
                    button = createBookshelfButton(clientView, screenBounds.getWidth()*0.58/5, screenBounds.getHeight()*0.58/6, i, j, false);
                }
                gridPane.add(button, j, i+2);
            }
        }
        for (int j = 0; j < 5; j++) {
            Button button = new Button();
            int i = 0;
            button = createBookshelfButton(clientView, screenBounds.getWidth()*0.58/5, screenBounds.getHeight()*0.58/6, i, j, false);
            gridPane.add(button, j, 1);
        }
        for (int j = 0; j < 5; j++) {
            Button button = new Button();
            button = createArrow(button, j);
            gridPane.add(button, j, 0);
        }
        ImageView background = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\boards\\bookshelf.png"));
        background.setFitWidth(screenBounds.getWidth()*0.8);
        background.setFitHeight(screenBounds.getHeight()*0.8);
        background.setPreserveRatio(true);
        getChildren().addAll(background, gridPane);
        //double[] size = getImageSize(screenBounds.getWidth()*0.58/5, screenBounds.getHeight()*0.58/6);
        gridPane.setMaxSize(getZ()*5, getW()*8);
        setAlignment(background, Pos.BOTTOM_CENTER);

        gridPane.setPadding(new Insets(((screenBounds.getHeight()-getW()*8)/2), ((screenBounds.getWidth()-getZ()*5)/2), (background.getFitHeight()*0.22), ((screenBounds.getWidth()-getZ()*5)/2)));
        Font font = new Font("Poor Richard", 24);
        VBox box2 = new VBox();
        box2.setMaxSize(500, 200);
        Label label5 = new Label("SELECT  THE  COLUMN  TO  INSERT  \n"+"              THE  SELECTED  TILE(S)!"); label5.setFont(font); label5.setTextFill(Color.YELLOW);
        Label label6 = new Label("\nTIP: check the goal cards thanks to the above buttons to \n"+" optimize your play and score as many points as possible");
        label6.setFont(new Font("Poor Richard", 20)); label6.setTextFill(Color.WHITE);
        box2.getChildren().addAll(label5, label6);
        box2.setAlignment(Pos.CENTER);
        getChildren().addAll(box2);
        setAlignment(box2, Pos.CENTER_LEFT);

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

        HBox hBox = new HBox();
        hBox.setSpacing(25);
        Label label = new Label("YOUR TILE(S):    "); label.setFont(font); label.setTextFill(Color.YELLOW);
        hBox.getChildren().add(label);
        for (int i=0;i<clientView.getTilesSelected().length;i++) {
            Button button = new Button();
            button.setPrefSize(80, 80);
            BackgroundSize backgroundSize1 = new BackgroundSize(100, 100, true, true, true, false);
            //BackgroundImage backgroundImage1 = new BackgroundImage(getBoardTiles(clientView.getCoordinatesSelected().get(i), clientView.getCoordinatesSelected().get(i+1)), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize1);
            Image image = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\item tiles\\" + clientView.getTilesSelected()[i].getTypeView() + " " + clientView.getTilesSelected()[i].getTileID() % 3 + ".png");
            BackgroundImage backgroundImage1 = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize1);
            Background background1 = new Background(backgroundImage1);
            button.setBackground(background1);
            hBox.getChildren().add(button);
        }
        getChildren().add(hBox);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPickOnBounds(false);
    }

    private Button createArrow (Button button, int i) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\arrow.png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(screenBounds.getWidth()*0.58/5);
        imageView.setFitHeight(screenBounds.getHeight()*0.58/6);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(() -> {
                    ErrorType errorType = Check.checkBookshelf(i, clientView.getBookshelfView(), clientView.getTilesSelected());
                    if (errorType == null) {
                        try {
                            clientView.setColumn(i);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        Check.insertTiles(i, clientView.getBookshelfView(), clientView.getTilesSelected());
                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Error!");
                        alert.setHeaderText("Invalid column selected");
                        alert.setContentText("Check the columns' free shelves");
                        alert.show();
                    }
                });
            }
        });
        return button;
    }

}
