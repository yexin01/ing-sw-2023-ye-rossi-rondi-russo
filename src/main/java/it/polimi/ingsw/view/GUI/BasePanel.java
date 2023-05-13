package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.ClientInterface;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Button;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

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
    private Image [][] boardTiles = new Image[9][9];
    private double x;
    private double y;
    private double z;
    private double w;

    public Font getFont() {
        return font;
    }

    public Button createBoardButton(ClientView clientView, double boardWidth, double boardHeight, int i, int j, boolean filled) {
        Button button = new Button();
        ImageView imageView = new ImageView();
        if (!filled) {
            Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\itemTiles\\BOOK 0.png");
            imageView.setImage(icon);
            button.setDisable(true);
            button.setOpacity(0);
        } else {
            Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBoardView()[i][j].getItemTileView().getTypeView() + " " + clientView.getBoardView()[i][j].getItemTileView().getTileID() % 3 + ".png");
            imageView.setImage(icon);
            boardTiles [i][j] = icon;
        }
        imageView.setFitWidth(boardWidth);
        imageView.setFitHeight(boardHeight);
        imageView.setPreserveRatio(true);
        button.setGraphic(imageView);
        x = imageView.getFitWidth();
        y = imageView.getFitHeight();
        return button;
    }

    public Button createBookshelfButton(ClientView clientView, double bookshelfWidth, double bookshelfHeight, int i, int j, boolean filled) {
        Button button = new Button();
        ImageView imageView = new ImageView();
        if (!filled) {
            Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\itemTiles\\BOOK 0.png");
            imageView.setImage(icon);
            button.setDisable(true);
            button.setOpacity(0);
        } else {
            Image icon = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Item tiles\\" + clientView.getBookshelfView()[i][j].getTypeView() + " " + clientView.getBookshelfView()[i][j].getTileID() % 3 + ".png");
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

    public VBox createCardsBox (ClientView clientView, Rectangle2D screenBounds) {
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

        ImageView personalGoalCard = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\personal goal cards\\Personal_Goals"+clientView.getIndexPersonal()+".png"));
        personalGoalCard.setFitWidth(screenBounds.getWidth()*0.5);
        personalGoalCard.setFitHeight(screenBounds.getHeight()*0.5);
        personalGoalCard.setPreserveRatio(true);

        Button common1 = new Button("Common Goal Card 1");
        personal.setFont(font);
        personal.setStyle(style1);
        personal.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common1.setStyle(style2);
            }
        });

        personal.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common1.setStyle(style1);
            }
        });

        ImageView commonGoalCard1 = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\personal goal cards\\Personal_Goals"+clientView.getIndexPersonal()+".png"));
        commonGoalCard1.setFitWidth(screenBounds.getWidth()*0.5);
        commonGoalCard1.setFitHeight(screenBounds.getHeight()*0.5);
        commonGoalCard1.setPreserveRatio(true);

        Button common2 = new Button("Personal Goal Card");
        common2.setFont(font);
        common2.setStyle(style1);
        common2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common2.setStyle(style2);
            }
        });

        personal.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                common2.setStyle(style1);
            }
        });

        ImageView commonGoalCard2 = new ImageView(new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\personal goal cards\\Personal_Goals"+clientView.getIndexPersonal()+".png"));
        commonGoalCard2.setFitWidth(screenBounds.getWidth()*0.5);
        commonGoalCard2.setFitHeight(screenBounds.getHeight()*0.5);
        commonGoalCard2.setPreserveRatio(true);


        StackPane stackPane1 = new StackPane(personalGoalCard);
        this.personalGoalCardImage = stackPane1;
        StackPane stackPane2 = new StackPane(commonGoalCard1);
        this.commonGoalCard1Image = stackPane2;
        StackPane stackPane3 = new StackPane(commonGoalCard2);
        this.commonGoalCard2Image = stackPane3;
        ImageView parquet = new ImageView("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\misc\\sfondo parquet.jpg");
        parquet.setFitWidth(screenBounds.getWidth()*0.65);
        parquet.setFitHeight(screenBounds.getHeight()*0.65);
        parquet.setPreserveRatio(true);
        parquet.setEffect(new GaussianBlur());
        this.parquet = parquet;
        vBox.getChildren().addAll(personal, common1, common2);
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
        common1.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                parquet.setVisible(true);
                stackPane2.setVisible(true);
            });
        });
        stackPane2.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()-> {
                parquet.setVisible(false);
                stackPane2.setVisible(false);
            });
        });
        common2.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                parquet.setVisible(true);
                stackPane3.setVisible(true);
            });
        });
        stackPane3.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()-> {
                parquet.setVisible(false);
                stackPane3.setVisible(false);
            });
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
        GridPane bookshelf = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                Button button;
                button = createBookshelfButton(clientView, screenBounds.getWidth()*0.35/5, screenBounds.getHeight()*0.35/6, i, j, true);
                bookshelf.add(button, j, i);
            }
        }
        this.bookshelf = bookshelf;
        yourBookshelf.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()->{
                parquet.setVisible(true);
                bookshelf.setVisible(true);
            });
        });
        bookshelf.setOnMouseClicked(mouseEvent -> {
            Platform.runLater(()-> {
                parquet.setVisible(false);
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
        return vBox;
    }


}
