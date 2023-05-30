package it.polimi.ingsw.view.GUI;

import it.polimi.ingsw.network.client.ClientHandler;
import it.polimi.ingsw.view.ClientView;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class LobbyPanel extends BasePanel{

    private String nickname;
    private String port;
    private String ip;
    private final String defaultIp = "127.0.0.1";
    public LobbyPanel(ClientView clientView) throws IOException {
        setBackground(new Background(getLobbyBackground()));

        getChildren().addAll(getTitle(), getPublisher());
        setAlignment(getChildren().get(0), Pos.TOP_CENTER);
        setAlignment(getChildren().get(1), Pos.BOTTOM_RIGHT);

        TextField textField = new TextField("Insert your nickname here");
        textField.setPrefSize(220, 35);
        textField.setOnMouseClicked(mouseEvent -> textField.selectAll());
        Button confirmButton = new Button("Set Nickname");
        Button confirmButton1 = new Button("Set Socket Connection"); confirmButton1.setDisable(true);
        Button confirmButton2 = new Button("Set RMI Connection"); confirmButton2.setDisable(true);
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
                confirmButton1.setDisable(false);
                confirmButton2.setDisable(false);
            }
        });
        HBox hBox = new HBox(textField, confirmButton);
        hBox.setSpacing(5);

        TextField textField1 = new TextField("Insert port (Leave empty for default)");
        textField1.setPrefSize(220, 35);
        textField1.setOnMouseClicked(mouseEvent -> textField1.selectAll());
        TextField textField2 = new TextField("Insert ip (Leave empty for default)");
        textField2.setOnMouseClicked(mouseEvent -> textField2.selectAll());
        textField2.setPrefSize(220, 35);
        confirmButton1.setOnAction(actionEvent -> {
                    port = textField1.getText();
                    ip = textField2.getText();
                    //int chosenPort = (port.isEmpty() ? 1100 : Integer.parseInt(port));
            int chosenPort2 = 0;
            try {
                int chosenPort = (port.isEmpty() ? 1100 : Integer.parseInt(port));
                if (chosenPort >= 1024 && chosenPort <= 65535) {
                   chosenPort2 = chosenPort;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid port number!");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a port number between 1024 and 65535");
                    alert.show();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid port number!");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid port number");
                alert.show();
            }
            String chosenIp = (ip.isEmpty() ? defaultIp : ip);
                    ClientHandler clientHandler = new ClientHandler();
                    try {
                        clientHandler.createConnection(0, chosenIp, chosenPort2, GUIApplication.guiApplicationStatic);
                    } catch (Exception e) {
                        GUIApplication.guiApplicationStatic.askNicknameAndConnection();
                    }
                });

        HBox hBox1 = new HBox(textField1, textField2, confirmButton1);
        hBox1.setSpacing(5);

        TextField textField3 = new TextField("Insert port (Leave empty for default)");
        textField3.setOnMouseClicked(mouseEvent -> textField3.selectAll());
        textField3.setPrefSize(220, 35);
        TextField textField4 = new TextField("Insert ip (Leave empty for default)");
        textField4.setOnMouseClicked(mouseEvent -> textField4.selectAll());
        textField4.setPrefSize(220, 35);
        confirmButton2.setOnAction(actionEvent -> {
            port = textField3.getText();
            ip = textField4.getText();
            int chosenPort2 = 0;
            try {
                int chosenPort = (port.isEmpty() ? 1100 : Integer.parseInt(port));
                if (chosenPort >= 1024 && chosenPort <= 65535) {
                    chosenPort2 = chosenPort;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Invalid port number!");
                    alert.setHeaderText(null);
                    alert.setContentText("Please enter a port number between 1024 and 65535");
                    alert.show();
                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid port number!");
                alert.setHeaderText(null);
                alert.setContentText("Please enter a valid port number");
                alert.show();
            }
            String chosenIp = (ip.isEmpty() ? defaultIp : ip);
            ClientHandler clientHandler = new ClientHandler();
            try {
                clientHandler.createConnection(1, chosenIp, chosenPort2, GUIApplication.guiApplicationStatic);
            } catch (Exception e) {
                GUIApplication.guiApplicationStatic.askNicknameAndConnection();
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
