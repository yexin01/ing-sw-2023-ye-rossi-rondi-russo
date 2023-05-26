package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public abstract class BasePanel extends StackPane {

    private String style2 = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: yellow;";
    private String style1 = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: white;";
    private String styleConfirm = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: lightgreen;";
    private String styleReset = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: red;";
    private Font font = new Font("Poor Richard", 17);
    private StackPane personalGoalCardImage;
    private StackPane commonGoalCard1Image;
    private StackPane commonGoalCard2Image;
    private ImageView parquet;
    private GridPane bookshelf;
    private ImageView png;
    private Image [][] boardTiles = new Image[9][9];
    private double x;
    private double y;
    private double z;
    private double w;

    public Font getFont() {
        return font;
    }

    public Button createBoardButton(ClientView clientView, double boardWidth, double boardHeight, int i, int j, boolean filled) throws IOException {
        Button button = new Button();
        ImageView imageView = new ImageView();
        if (!filled) {
            Image icon = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("BOOK 0.png")).openStream());
            imageView.setImage(icon);
            button.setDisable(true);
            button.setOpacity(0);
        } else {
            //Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getItemTileView().getTypeView() + " " + clientView.getBoardView()[i][j].getItemTileView().getTileID() % 3 + ".png");
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

    public Button createBookshelfButton(ClientView clientView, double bookshelfWidth, double bookshelfHeight, int i, int j, boolean filled) throws IOException {
        Button button = new Button();
        ImageView imageView = new ImageView();
        if (!filled) {
            Image icon = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("BOOK 0.png")).openStream());
            imageView.setImage(icon);
            button.setDisable(true);
            button.setOpacity(0);
        } else {
            //Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\item tiles\\" + clientView.getBookshelfView()[i][j].getTypeView() + " " + clientView.getBookshelfView()[i][j].getTileID() % 3 + ".png");
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

    /*public Button createEmptyButton (double x, double y) {
        Button button = new Button();
        Image icon = new Image("file:src\\main\\java\\com\\example\\demo1\\Images\\itemTiles\\BOOK0.png");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(x);
        imageView.setFitHeight(y);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        button.setOpacity(0);
        return button;
    }

     */

    /*public ImageView getImageView (double x, double y) {
        Image icon = new Image("file:src\\main\\java\\com\\example\\demo1\\Images\\itemTiles\\BOOK0.png");
        ImageView imageView = new ImageView(icon);
        imageView.setFitWidth(x);
        imageView.setFitHeight(y);
        imageView.setPreserveRatio(true);
        /*double[] size = new double[2];
        size[0] = imageView.getFitWidth();
        size[1] = imageView.getFitHeight();


        return imageView;
    };

     */

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public Image getBoardTiles(int i, int j) {
        return boardTiles[i][j];
    }

    public GridPane getBookshelf() {
        return bookshelf;
    }

    public ImageView getParquet() {
        return parquet;
    }

    public ImageView getPng() {
        return png;
    }

    public VBox createCardsBox (ClientView clientView, Rectangle2D screenBounds) throws IOException {
        VBox vBox = new VBox();
        Button personal = new Button("Personal Goal Card");
        personal.setFont(font);
        personal.setStyle(style1);
        personal.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                personal.setStyle(style2);
            }
        });

        personal.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                personal.setStyle(style1);
            }
        });

        //ImageView personalGoalCard = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\personal goal cards\\Personal_Goals"+(clientView.getPlayerPersonalGoal().getIdPersonal()+1)+".png"));
        ImageView personalGoalCard = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Personal_Goals"+(clientView.getPlayerPersonalGoal().getIdPersonal()+1)+".png")).openStream()));
        personalGoalCard.setFitWidth(screenBounds.getWidth()*0.5);
        personalGoalCard.setFitHeight(screenBounds.getHeight()*0.5);
        personalGoalCard.setPreserveRatio(true);

        for (int i=0; i<clientView.getCommonGoalView().length;i++) {
            Button button = new Button("Common Goal Card "+(i+1));
            button.setFont(font);
            button.setStyle(style1);
            button.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setStyle(style2);
                }
            });

            button.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    button.setStyle(style1);
                }
            });

            //ImageView commonGoalCard = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\common goal cards\\"+(clientView.getCommonGoalView()[0][i]+1)+".jpg"));
            ImageView commonGoalCard = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource((clientView.getCommonGoalView()[0][i]+1)+".jpg")).openStream()));
            commonGoalCard.setFitWidth(screenBounds.getWidth()*0.35);
            commonGoalCard.setFitHeight(screenBounds.getHeight()*0.35);
            commonGoalCard.setPreserveRatio(true);

            int finalI = i;
            button.setOnMouseClicked(mouseEvent -> {
                Platform.runLater(()->{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Common Goal Card "+(finalI +1));
                    alert.setHeaderText(null);
                    VBox content = new VBox(10);
                    content.getChildren().addAll(commonGoalCard, new Label(commonDescription(clientView.getCommonGoalView()[0][finalI])));
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setContent(content);
                    alert.showAndWait();
                });
            });
            vBox.getChildren().add(button);
        }

        /*Button common1 = new Button("Common Goal Card 1");
        common1.setFont(font);
        common1.setStyle(style1);
        common1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common1.setStyle(style2);
            }
        });

        common1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common1.setStyle(style1);
            }
        });

        ImageView commonGoalCard1 = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\common goal cards\\"+(clientView.getCommonGoalView()[0][0]+1)+".jpg"));
        commonGoalCard1.setFitWidth(screenBounds.getWidth()*0.35);
        commonGoalCard1.setFitHeight(screenBounds.getHeight()*0.35);
        commonGoalCard1.setPreserveRatio(true);

        common1.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Common Goal Card 1");
                alert.setHeaderText(null);
                VBox content = new VBox(10);
                content.getChildren().addAll(commonGoalCard1, new Label(commonDescription(clientView.getCommonGoalView()[0][0])));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(content);
                alert.showAndWait();
            });
        });

         */

        /*Button common2 = new Button("Common Goal Card 2");
        common2.setFont(font);
        common2.setStyle(style1);
        common2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common2.setStyle(style2);
            }
        });

        common2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common2.setStyle(style1);
            }
        });

        ImageView commonGoalCard2 = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\common goal cards\\"+(clientView.getCommonGoalView()[0][1]+1)+".jpg"));
        commonGoalCard2.setFitWidth(screenBounds.getWidth()*0.35);
        commonGoalCard2.setFitHeight(screenBounds.getHeight()*0.35);
        commonGoalCard2.setPreserveRatio(true);

        common2.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Common Goal Card 2");
                alert.setHeaderText(null);
                VBox content = new VBox(10);
                content.getChildren().addAll(commonGoalCard2, new Label(commonDescription(clientView.getCommonGoalView()[0][1])));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(content);
                alert.showAndWait();
            });
        });

         */


        StackPane stackPane1 = new StackPane(personalGoalCard);
        this.personalGoalCardImage = stackPane1;
        /*StackPane stackPane2 = new StackPane(commonGoalCard1);
        this.commonGoalCard1Image = stackPane2;
        StackPane stackPane3 = new StackPane(commonGoalCard2);
        this.commonGoalCard2Image = stackPane3;

         */
        ImageView parquet = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("sfondo parquet.jpg")).openStream()));
        parquet.setFitWidth(screenBounds.getWidth()*0.65);
        parquet.setFitHeight(screenBounds.getHeight()*0.65);
        parquet.setPreserveRatio(true);
        parquet.setEffect(new GaussianBlur());
        this.parquet = parquet;
        vBox.getChildren().addAll(personal);
        personal.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                parquet.setVisible(true);
                stackPane1.setVisible(true);
            });
        });
        stackPane1.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()-> {
                parquet.setVisible(false);
                stackPane1.setVisible(false);
                });
        });
        /*common1.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Common Goal Card 1");
                alert.setHeaderText(null);
                VBox content = new VBox(10);
                content.getChildren().addAll(commonGoalCard1, new Label(commonDescription(clientView.getCommonGoalView()[0][0])));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(content);
                alert.showAndWait();
            });
        });
        common2.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Common Goal Card 2");
                alert.setHeaderText(null);
                VBox content = new VBox(10);
                content.getChildren().addAll(commonGoalCard2, new Label(commonDescription(clientView.getCommonGoalView()[0][1])));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(content);
                alert.showAndWait();
            });
        });


        stackPane3.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()-> {
                parquet.setVisible(false);
                stackPane3.setVisible(false);
            });
        });

         */

        Button restore = new Button("Restore info");
        restore.setFont(font);
        restore.setStyle(style1);
        restore.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restore.setStyle(style2);
            }
        });

        restore.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                restore.setStyle(style1);
            }
        });

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

        Button yourBookshelf = new Button("Your bookshelf");
        yourBookshelf.setFont(font);
        yourBookshelf.setStyle("-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: white;");
        yourBookshelf.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yourBookshelf.setStyle(style2);
            }
        });
        yourBookshelf.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                yourBookshelf.setStyle(style1);
            }
        });
        vBox.getChildren().add(yourBookshelf);
        vBox.getChildren().add(restore);
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
        //png.setFitWidth(getZ()*7.7);
        //png.setFitHeight(getW()*8.7);
        png.setFitWidth(getZ()*7.7);
        png.setFitHeight(getW()*8.7);
        png.setPreserveRatio(true);
        png.setTranslateY(getW()/5);
        this.png = png;
        this.bookshelf = bookshelf;
        yourBookshelf.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                parquet.setVisible(true);
                png.setVisible(true);
                bookshelf.setVisible(true);
            });
        });
        bookshelf.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()-> {
                parquet.setVisible(false);
                png.setVisible(false);
                bookshelf.setVisible(false);
            });
        });
        return vBox;
    }

    public StackPane getPersonalGoalCardImage() {
        return personalGoalCardImage;
    }

    public StackPane getCommonGoalCard1Image() {
        return commonGoalCard1Image;
    }

    public StackPane getCommonGoalCard2Image() {
        return commonGoalCard2Image;
    }

    public VBox createChoiceBox (){
        VBox vBox = new VBox();
        Button confirmChoiceButton = new Button("Confirm choice");
        confirmChoiceButton.setFont(font);
        confirmChoiceButton.setStyle(style1);
        confirmChoiceButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmChoiceButton.setStyle(styleConfirm);
            }
        });

        confirmChoiceButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                confirmChoiceButton.setStyle(style1);
            }
        });
        Button resetChoiceButton = new Button("Reset choice");
        resetChoiceButton.setFont(font);
        resetChoiceButton.setStyle(style1);
        resetChoiceButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetChoiceButton.setStyle(styleReset);
            }
        });

        resetChoiceButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                resetChoiceButton.setStyle(style1);
            }
        });
        vBox.getChildren().add(confirmChoiceButton);
        vBox.getChildren().add(resetChoiceButton);
        Button quit = new Button("Quit");
        quit.setFont(font);
        quit.setStyle(style1);
        quit.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                quit.setStyle(styleReset);
            }
        });

        quit.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                quit.setStyle(style1);
            }
        });
        quit.setOnAction(actionEvent -> {
            Platform.runLater(()->{
                Platform.exit();
                System.exit(0);
            });
        });
        vBox.getChildren().add(quit);
        return vBox;
    }

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
