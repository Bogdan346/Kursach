package sample;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(";)");

        Scene scene = new Scene(root, 881, 530);
        scene.getStylesheets().add(0,"my.css");

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(881);
      primaryStage.setMinHeight(530);

        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
