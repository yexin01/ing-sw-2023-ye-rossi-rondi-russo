package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.model.modelView.ItemTileView;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;

public class ChoicePanel extends BasePanel{
    private ItemTileView[] tilesSelected;
    private final ClientView clientView;
    private int counter;
    private final Button[] itemButtons;
    private int[] orderTiles;

    /**
     * Creates the scene root(Tile(s) order)
     * @param clientView : Client infos
     * @param gap : gap between the selected tiles in the scene
     */
    public ChoicePanel (ClientView clientView, double gap) throws IOException {
        this.clientView=clientView;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        StackPane stackPane = new StackPane();
        stackPane.setMaxWidth(screenBounds.getWidth()*0.8);
        setBackground(new Background(getParquetBackground()));

        itemButtons = new Button[3];
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
        box1.getChildren().get(0).setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            try {
                clientView.setOrderTiles(orderTiles);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            clientView.setTilesSelected(tilesSelected);
        }));
        box1.getChildren().get(1).setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            counter = 0;
            for (int i = 0; i < 3; i++) {
                itemButtons[i].setDisable(false);
                itemButtons[i].setOpacity(1);
            }
        }));
        getChildren().add(box1);

        VBox vBox2 = createCardsBox(clientView, screenBounds);
        getChildren().addAll(getParquet(), getPersonalGoalCardImage(), getPng(), getBookshelf());
        getParquet().setVisible(false);
        getPng().setVisible(false);
        getPersonalGoalCardImage().setVisible(false);
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

    /**
     * Create 3 buttons (Max selectable tiles from the previous scene)
     * @param button : chosen button
     * @param i : button position in the 3 buttons group
     */
    private void createEmptyButton(Button button, int i) {
        button.setDisable(true);
        button.setOpacity(0);
        button.setOnAction(actionEvent -> {
            tilesSelected[counter] = clientView.getTilesSelected()[i];
            button.setDisable(true);
            button.setOpacity(0.3);
            counter++;
        });
    }

    /**
     * For every chosen tile in the previous scene, it changes default button
     * into the chosen tile
     * @param icon : tile image
     * @param index : position inside the tiles selected in the previous scene
     */
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

    /**
     * Initialize two arrays based on the number of the selected tiles
     * @param num : number of selected tiles
     */
    public void setup (int num) {
        tilesSelected = new ItemTileView[num];
        orderTiles = new int[num];
        for (int i=0;i<num;i++) {
            orderTiles[i] = i;
        }
    }

    /**
     * Clear the image set up if the user resets a choice
     */
    public void clear () {
        for (int i = 0; i < 3; i++) {
            itemButtons[i].setDisable(true);
            itemButtons[i].setOpacity(0);
        }
    }
}