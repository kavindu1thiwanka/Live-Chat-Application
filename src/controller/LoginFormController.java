package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginFormController{
    public JFXTextField txtUsername;
    public AnchorPane root;
    public static String userName;

    public void loginOnAction(ActionEvent actionEvent) throws IOException {
        userName=txtUsername.getText();
        Stage stage = (Stage) root.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/ClientForm.fxml"))));
    }

    public void closeProgramOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

}
