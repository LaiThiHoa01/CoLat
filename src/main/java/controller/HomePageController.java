package controller;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private JFXButton btnExitGame;

    @FXML
    private ImageView btnSound;

    @FXML
    private JFXButton btnguide;

    @FXML
    private JFXButton btnstart;
    private MediaPlayer musicPlayer;
    private boolean isMuted = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playBackgroundMusic();

    }
    private void playBackgroundMusic() {
        try {
            URL musicResource = getClass().getResource("/sound/nhacnen.mp3");

            if (musicResource != null) {
                Media media = new Media(musicResource.toString());
                musicPlayer = new MediaPlayer(media);

                musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
                musicPlayer.setVolume(0.2);
                musicPlayer.play();
            } else {
                System.err.println("Không tìm thấy file nhạc nền ");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi tải nhạc nền: " + e.getMessage());
        }
    }

    public void exitGame(ActionEvent actionEvent) {
        if (musicPlayer != null) {
            musicPlayer.stop();
        }
        Platform.exit();
        System.exit(0);


    }
    public void instructions(ActionEvent actionEvent) {
        switchScene(actionEvent,"/view/rules.fxml");

    }

    public void sound(ActionEvent actionEvent) {
        isMuted = !isMuted;
        if (musicPlayer != null) {
            musicPlayer.setMute(isMuted);
        }
        updateSoundIcon();
    }
    private void updateSoundIcon() {
        try {
            String imagePath;
            if (isMuted) {
                imagePath = "/img/btnMuteSound.png";
            } else {
                imagePath = "/img/btnSound.png";
            }
            Image newImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            btnSound.setImage(newImage);

        } catch (Exception e) {
            System.err.println("Lỗi không tìm thấy icon âm thanh: " + e.getMessage());
        }
    }

    public void startGame(ActionEvent actionEvent) {
        switchScene(actionEvent, "/view/chooseplay.fxml");

    }
    private void switchScene(ActionEvent event, String fxmlPath) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlPath)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Lỗi không thể tải file FXML: " + fxmlPath);
        } catch (NullPointerException e) {
            System.err.println("Sai đường dẫn FXML: " + fxmlPath);
        }
    }


}
