package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class BoardBoxPanel extends BasePanel {

    private final ArrayList<Integer> coordinatesSelected = new ArrayList<>();
    private int selectedCount;


    /**
     * Creates the scene root(Tile(s) selection from the board) and starts to
     * set up the next scene root(Tile(s) order)
     * @param clientView : Client infos
     * @param choicePanel : Panel from the next scene (Tile(s) order)
     */
    public BoardBoxPanel (ClientView clientView, ChoicePanel choicePanel) throws IOException {
        coordinatesSelected.clear();
        choicePanel.clear();
        selectedCount = 0;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        setBackground(new Background(getParquetBackground()));

        Image box = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Box 280x280px.png")).openStream());
        ImageView boxView = new ImageView(box);
        getChildren().add(boxView);
        setAlignment(boxView, Pos.BOTTOM_RIGHT);

        ImageView background = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("livingroom.png")).openStream()));
        background.setFitWidth(screenBounds.getWidth());
        background.setFitHeight(screenBounds.getHeight());
        background.setPreserveRatio(true);

        GridPane gridPane = new GridPane();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button;
                if (clientView.getBoardView()[i][j].getItemTileView().getTypeView() != null) {
                    button = createBoardButton(clientView, background.getFitWidth() * 0.75 / clientView.getBoardView().length, background.getFitHeight() * 0.75 / clientView.getBoardView()[0].length, i, j, true);
                    int finalJ = j;
                    int finalI = i;
                    button.setOnAction(actionEvent -> Platform.runLater(() -> {
                        ErrorType errorType1 = Check.checkNumTilesSelectedBoard(coordinatesSelected, clientView.getBookshelfView());
                        coordinatesSelected.add(finalI);
                        coordinatesSelected.add(finalJ);
                        ErrorType errorType2 = Check.checkCoordinates(finalI, finalJ, clientView.getBoardView());
                        ErrorType errorType = Check.checkSelectable(coordinatesSelected, clientView.getBoardView());
                        if (errorType1 == null && errorType2 == null && errorType == null) {
                            button.setOpacity(0.5);
                            choicePanel.setButtonIcon(getBoardTiles(finalI, finalJ), selectedCount);
                            button.setDisable(true);
                            selectedCount++;
                        } else {
                            coordinatesSelected.remove(coordinatesSelected.size() - 2);
                            coordinatesSelected.remove(coordinatesSelected.size() - 1);
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setTitle("Error!");
                            alert.setHeaderText("Invalid tile selected");
                            alert.setContentText("Check the tile's position and the maximum number of tiles you can pick");
                            alert.show();
                        }
                    }));
                } else {
                    button = createBoardButton(clientView, background.getFitWidth() * 0.75 / 9, background.getFitHeight() * 0.75 / 9, i, j, false);
                }
                gridPane.add(button, j, i);
            }
        }
        getChildren().add(background);
        getChildren().add(gridPane);
        gridPane.setTranslateX(-3);

        VBox box1 = createChoiceBox();
        box1.setPickOnBounds(false);
        box1.getChildren().get(0).setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            try {
                choicePanel.setup(selectedCount);
                clientView.setCoordinatesSelected(coordinatesSelected);
                clientView.setTilesSelected(Check.createItemTileView(coordinatesSelected, clientView.getBoardView()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        box1.getChildren().get(1).setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            selectedCount = 0;
            for (int i =0;i<coordinatesSelected.size(); i+=2) {
                int j = (coordinatesSelected.get(i)*9+coordinatesSelected.get(i+1));
                gridPane.getChildren().get(j).setDisable(false);
                gridPane.getChildren().get(j).setOpacity(1);
            }
            coordinatesSelected.clear();
            choicePanel.clear();
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

        gridPane.setAlignment(Pos.CENTER);
        Font font = new Font("Poor Richard", 23);
        VBox box4 = new VBox();
        box4.setMaxSize(350, 230);
        Label label5 = new Label("SELECT UP TO 3 TILES!"); label5.setFont(font); label5.setTextFill(Color.YELLOW);
        Label label6 = new Label("""

                ATTENTION: The tiles you take must be\s
                adjacent to each other and form a straight line.\s
                All the tiles you take must have at least one\s
                side free at the beginning of your turn""");
        label6.setFont(new Font("Poor Richard", 20)); label6.setTextFill(Color.WHITE);
        box4.getChildren().addAll(label5, label6);
        getChildren().add(box4);
        setAlignment(box4, Pos.CENTER_LEFT);
    }
}

