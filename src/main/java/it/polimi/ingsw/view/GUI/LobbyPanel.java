package it.polimi.ingsw.view.GUI;

import com.sun.tools.javac.Main;
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

import java.io.IOException;
import java.util.Objects;

public class LobbyPanel extends BasePanel{

    private String nickname;
    private String port;
    private String ip;
    private String defaultIp = "127.0.0.1";
    public LobbyPanel(ClientView clientView) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        //Image backgroundImage = new Image("file:src\\main\\java\\it\\polimi\\ingsw\\Images\\Publisher material\\Display_5.jpg");
        Image backgroundImage = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Display_5.jpg")).openStream());
        BackgroundSize backgroundSize = new BackgroundSize(screenBounds.getWidth(), screenBounds.getHeight(), true, true, true, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        setBackground(new Background(background));
        Image publisher = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Publisher.png")).openStream());
        ImageView publisherView = new ImageView(publisher);
        Image title = new Image(Objects.requireNonNull(Main.class.getClassLoader().getResource("Title 2000x618px.png")).openStream());
        ImageView titleView = new ImageView(title);
        titleView.setFitHeight(250);
        titleView.setPreserveRatio(true);
        getChildren().add(titleView);
        getChildren().add(publisherView);
        setAlignment(titleView, Pos.TOP_CENTER);
        setAlignment(publisherView, Pos.BOTTOM_RIGHT);

        TextField textField = new TextField("Insert your nickname here");
        textField.setPrefSize(220, 35);
        textField.setOnMouseClicked(mouseEvent -> {
            textField.selectAll();
        });
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
        hBox.setSpacing(5);

        TextField textField1 = new TextField("Insert port (Leave empty for default)");
        textField1.setPrefSize(220, 35);
        textField1.setOnMouseClicked(mouseEvent -> {
            textField1.selectAll();
        });
        TextField textField2 = new TextField("Insert ip (Leave empty for default)");
        textField2.setOnMouseClicked(mouseEvent -> {
            textField2.selectAll();
        });
        textField2.setPrefSize(220, 35);
        Button confirmButton1 = new Button("Set Socket Connection");
        confirmButton1.setOnAction(actionEvent -> {
                    port = textField1.getText();
                    ip = textField2.getText();
                    int chosenPort = (port.isEmpty() ? 1100 : Integer.parseInt(port));
                    String chosenIp = (ip.isEmpty() ? defaultIp : ip);
                    ClientHandler clientHandler = new ClientHandler();
                    try {
                        clientHandler.createConnection(0, chosenIp, chosenPort, GUIApplication.guiApplicationStatic);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            /*if (port.isEmpty() && ip.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Error!");
                alert.setHeaderText("No connection founded");
                alert.setContentText("Please insert a valid number connection");
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

             */
        HBox hBox1 = new HBox(textField1, textField2, confirmButton1);
        hBox1.setSpacing(5);

        TextField textField3 = new TextField("Insert port (Leave empty for default)");
        textField3.setOnMouseClicked(mouseEvent -> {
            textField3.selectAll();
        });
        textField3.setPrefSize(220, 35);
        TextField textField4 = new TextField("Insert ip (Leave empty for default)");
        textField4.setOnMouseClicked(mouseEvent -> {
            textField4.selectAll();
        });
        textField4.setPrefSize(220, 35);
        Button confirmButton2 = new Button("Set RMI Connection");
        confirmButton2.setOnAction(actionEvent -> {
            port = textField3.getText();
            ip = textField4.getText();
            int chosenPort = (port.isEmpty() ? 1099 : Integer.parseInt(port));
            String chosenIp = (ip.isEmpty() ? defaultIp : ip);
            ClientHandler clientHandler = new ClientHandler();
            try {
                clientHandler.createConnection(1, chosenIp, chosenPort, GUIApplication.guiApplicationStatic);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        HBox hBox2 = new HBox(textField3, textField4, confirmButton2);
        hBox2.setSpacing(5);

        VBox vBox = new VBox(hBox, hBox1, hBox2);
        vBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);
        getChildren().add(vBox);
        vBox.setAlignment(Pos.CENTER);
    }
}
