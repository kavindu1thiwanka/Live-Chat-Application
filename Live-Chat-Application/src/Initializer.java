import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Initializer extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("view/LoginForm.fxml"))));
        primaryStage.centerOnScreen();
        primaryStage.setTitle("Play Tech Live Chat");
        primaryStage.getIcons().add(new Image("assets/icons8-robot.gif"));
        primaryStage.show();
    }
}
