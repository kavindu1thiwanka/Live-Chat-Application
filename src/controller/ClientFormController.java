package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtMessage;
    public Text clientNameLabel;
    public ScrollPane scrollPane;
    public VBox vBox;
    public AnchorPane msgViewerPane;
    public JFXButton msgSendButton;
    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            String userName = LoginFormController.userName;
            clientNameLabel.setText(userName);
            Socket socket = new Socket("localhost",9955);
            client=new Client(socket,userName);
            client.receiveMessage(vBox);
            client.send();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vBox.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                scrollPane.setVvalue((Double) newValue);
            }
        });

        client.receiveMessage(vBox);

//        msgSendButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                String msgToSend=txtMessage.getText();
//                if (!msgToSend.isEmpty()){
//                    HBox hBox = new HBox();
//                    hBox.setAlignment(Pos.CENTER_RIGHT);
//                    hBox.setPadding(new Insets(5,5,5,10));
//
//                    Text text=new Text(msgToSend);
//                    TextFlow textFlow=new TextFlow(text);
//                    textFlow.setStyle("-fx-color: rgb(239,242,255); " +
//                            "-fx-background-color: rgb(15,125,242);" +
//                            "-fx-background-radius: 20px");
//                    textFlow.setPadding(new Insets(5,10,5,10));
//                    text.setFill(Color.color(0.934,0.945,0.996));
//
//                    hBox.getChildren().add(textFlow);
//                    vBox.getChildren().add(hBox);
//
//                    client.send(msgToSend);
//                    txtMessage.clear();
//                }
//            }
//        });

    }

    public void sendMessageOnAction(ActionEvent actionEvent) {
        String msgToSend=txtMessage.getText();
        if (!msgToSend.isEmpty()){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5,5,5,10));

            Text text=new Text(msgToSend);
            text.setFill(Color.color(0.934,0.945,0.996));
            TextFlow textFlow=new TextFlow(text);
            textFlow.setStyle("-fx-color: rgb(239,242,255);" + "-fx-background-color: rgb(15,125,242);" + "-fx-background-radius: 20px");
            textFlow.setPadding(new Insets(5,10,5,10));

            hBox.getChildren().add(textFlow);
            vBox.getChildren().add(hBox);

            client.send(msgToSend);
            txtMessage.clear();
        }
    }

    public static void addLabel(String msg,VBox vBox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5,10,5,10));

        Text text=new Text(msg);
        TextFlow textFlow=new TextFlow(text);
        textFlow.setStyle("-fx-background-color: rgb(233,233,235); " +
                "-fx-background-radius: 20px");
        textFlow.setPadding(new Insets(5,10,5,10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vBox.getChildren().add(hBox);
            }
        });
    }

    public void openEmojiPanel(ActionEvent actionEvent) {
    }

    public void sendImageOnAction(ActionEvent actionEvent) {
    }
}
