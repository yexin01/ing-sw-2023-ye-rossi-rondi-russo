package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;

import java.io.IOException;
import java.util.Objects;

public abstract class BasePanel extends StackPane {

    private final String style1 = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: white;";
    private final String styleReset = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: red;";
    private final Font font = new Font("Poor Richard", 17);
    private StackPane personalGoalCardImage;
    private ImageView parquet;
    private GridPane bookshelf;
    private ImageView png;
    private final Image [][] boardTiles = new Image[9][9];
    private final Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
    private double x;
    private double y;
    private double z;
    private double w;

    /**
     * @param clientView : Client infos
     * @param boardWidth : Button width
     * @param boardHeight : Button height
     * @param i : Board row
     * @param j : Board column
     * @param filled : true if filled, false if empty
     * @return : BoardButton with the correct tile image
     */
    public Button createBoardButton(ClientView clientView, double boardWidth, double boardHeight, int i, int j, boolean filled) throws IOException {
        Button button = new Button();
        ImageView imageView = new ImageView();
        if (!filled) {
            Image icon = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("BOOK 0.png")).openStream());
            imageView.setImage(icon);
            button.setDisable(true);
            button.setOpacity(0);
        } else {
            Image icon = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource(clientView.getBoardView()[i][j].getItemTileView().getTypeView()+" "+clientView.getBoardView()[i][j].getItemTileView().getTileID()%3+".png")).openStream());
            imageView.setImage(icon);
            boardTiles [i][j] = icon;
        }
        imageView.setFitWidth(boardWidth);
        imageView.setFitHeight(boardHeight);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        x = imageView.getFitWidth();
        y = imageView.getFitHeight();
        button.setPadding(new Insets(boardWidth/18));
        return button;
    }

    /**
     * @param clientView : Client infos
     * @param bookshelfWidth : Button width
     * @param bookshelfHeight : Button height
     * @param i : Bookshelf row
     * @param j : Bookshelf column
     * @param filled : true if filled, false if empty
     * @return : BookshelfButton with the correct tile image
     */
    public Button createBookshelfButton(ClientView clientView, double bookshelfWidth, double bookshelfHeight, int i, int j, boolean filled) throws IOException {
        Button button = new Button();
        ImageView imageView = new ImageView();
        if (!filled) {
            Image icon = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("BOOK 0.png")).openStream());
            imageView.setImage(icon);
            button.setDisable(true);
            button.setOpacity(0);
        } else {
            Image icon = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource(clientView.getBookshelfView()[i][j].getTypeView()+" "+clientView.getBookshelfView()[i][j].getTileID()%3+".png")).openStream());
            imageView.setImage(icon);
        }
        imageView.setFitWidth(bookshelfWidth);
        imageView.setFitHeight(bookshelfHeight);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        z = imageView.getFitWidth();
        w = imageView.getFitHeight();
        return button;
    }

    /**
     *
     * @param clientView : Client infos
     * @param screenBounds : screen properties (width and height)
     * @return Vbox containing buttons that shows personal goal card, common goal cards,
     *         your bookshelf and the possibility to restore the info from the Client
     *         view and to quit from the game
     */
    public VBox createCardsBox (ClientView clientView, Rectangle2D screenBounds) throws IOException {
        VBox vBox = new VBox();

        GridPane bookshelf = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Button button;
                if (clientView.getBookshelfView()[i][j].getTypeView() != null) {
                    button = createBookshelfButton(clientView, screenBounds.getWidth()*0.35/5, screenBounds.getHeight()*0.35/6, i, j, true);
                } else {
                    button = createBookshelfButton(clientView, screenBounds.getWidth()*0.35/5, screenBounds.getHeight()*0.35/6, i, j, false);
                }
                bookshelf.add(button, j, i);
            }
        }
        ImageView png = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("bookshelf_orth.png")).openStream()));
        png.setFitWidth(getZ()*7.7);
        png.setFitHeight(getW()*8.7);
        png.setPreserveRatio(true);
        png.setTranslateY(getW()/5);
        this.png = png;
        this.bookshelf = bookshelf;

        Button personal = new Button("Personal Goal Card");
        String style2 = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: yellow;";
        mouseStyle(personal, font, style1, style2);
        ImageView personalGoalCard = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Personal_Goals"+(clientView.getPlayerPersonalGoal().getIdPersonal()+1)+".png")).openStream()));
        personalGoalCard.setFitWidth(screenBounds.getWidth()*0.5);
        personalGoalCard.setFitHeight(screenBounds.getHeight()*0.5);
        personalGoalCard.setPreserveRatio(true);
        StackPane stackPane1 = new StackPane(personalGoalCard);
        this.personalGoalCardImage = stackPane1;
        personal.setOnMouseClicked(mouseEvent -> Platform.runLater(()->{
            parquet.setVisible(true);
            stackPane1.setVisible(true);
        }));
        stackPane1.setOnMouseClicked(mouseEvent -> Platform.runLater(()-> {
            parquet.setVisible(false);
            stackPane1.setVisible(false);
        }));
        vBox.getChildren().add(personal);

        for (int i=0; i<clientView.getCommonGoalView().length;i++) {
            Button button = new Button("Common Goal Card "+(i+1));
            mouseStyle(button, font, style1, style2);
            ImageView commonGoalCard = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource((clientView.getCommonGoalView()[0][i]+1)+".jpg")).openStream()));
            commonGoalCard.setFitWidth(screenBounds.getWidth()*0.35);
            commonGoalCard.setFitHeight(screenBounds.getHeight()*0.35);
            commonGoalCard.setPreserveRatio(true);
            int finalI = i;
            button.setOnMouseClicked(mouseEvent -> Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Common Goal Card "+(finalI +1));
                alert.setHeaderText(null);
                VBox content = new VBox(10);
                content.getChildren().addAll(commonGoalCard, new Label(commonDescription(clientView.getCommonGoalView()[0][finalI])));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(content);
                alert.showAndWait();
            }));
            vBox.getChildren().add(button);
        }

        Button yourBookshelf = new Button("Your bookshelf");
        mouseStyle(yourBookshelf, font, style1, style2);
        yourBookshelf.setOnMouseClicked(mouseEvent -> Platform.runLater(()->{
            parquet.setVisible(true);
            png.setVisible(true);
            bookshelf.setVisible(true);
        }));
        bookshelf.setOnMouseClicked(mouseEvent -> Platform.runLater(()-> {
            parquet.setVisible(false);
            png.setVisible(false);
            bookshelf.setVisible(false);
        }));

        Button restore = new Button("Restore info");
        mouseStyle(restore, font, style1, style2);
        restore.setOnMouseClicked(mouseEvent -> {
            try {
                clientView.somethingWrong();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request sent");
            alert.setHeaderText(null);
            alert.setContentText("Server restored all game's info");
            alert.show();
        });

        Button quit = new Button("Quit");
        mouseStyle(quit, font, style1, styleReset);
        quit.setOnAction(actionEvent -> Platform.runLater(()->{
            Platform.exit();
            System.exit(0);
        }));

        ImageView parquet = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("sfondo parquet.jpg")).openStream()));
        parquet.setFitWidth(screenBounds.getWidth()*0.65);
        parquet.setFitHeight(screenBounds.getHeight()*0.65);
        parquet.setPreserveRatio(true);
        parquet.setEffect(new GaussianBlur());
        this.parquet = parquet;

        vBox.getChildren().addAll(yourBookshelf, restore, quit);
        return vBox;
    }

    /**
     *
     * @return personal goal card png
     */
    public StackPane getPersonalGoalCardImage() {
        return personalGoalCardImage;
    }

    /**
     *
     * @return Vbox containing confirm and reset choice buttons
     */
    public VBox createChoiceBox (){
        VBox vBox = new VBox();
        Button confirmChoiceButton = new Button("Confirm choice");
        String styleConfirm = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: lightgreen;";
        mouseStyle(confirmChoiceButton, font, style1, styleConfirm);

        Button resetChoiceButton = new Button("Reset choice");
        mouseStyle(resetChoiceButton, font, style1, styleReset);

        vBox.getChildren().addAll(confirmChoiceButton, resetChoiceButton);
        return vBox;
    }

    /**
     * Receives a button and changes its style when the mouse is on it
     * @param button : Button chosen
     * @param font : Font chosen for the button
     * @param style1 : Default style
     * @param style2 : Style when the mouse is on the button
     */
    public void mouseStyle (Button button, Font font, String style1, String style2) {
        button.setFont(font);
        button.setStyle(style1);
        button.setOnMouseEntered(event -> button.setStyle(style2));
        button.setOnMouseExited(event -> button.setStyle(style1));
    }

    /**
     * @return Parquet png for the background
     */
    public BackgroundImage getParquetBackground () throws IOException {
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("base_pagina2.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, true);
        return new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    }

    /**
     *
     * @return background image for the lobby
     */
    public BackgroundImage getLobbyBackground () throws IOException {
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Display_5.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
        return new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
    }

    /**
     *
     * @return publisher logo
     */
    public ImageView getPublisher () throws IOException {
        Image publisher = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Publisher.png")).openStream());
        return new ImageView(publisher);
    }

    /**
     *
     * @return Game title
     */
    public ImageView getTitle () throws IOException {
        Image title = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Title 2000x618px.png")).openStream());
        ImageView titleView = new ImageView(title);
        titleView.setFitHeight(250);
        titleView.setPreserveRatio(true);
        return titleView;
    }

    /**
     * @return last board button width
     */
    public double getX() {
        return x;
    }

    /**
     * @return last board button height
     */
    public double getY() {
        return y;
    }

    /**
     * @return last bookshelf button width
     */
    public double getZ() {
        return z;
    }

    /**
     * @return last bookshelf button height
     */
    public double getW() {
        return w;
    }

    /**
     * @param i : board row
     * @param j : board column
     * @return tile image of the board[i][j] tile
     */
    public Image getBoardTiles(int i, int j) {
        return boardTiles[i][j];
    }

    /**
     * @return bookshelf for "Your bookshelf" button
     */
    public GridPane getBookshelf() {
        return bookshelf;
    }

    /**
     * @return parquet background for several buttons
     */
    public ImageView getParquet() {
        return parquet;
    }

    /**
     * @return bookshelf png for "Your Bookshelf" button
     */
    public ImageView getPng() {
        return png;
    }

    /**
     * @param id : Common goal card id
     * @return description of the correspondent common goal card
     */
    public String commonDescription (int id) {
        switch (id) {
            case 0 -> {
                return "Two groups each containing 4 tiles of the same type in a 2x2 square.\nThe tiles of one square can be different from those of the other square.";
            }
            case 1 -> {
                return "Two columns each formed by 6 different types of tiles.";
            }
            case 2 -> {
                return "Four groups each containing at least 4 tiles of the same type (not necessarily in the depicted shape).\nThe tiles of one group can be different from those of another group.";
            }
            case 3 -> {
                return "Six separate groups, each formed by two adjacent tiles of the same type (not necessarily as shown in the figure).\nThe tiles of one group can be different from those of another group.";
            }
            case 4 -> {
                return "Three columns each formed by 6 tiles of maximum three different types.\nOne column can show the same or a different combination of another column.";
            }
            case 5 -> {
                return "Two lines each formed by 5 different types of tiles.\nOne line can show the same or a different combination of the other line.";
            }
            case 6 -> {
                return "Four lines each formed by 5 tiles of maximum three different types.\nOne line can show the same or a different combination of another line.";
            }
            case 7 -> {
                return "Four tiles of the same type in the four corners of the bookshelf.";
            }
            case 8 -> {
                return "Eight tiles of the same type. There’s no restriction about the position of these tiles.";
            }
            case 9 -> {
                return "Five tiles of the same type forming an X.";
            }
            case 10 -> {
                return "Five tiles of the same type forming a diagonal.";
            }
            case 11 -> {
                return "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right,\neach next column must be made of exactly one more tile. Tiles can be of any type.";
            }
        }
        return "Error";
    }
}
