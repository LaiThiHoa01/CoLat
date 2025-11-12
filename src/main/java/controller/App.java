package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class App extends Application {
    @Override
    // Trong App.java
    public void start(Stage primaryStage) throws IOException {
        // Phải có dấu "/" ở đầu
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/index.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);

        primaryStage.initStyle(StageStyle.UNDECORATED); // Sử dụng primaryStage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }

}
