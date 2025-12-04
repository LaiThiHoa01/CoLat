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
import java.io.IOException;

public class ChoosePlayController {

    @FXML
    private JFXButton btnBack;

    @FXML
    private JFXButton buttonNvsM; // Máy với Máy

    @FXML
    private JFXButton buttonNvsN; // Người với Máy

    @FXML
    private JFXRadioButton rdTurn; // Checkbox đi trước

    // --- CÁC PHƯƠNG THỨC ACTION ---

    @FXML
    void backToHome(ActionEvent event) {
        switchScene(event, "/view/HomePage.fxml");
    }

    @FXML
    void choosePvM(ActionEvent event) {
        // Xử lý chọn Người vs Máy
        boolean isHumanFirst = rdTurn.isSelected();
        System.out.println("Chế độ: Người vs Máy. Người đi trước: " + isHumanFirst);

        // Lưu cài đặt game (bạn có thể tạo 1 class GameSettings static để lưu)
        // GameSettings.setMode(PlayerMode.HUMAN_VS_MACHINE);

        // Chuyển sang chọn độ khó
        switchScene(event, "/view/playmode.fxml");
    }

    @FXML
    void chooseMvM(ActionEvent event) {
        // Xử lý chọn Máy vs Máy
        System.out.println("Chế độ: Máy vs Máy");

        // Vào thẳng game hoặc chọn độ khó tuỳ logic của bạn
        switchScene(event, "/view/othello.fxml");
    }

    // Hàm chuyển cảnh chung
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