package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Screen;

import static java.lang.System.out;

public class LobbyPanel extends BasePanel{

    private String nickname;
    private String connection;
    private String defaultIp = "127.0.0.1";
    public LobbyPanel(ClientView clientView) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Display_5.jpg");
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));
        Image publisher = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\publisher.png");
        ImageView publisherView = new ImageView(publisher);
        Image title = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Title 2000x618px.png");
        ImageView titleView = new ImageView(title);
        titleView.setFitHeight(250);
        titleView.setPreserveRatio(true);
        getChildren().add(titleView);
        getChildren().add(publisherView);
        setAlignment(titleView, Pos.TOP_CENTER);
        setAlignment(publisherView, Pos.BOTTOM_RIGHT);

        TextField textField = new TextField("Insert your nickname here");
        textField.setPrefSize(400, 35);
        Button confirmButton = new Button("Set Nickname");
        confirmButton.setOnAction(actionEvent -> {
            nickname = textField.getText();
            if (nickname.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("No nickname founded");
                alert.setContentText("Please insert a nickname");
                alert.show();
            } else {
                clientView.setNickname(nickname);
            }
        });
        HBox hBox = new HBox(textField, confirmButton);

        TextField textField1 = new TextField("0->Socket, 1->RMI");
        textField.setPrefSize(400, 35);
        Button confirmButton1 = new Button("Set Connection");
        confirmButton1.setOnAction(actionEvent -> {
            connection = textField1.getText();
            if (connection.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("No connection founded");
                alert.setContentText("Please insert a connection");
                alert.show();
            } else {
                //int defaultPort = (1099);
                int defaultPort = (connection.equals("0") ? 1100 : 1099);
                ClientHandler clientHandler=new ClientHandler();
                try{
                    clientHandler.createConnection(Integer.parseInt(connection), defaultIp, defaultPort, GUIApplication.guiApplicationStatic);
                    out.println("Connection created");
                } catch (Exception e){
                    out.println("Error in creating connection. Please try again.\n");
                }
            }
        });
        HBox hBox1 = new HBox(textField1, confirmButton1);
        VBox vBox = new VBox(hBox, hBox1);
        hBox.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
    }
}
