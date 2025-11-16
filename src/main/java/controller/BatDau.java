package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;

public class BatDau {

    @FXML
    public JFXButton start;
    @FXML
    public JFXButton huongdan;
    @FXML
    public JFXButton exit;

    public void Start(ActionEvent actionEvent) {
        try {
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent homeRoot = loader.load();
            Scene scene = new Scene(homeRoot);
            stage.setScene(scene);
            stage.centerOnScreen();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Lỗi: Không thể tải tệp home.fxml");
        }
    }

    public void Guide(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hướng dẫn chơi Othello");
        alert.setHeaderText("Luật chơi Cờ Lật (Othello)");

        String content = "1. Đen đi trước. Bạn phải đặt quân cờ sao cho nó 'kẹp' được ít nhất một quân cờ của đối thủ.\n\n"
                + "2. 'Kẹp' có nghĩa là quân cờ mới của bạn và một quân cờ khác của bạn (đã có trên bàn) nằm ở hai đầu của một hàng (ngang, dọc, hoặc chéo) gồm các quân cờ của đối thủ.\n\n"
                + "3. Tất cả quân cờ của đối thủ bị kẹp sẽ bị lật lại thành màu của bạn.\n\n"
                + "4. Nếu không thể đi được nước nào, bạn sẽ bị mất lượt.\n\n"
                + "5. Trò chơi kết thúc khi bàn cờ đầy hoặc không ai có thể đi được nữa. Ai có nhiều quân cờ hơn sẽ thắng.";

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(450, 250);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    public void Exit(ActionEvent actionEvent) {
        System.out.println("Exit");
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}