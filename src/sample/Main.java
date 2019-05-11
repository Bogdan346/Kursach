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
        String css = "my.css";
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle(";)");

        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(0,css  );

        primaryStage.setScene(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
