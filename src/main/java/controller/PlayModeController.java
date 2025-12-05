package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class PlayModeController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton buttonEasy;

    @FXML
    private JFXButton buttonHard;

    @FXML
    private BorderPane home;

    @FXML
    private Label textOption;


    public void backToHome(ActionEvent actionEvent) {
        switchScene(actionEvent, "/view/chooseplay.fxml");

        }
    @FXML
    void chooseEasy(ActionEvent event) {
        System.out.println("Độ khó: DỄ");
        model.PlayMode.setDifficulty(1);
        switchScene(event, "/view/othello.fxml");
    }

    @FXML
    void chooseHard(ActionEvent event) {
        System.out.println("Độ khó: KHÓ");
       model.PlayMode.setDifficulty(7);
        switchScene(event, "/view/othello.fxml");
    }

    private void switchScene(ActionEvent event, String path) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
