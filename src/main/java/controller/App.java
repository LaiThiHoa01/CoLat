package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/trangchu.fxml"));

        loader.setControllerFactory(c -> {
            if (c.equals(Test.class)) {
                return new Test();
            } else {
                try {
                    return c.getDeclaredConstructor().newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Parent root = loader.load();
        Scene scene = new Scene(root, 880, 495);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Othello");
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}