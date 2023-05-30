package it.polimi.ingsw.view.GUI;


import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
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


    /**
     * Creates a panel to be shown when the user's turn is over.
     * Besides the others in-turn options, it additionally shows
     * a real time board and the common goal card tokens left
     * @param clientView : Client infos
     */
    public EndTurnPanel(ClientView clientView) throws IOException {
        int counter = 0;
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        setBackground(new Background(getParquetBackground()));

        Image banner = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("banner 1386x400px.png")).openStream());
        ImageView bannerView = new ImageView(banner);
        bannerView.setFitHeight(300);
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

        Font font = new Font("Poor Richard", 26);
        Font font1 = new Font("Poor Richard", 22);
        Font font2 = new Font("Poor Richard", 19);
        VBox vBox3 = new VBox();
        vBox3.setMaxSize(600, 400);
        for (int i=(clientView.getPlayerPointsViews().length-1); i>-1; i--) {
            VBox vBox = new VBox();

            Label label = new Label(clientView.getPlayerPointsViews()[i].getNickname());
            if (clientView.getPlayerPointsViews()[i].getNickname().equals(clientView.getTurnPlayer())) {
                label.setFont(font1); label.setTextFill(Color.GOLD);
            } else {
                label.setFont(font1); label.setTextFill(Color.WHITE);
            }

            Label label5 = new Label( clientView.getPlayerPointsViews()[i].getPoints()+"  Points");
            label5.setFont(font1); label5.setTextFill(Color.WHITE);

            if (Objects.equals(clientView.getPlayerPointsViews()[i].getNickname(), clientView.getNickname())) {
                int sumToken = 0;
                for (int j =0; j<clientView.getCommonGoalView().length; j++) {
                    sumToken += clientView.getPlayerPointsViews()[i].getPointsToken()[j];
                }
                Label label1 = new Label("Common goals: "+sumToken+"   Adjacent tiles: "+clientView.getPlayerPointsViews()[i].getAdjacentPoints()+"   Personal goal: "+clientView.getPersonalPoints());
                label1.setFont(font2); label1.setTextFill(Color.WHITE);
                vBox.getChildren().addAll(label5,label1);
            } else {
                int sumToken = 0;
                for (int j =0; j<clientView.getCommonGoalView().length; j++) {
                    sumToken += clientView.getPlayerPointsViews()[i].getPointsToken()[j];
                }
                Label label1 = new Label("Common goals: "+sumToken+"   Adjacent tiles: "+clientView.getPlayerPointsViews()[i].getAdjacentPoints()+"   Personal goal: ?");
                label1.setFont(font2); label1.setTextFill(Color.WHITE);
                vBox.getChildren().addAll(label5,label1);
            }

            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);
            Label label2 = new Label((counter +1)+""); label2.setFont(font);
            Color[] colors = new Color[]{Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.LIGHTPINK};
            label2.setTextFill(colors[counter]);
            HBox hBox = new HBox(label2, label, vBox);
            hBox.setMaxSize(600, 400);
            hBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: #805126; -fx-background-radius: 8px; -fx-smooth: true");
            vBox3.getChildren().addAll(hBox);
            counter++;
        }

        vBox3.setSpacing(10);
        vBox3.setAlignment(Pos.CENTER);
        getChildren().add(vBox3);
        setAlignment(vBox3, Pos.CENTER);
        vBox3.setTranslateY(150);

        Label label = new Label("THE BOARD:"); label.setFont(font); label.setTextFill(Color.YELLOW);
        VBox vBox = new VBox(label, board);
        vBox.setSpacing(15);
        vBox.setMaxSize(getX(), getY()+30);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPickOnBounds(false);
        vBox.setTranslateX(30);
        vBox.setTranslateY(-30);

        VBox vBox1 = new VBox();
        vBox1.setMaxSize(screenBounds.getWidth()*0.2, screenBounds.getHeight()*0.2);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(20);
        for (int i = 0; i<clientView.getCommonGoalView().length; i++) {
            Label label2 = new Label("Common Goal Card "+(i+1)+" points left:    "+clientView.getCommonGoalView()[1][i]); label2.setFont(font1); label2.setTextFill(Color.WHITE);
            vBox1.getChildren().addAll(label2);
            if (clientView.getCommonGoalView()[1][i]!= 0) {
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("scoring_" + clientView.getCommonGoalView()[1][i] + ".jpg")).openStream()));
                imageView.setFitWidth(screenBounds.getWidth() * 0.15);
                imageView.setFitHeight(screenBounds.getHeight() * 0.15);
                imageView.setPreserveRatio(true);
                vBox1.getChildren().add(imageView);
            } else {
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("scoring_back_EMPTY.jpg")).openStream()));
                imageView.setFitWidth(screenBounds.getWidth() * 0.15);
                imageView.setFitHeight(screenBounds.getHeight() * 0.15);
                imageView.setPreserveRatio(true);
                vBox1.getChildren().add(imageView);
            }
        }

        getChildren().addAll(vBox1, vBox);
        setAlignment(vBox, Pos.BOTTOM_LEFT);
        setAlignment(vBox1, Pos.BOTTOM_RIGHT);
        vBox1.setTranslateX(-100);
        vBox1.setTranslateY(-60);

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
    }
}



