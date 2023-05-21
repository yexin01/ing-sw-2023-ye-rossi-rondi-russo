package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

public class ChoicePanel extends BasePanel{
    private ItemTileView[] tilesSelected;
    //public static Semaphore semaphore = new Semaphore(0);

    private ClientView clientView;
    private int counter;
    private Button[] itemButtons;
    private int[] orderTiles;

    public ChoicePanel (ClientView clientView, double gap) {
        this.clientView=clientView;
        StackPane stackPane = new StackPane();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stackPane.setMaxWidth(screenBounds.getWidth()*0.8);
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\misc\\base_pagina2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, false, false);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        itemButtons = new Button[3];
        //semaphore.acquire();
        for (int i = 0; i < Check.MAX_SELECTABLE_TILES; i++) {
            itemButtons[i] = new Button();
            createEmptyButton(itemButtons[i], i);
            stackPane.getChildren().add(itemButtons[i]);
            setAlignment(itemButtons[i], Pos.CENTER_LEFT);
            stackPane.getChildren().get(i).setTranslateX(i*gap);
        }

        setPrefSize(screenBounds.getWidth(), screenBounds.getHeight());
        getChildren().add(stackPane);
        setAlignment(stackPane, Pos.CENTER);

        VBox box1 = createChoiceBox();
        box1.setPickOnBounds(false);
        box1.getChildren().get(0).setOnMouseClicked(mouseEvent ->  {
            Platform.runLater(() -> {
                try {
                    clientView.setOrderTiles(orderTiles);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                clientView.setTilesSelected(tilesSelected);
            });
        });
        box1.getChildren().get(1).setOnMouseClicked(mouseEvent ->  {
            Platform.runLater(() -> {
                counter = 0;
                for (int i = 0; i < 3; i++) {
                    itemButtons[i].setDisable(false);
                    itemButtons[i].setOpacity(1);
                }
            });
        });
        getChildren().add(box1);

        VBox vBox2 = createCardsBox(clientView, screenBounds);
        getChildren().add(getParquet());
        getChildren().add(getPersonalGoalCardImage());
        //getChildren().add(getCommonGoalCard1Image());
        //getChildren().add(getCommonGoalCard2Image());
        getChildren().add(getBookshelf());
        getParquet().setVisible(false);
        getPersonalGoalCardImage().setVisible(false);
        //getCommonGoalCard1Image().setVisible(false);
        //getCommonGoalCard2Image().setVisible(false);
        getBookshelf().setVisible(false);
        getBookshelf().setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.TOP_RIGHT);
        vBox2.setPickOnBounds(false);
        getChildren().add(vBox2);


        Font font = new Font("Poor Richard", 25);
        VBox box2 = new VBox();
        box2.setMaxSize(600, 200);
        Label label5 = new Label("SELECT THE TILE(S) ORDER!"); label5.setFont(font); label5.setTextFill(Color.YELLOW);
        Label label6 = new Label("\n The first selected will be the first to be inserted into the library");
        label6.setFont(new Font("Poor Richard", 22)); label6.setTextFill(Color.WHITE);
        box2.getChildren().addAll(label5, label6);
        box2.setAlignment(Pos.CENTER);
        getChildren().add(box2);
        setAlignment(box2, Pos.TOP_CENTER);
    }
    private void createEmptyButton(Button button, int i) {
        button.setDisable(true);
        button.setOpacity(0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                tilesSelected[counter] = new ItemTileView(clientView.getBoardView()[clientView.getCoordinatesSelected().get(2*i)][clientView.getCoordinatesSelected().get(2*i+1)].getType(), clientView.getBoardView()[clientView.getCoordinatesSelected().get(2*i)][clientView.getCoordinatesSelected().get(2*i+1)].getId());
                button.setDisable(true);
                button.setOpacity(0.3);
                counter++;
            }
        });
    }

        public void setButtonIcon(Image icon, int index) {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            ImageView imageView = new ImageView(icon);
            imageView.setFitWidth(screenBounds.getWidth()*0.2*(3.0/Check.MAX_SELECTABLE_TILES));
            imageView.setFitHeight(screenBounds.getHeight()*0.3*(3.0/Check.MAX_SELECTABLE_TILES));
            imageView.setPreserveRatio(true);
            itemButtons[index].setGraphic(imageView);
            itemButtons[index].setOpacity(1);
            itemButtons[index].setDisable(false);
            itemButtons[index].setStyle("-fx-border-color: rgba(255,255,0,0.86); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.86), 20, 0, 0, 0);");
    }

    public void setup (int num) {
        tilesSelected = new ItemTileView[num];
        /*switch (num) {
            case 1:
                orderTiles = new int[]{0};
                break;
            case 2:
                orderTiles = new int[]{0,1};
                break;
            case 3:
                orderTiles = new int[]{0,1,2};
                break;
        }

         */
        orderTiles = new int[num];
        for (int i=0;i<num;i++) {
            orderTiles[i] = i;
        }
    }

    public void clear () {
        for (int i = 0; i < 3; i++) {
            itemButtons[i].setDisable(true);
            itemButtons[i].setOpacity(0);
        }
    }
}