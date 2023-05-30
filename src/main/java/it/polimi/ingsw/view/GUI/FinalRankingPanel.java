package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.view.ClientView;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.Objects;

public class FinalRankingPanel extends BasePanel{

    private final Font font = new Font("Poor Richard", 17);
    Color[] colors= new Color [] {Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.LIGHTPINK};
    private final String style1 = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: white;";
    private final String styleReset = "-fx-border-color: rgba(255,255,0,0.65); -fx-border-width: 2px; -fx-border-radius: 4px; -fx-effect: dropshadow(three-pass-box, rgba(255,255,0,0.65), 6, 0, 0, 0); -fx-background-color: rgba(0,0,0,0.58); -fx-text-fill: red;";

    /**
     * Creates the panel to be shown when tha game is over. it shows the
     * final ranking and the possibility to go back to the global lobby
     * @param clientView : Client infos
     * @param personalPoints : personal goal points of all the players
     */
    public FinalRankingPanel(ClientView clientView, int[] personalPoints) throws IOException {
        int counter = 0;

        setBackground(new Background(getParquetBackground()));
        Font font4 = new Font("Poor Richard", 60);
        Label label4 = new Label("FINAL RANKING"); label4.setFont(font4); label4.setTextFill(Color.YELLOW);
        getChildren().add(label4); setAlignment(label4, Pos.TOP_CENTER); label4.setTranslateY(80);

        Font font = new Font("Poor Richard", 33);
        Font font1 = new Font("Poor Richard", 40);
        Font font2 = new Font("Poor Richard", 24);
        VBox vBox3 = new VBox();
        vBox3.setSpacing(40);
        vBox3.setAlignment(Pos.CENTER);
        ImageView winner = new ImageView(new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("end game.jpg")).openStream()));
        winner.setFitWidth(200);
        winner.setFitHeight(200);
        getChildren().add(winner);
        setAlignment(winner, Pos.CENTER_LEFT);
        winner.setTranslateX(50);
        for (int i=(clientView.getPlayerPointsViews().length-1); i>-1; i--) {
            VBox vBox = new VBox();
            Label label = new Label(clientView.getPlayerPointsViews()[i].getNickname());
            label.setFont(font1); label.setTextFill(Color.WHITE);
            Label label5 = new Label( (clientView.getPlayerPointsViews()[i].getPoints()+personalPoints[i])+"  Points");
            label5.setFont(font); label5.setTextFill(Color.WHITE);
            int sumToken = 0;
            for (int j =0; j<clientView.getCommonGoalView().length; j++) {
                sumToken += clientView.getPlayerPointsViews()[i].getPointsToken()[j];
            }
            Label label1 = new Label("Common goals: "+sumToken+"   Adjacent tiles: "+clientView.getPlayerPointsViews()[i].getAdjacentPoints()+"   Personal goal: "+personalPoints[i]);
            label1.setFont(font2); label1.setTextFill(Color.WHITE);
            vBox.getChildren().addAll(label5,label1);
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER);
            Label label2 = new Label((counter +1)+""); label2.setFont(font1); label2.setTextFill(colors[counter]);
            HBox hBox = new HBox(label2, label, vBox);
            hBox.setMaxSize(700, 400);
            hBox.setSpacing(40);
            hBox.setAlignment(Pos.CENTER);
            hBox.setStyle("-fx-background-color: #805126; -fx-background-radius: 8px; -fx-smooth: true");
            vBox3.getChildren().addAll(hBox);
            counter++;
        }
        Button button = new Button("Return to the lobby");
        mouseStyle(button, font, style1, styleReset);
        vBox3.getChildren().add(button);
        button.setAlignment(Pos.CENTER);
        button.setOnAction(actionEvent -> Platform.runLater(()-> {
            try {
                clientView.receiveEndGame();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }));
        getChildren().add(vBox3);
        setAlignment(vBox3, Pos.CENTER);

    }
}
