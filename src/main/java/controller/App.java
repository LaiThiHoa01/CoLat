package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Tên file FXML của bạn (giả sử tên là "MainScene.fxml" và nằm trong classpath)
        // Nếu file FXML nằm trong src/main/resources, bạn dùng "/"
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/colat.fxml"));

        // Đặt Controller là class VideoBackgroundApp
        loader.setControllerFactory(c -> {
            if (c.equals(Game.class)) {
                return new Game();
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
        primaryStage.setTitle("Video Background Demo");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}