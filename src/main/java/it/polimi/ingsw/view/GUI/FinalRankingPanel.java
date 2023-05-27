package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;

public class FinalRankingPanel extends BasePanel{

    Color[] colors= new Color [] {Color.YELLOW, Color.SILVER, Color.SANDYBROWN, Color.LIGHTPINK};

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
        for (int i=(clientView.getPlayerPointsViews().length-1); i>-1; i--) {
            VBox vBox = new VBox();
            Label label = new Label(clientView.getPlayerPointsViews()[i].getNickname());
            label.setFont(font1); label.setTextFill(Color.WHITE);
            Label label5 = new Label( clientView.getPlayerPointsViews()[i].getPoints()+"Points");
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
        getChildren().add(vBox3);
        setAlignment(vBox3, Pos.CENTER);
    }
}
