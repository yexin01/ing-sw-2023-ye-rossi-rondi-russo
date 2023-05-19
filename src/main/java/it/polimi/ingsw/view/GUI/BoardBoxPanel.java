package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.message.ErrorType;
import it.polimi.ingsw.view.Check;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.ArrayList;
public class BoardBoxPanel extends BasePanel {

    private ArrayList<Integer> coordinatesSelected = new ArrayList<>();
    private ClientView clientView;
    private ChoicePanel choicePanel;
    private ArrayList<Integer> selectedButtons=new ArrayList<>();
    private int selectedCount;

    //private double x;
    //private double y;

    public BoardBoxPanel (ClientView clientView, ChoicePanel choicePanel) {
        this.clientView=clientView;
        this.choicePanel = choicePanel;

        coordinatesSelected.clear();
        choicePanel.clear();
        selectedCount = 0;

        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\misc\\base_pagina2.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, false, false);
        BackgroundImage background2 = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background2));
        Image box = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\box 280x280px.png");
        ImageView boxView = new ImageView(box);
        getChildren().add(boxView);
        setAlignment(boxView, Pos.BOTTOM_RIGHT);
        ImageView background = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\boards\\livingroom.png"));
        background.setFitWidth(screenBounds.getWidth());
        background.setFitHeight(screenBounds.getHeight());
        background.setPreserveRatio(true);
        GridPane gridPane = new GridPane();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Button button;
                if (clientView.getBoardView()[i][j].getItemTileView().getTypeView() != null) {
                    button = createBoardButton(clientView, background.getFitWidth() * 0.75 / 9, background.getFitHeight() * 0.75 / 9, i, j, true);
                    int finalJ = j;
                    int finalI = i;
                    button.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent actionEvent) {
                            Platform.runLater(() -> {
                                ErrorType errorType1 = Check.checkNumTilesSelectedBoard(coordinatesSelected, clientView.getBookshelfView());
                                coordinatesSelected.add(finalI);
                                coordinatesSelected.add(finalJ);
                                ErrorType errorType2 = Check.checkCoordinates(finalI, finalJ, clientView.getBoardView());
                                ErrorType errorType = Check.checkSelectable(coordinatesSelected, clientView.getBoardView());
                                if (errorType1 == null && errorType2 == null && errorType == null) {
                                    button.setOpacity(0.5);
                                    choicePanel.setButtonIcon(getBoardTiles(finalI, finalJ), selectedCount);
                                    button.setDisable(true);
                                    //coordinatesSelected.add(finalI);
                                    //coordinatesSelected.add(finalJ);
                                    selectedCount++;
                                } else {
                                    coordinatesSelected.remove(coordinatesSelected.size()-2);
                                    coordinatesSelected.remove(coordinatesSelected.size()-1);
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Error!");
                                    alert.setHeaderText("Invalid tile selected");
                                    alert.setContentText("Check the tile's position and the maximum number of tiles you can pick");
                                    alert.show();
                                }
                            });
                        }
                    });
                } else {
                    button = createBoardButton(clientView, background.getFitWidth() * 0.75 / 9, background.getFitHeight() * 0.75 / 9, i, j, false);
                }
                gridPane.add(button, j, i);
            }
        }
        gridPane.setBorder(null);
        //gridPane.setMaxSize(x*9, y*9);

        getChildren().add(background);
        getChildren().add(gridPane);
        gridPane.setTranslateX(-3);

        VBox box1 = createChoiceBox();
        box1.setPickOnBounds(false);
        box1.getChildren().get(0).setOnMouseClicked(mouseEvent ->  {
            Platform.runLater(() -> {
                try {
                    choicePanel.setup(selectedCount);
                    clientView.setCoordinatesSelected(coordinatesSelected);
                    //ChoicePanel.semaphore.release();
                    //coordinatesSelected.clear();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
        box1.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            Platform.runLater(() -> {
                coordinatesSelected.clear();
                selectedCount = 0;
                for (int i =0;i<81; i++) {
                    gridPane.getChildren().get(i).setDisable(false);
                }
                choicePanel.clear();
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

        gridPane.setAlignment(Pos.CENTER);
        Font font = new Font("Poor Richard", 23);
        VBox box4 = new VBox();
        box4.setMaxSize(350, 230);
        Label label5 = new Label("SELECT UP TO 3 TILES!"); label5.setFont(font); label5.setTextFill(Color.YELLOW);
        Label label6 = new Label("\nATTENTION: The tiles you take must be \n" +"adjacent to each other and form a straight line. \n" +
                "All the tiles you take must have at least one \n"+"side free at the beginning of your turn");
        label6.setFont(new Font("Poor Richard", 20)); label6.setTextFill(Color.WHITE);
        box4.getChildren().addAll(label5, label6);
        getChildren().add(box4);
        setAlignment(box4, Pos.CENTER_LEFT);
        //gridPane.setPadding(new Insets(((screenBounds.getHeight()-y*9)/2), ((screenBounds.getWidth()-x*9)/2), ((screenBounds.getHeight()-y*9)/2), ((screenBounds.getWidth()-x*9)/2)));
    }

    /*private Button createEmptyButton(ImageView background, Button button) {
        button.setOpacity(0);
        Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\BOOK 0.png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(background.getFitWidth()*0.75/9);
        imageView.setFitHeight(background.getFitHeight()*0.75/9);
        imageView.setPreserveRatio(true);
        x = imageView.getFitWidth();
        y = imageView.getFitHeight();
        button.setGraphic(imageView);
        return button;
    }

     */

    /*private Button createImageButton(ImageView background, Button button,int i, int j) {
        Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getItemTileView().getTypeView() + " " + clientView.getBoardView()[i][j].getItemTileView().getTileID() % 3 + ".png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(background.getFitWidth()*0.75/9);
        imageView.setFitHeight(background.getFitHeight()*0.75/9);
        imageView.setPreserveRatio(true);
        x = imageView.getFitWidth();
        y = imageView.getFitHeight();
        button.setGraphic(imageView);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Platform.runLater(()-> {
                ErrorType error;
                error = checkSelectable(i, j);
                if (error == null) {
                    selectButton(button, i, j);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Error!");
                    alert.setHeaderText(null);
                    alert.setContentText("Invalid tile selected");
                    alert.show();
                }
                });
            }
        });

        return button;
    }

     */
    /*private void selectButton(Button button, int i, int j) {
        choicePanel.setButtonIcon(".\\src\\main\\java\\org\\example\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getItemTileView().getTypeView() + " " + clientView.getBoardView()[i][j].getItemTileView().getTileID() % 3 + ".png", selectedCount, clientView.getBoardView()[i][j].getItemTileView().getTileID());
        button.setDisable(true);
        selectedCount++;
    }

     */
    /*public void resetChoice(ImageView background) {
        for (int k=0;k<selectedCount*2;k=k+2) {
            int x=selectedButtons.get(k);
            int y=selectedButtons.get(k+1);
            Button button = matrix[x][y];
            choicePanel.resetChoice();
            createImageButton(background,button,x,y);
        }
        selectedCount = 0;
        selectedButtons=new ArrayList<>();
        //Arrays.fill(selectedButtons, -1);



        for (int k=0;k<selectedCount*2;k=k+2) {
            int x=selectedButtons[k];
            int y=selectedButtons[k+1];
            JButton button = matrix[x][y];
            choicePanel.resetChoice();
            createImageButton(button,x,y);
        }
        selectedCount = 0;
        Arrays.fill(selectedButtons, -1);


    }

     */
    /**
     *
     * @return check that each ItemTile of selectedBoard is adjacent to the previous one
     */
    /*public boolean allAdjacent(){
        for (int i = 2; i <= selectedCount*2; i=i+2) {
            if (Math.abs(selectedButtons.get(i) - selectedButtons.get(i-2)) != 1 && Math.abs(selectedButtons.get(i+1) - selectedButtons.get(i-1))!= 1) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText(null);
                alert.setContentText("Not adjacent tile");
                return false;
            }
        }
        return true;
    }

     */

    /**
     *
     * @return check that all the ItemTiles in the selectedBoard array are on the same row or column
     */
    /*public boolean allSameRowOrSameColumn(){
        int firstX=selectedButtons.get(0);
        int firstY=selectedButtons.get(1);
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i <= selectedCount*2; i=i+2) {
            if (selectedButtons.get(i) != firstX) {
                allSameRow = false;
            }
            if (selectedButtons.get(i+1) != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }

     */



        /*
        int firstX=selectedButtons[0];
        int firstY=selectedButtons[1];
        boolean allSameRow = true;
        boolean allSameColumn = true;
        for (int i = 2; i <= selectedCount*2; i=i+2) {
            if (selectedButtons[i] != firstX) {
                allSameRow = false;
            }
            if (selectedButtons[i+1] != firstY) {
                allSameColumn = false;
            }
        }
        if (allSameRow ^ allSameColumn) {
            return true;
        }


        return false;
    }

         */

    /**
     * adjacent, in the same row or column and adjacent
     * @return
     */

    /*public ErrorType checkSelectable(int x,int y) throws Error {
        //TODO AGGIUNGERLO COME PARAMETRO
        if(selectedCount >= (3)){
            return ErrorType.TOO_MANY_TILES;
        }
        BoardBoxView boardBox=clientView.getBoardView()[x][y];
        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        selectedButtons.add(x);
        selectedButtons.add(y);
        /*
        selectedButtons[selectedCount * 2] = x;
        selectedButtons[selectedCount * 2 + 1] = y;


        if (selectedCount == 0) {
            return null;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedButtons.remove(selectedButtons.size()-1);
            selectedButtons.remove(selectedButtons.size()-1);
            /*
            selectedButtons[selectedCount* 2]=-1;
            selectedButtons[selectedCount* 2 + 1]=-1;


            return ErrorType.NOT_SELECTABLE_TILE;
        }

     */




        /*
        if(selectedCount*2 >= (selectedButtons.length)){
            return ErrorType.TOO_MANY_TILES;
        }
        BoardBoxView boardBox=clientView.getBoardView()[x][y];
        if ((boardBox.getFreeEdges() <= 0)) {
            return ErrorType.NOT_SELECTABLE_TILE;
        }
        selectedButtons[selectedCount * 2] = x;
        selectedButtons[selectedCount * 2 + 1] = y;
        if (selectedCount == 0) {
            return null;
        }
        if (!allAdjacent() || !allSameRowOrSameColumn()) {
            selectedButtons[selectedCount* 2]=-1;
            selectedButtons[selectedCount* 2 + 1]=-1;
            return ErrorType.NOT_SELECTABLE_TILE;
        }


        return null;
    }

         */
}

