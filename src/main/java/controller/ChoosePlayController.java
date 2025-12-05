package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.PlayMode;

import java.io.IOException;

public class ChoosePlayController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton buttonNvsM;

    @FXML
    private JFXButton buttonNvsN;

    @FXML
    private JFXRadioButton rdTurn;


    @FXML
    void backToHome(ActionEvent event) {
        switchScene(event, "/view/HomePage.fxml");
    }

    @FXML
    void choosePvM(ActionEvent event) {
        PlayMode.gameMode = 1;
        boolean isHumanFirst = rdTurn.isSelected();
        System.out.println("Chế độ: Người vs Máy. Người đi trước: " + isHumanFirst);
        switchScene(event, "/view/playmode.fxml");
    }

    @FXML
    void chooseMvM(ActionEvent event) {
        PlayMode.gameMode = 2;
        System.out.println("Chế độ: Máy vs Máy");
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