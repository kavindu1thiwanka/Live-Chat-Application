package controller;

import com.jfoenix.controls.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ResourceBundle;

public class ClientFormController implements Initializable {
    public AnchorPane root;
    public JFXTextField txtMessage;
    public Text clientNameLabel;
    public ScrollPane scrollPane;
    public VBox vBox;
    public AnchorPane msgViewerPane;
    public JFXButton msgSendButton;
    public Pane emojiPane;
    public GridPane grid;
    private Client client;
    private Socket socket;

    private Boolean emojiBoxVisibility=false;
    private JFXButton button1,button2,button3,button4,button5;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            String userName = LoginFormController.userName;
            clientNameLabel.setText(userName);
            socket = new Socket("localhost",9955);
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

//Set Emojis

        byte[] smiling_eyes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x81};
        String emoji1 = new String(smiling_eyes, Charset.forName("UTF-8"));

        byte[] tears_of_joy = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x82};
        String emoji2 = new String(tears_of_joy, Charset.forName("UTF-8"));

        byte[] heart_shaped_eyes = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x8D};
        String emoji3 = new String(heart_shaped_eyes, Charset.forName("UTF-8"));

        byte[] winking_eye = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x9C};
        String emoji4 = new String(winking_eye, Charset.forName("UTF-8"));

        byte[] smiling_open_mouth = new byte[]{(byte)0xF0, (byte)0x9F, (byte)0x98, (byte)0x85};
        String emoji5 = new String(smiling_open_mouth, Charset.forName("UTF-8"));

        button1 = new JFXButton(emoji1);
        button2 = new JFXButton(emoji2);
        button3 = new JFXButton(emoji3);
        button4 = new JFXButton(emoji4);
        button5 = new JFXButton(emoji5);

        grid.setMinSize(190, 40);
        grid.setStyle("-fx-background-color: white");
        grid.setStyle("-fx-background-radius: 10");
        grid.setPadding(new Insets(2, 15, 2, 0));

        grid.setVgap(5);
        grid.setHgap(5);

        grid.setAlignment(Pos.CENTER_LEFT);

        grid.add(button1, 1, 0);
        grid.add(button2, 2, 0);
        grid.add(button3, 3, 0);
        grid.add(button4, 4, 0);
        grid.add(button5, 5, 0);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(),emoji1);
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(),emoji2);
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(),emoji3);
            }
        });

        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(),emoji4);
            }
        });

        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txtMessage.insertText(txtMessage.getText().length(),emoji5);
            }
        });

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



    public void openEmojiPanel(ActionEvent actionEvent) throws IOException {

        if (!emojiBoxVisibility){
            emojiPane.setVisible(true);
            emojiBoxVisibility=true;
        }else {
            emojiPane.setVisible(false);
            emojiBoxVisibility=false;
        }


    }

    public void sendImageOnAction(ActionEvent actionEvent) throws IOException, InterruptedException {

    }

    public void sendImage() throws IOException, InterruptedException {

    }

    public void receiveImage() throws IOException {

    }
}
