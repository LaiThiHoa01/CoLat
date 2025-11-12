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

    // 1. Thêm @FXML để JavaFX "tiêm" (inject) các nút từ FXML vào đây
    //... (bên trong lớp Start)

    @FXML
    public JFXButton start;
    @FXML
    public JFXButton huongdan; // Nút này
    @FXML
    public JFXButton exit;

//    @FXML
//    public void initialize() {
//
//        // (Code cho nút 'start' đã có)
//        start.setOnAction(event -> {
//            try {
//                switchToHome();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        // (Code cho nút 'exit' đã có)
//        exit.setOnAction(event -> {
//            Stage stage = (Stage) exit.getScene().getWindow();
//            stage.close();
//        });
//
//        // ===============================================
//        // ==  CODE MỚI CHO NÚT HƯỚNG DẪN  ==
//        // ===============================================
//        huongdan.setOnAction(event -> {
//            // Tạo một hộp thoại (Alert) kiểu THÔNG TIN
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Hướng dẫn chơi Othello");
//            alert.setHeaderText("Luật chơi Cờ Lật (Othello)"); // Tiêu đề phụ
//
//            // Nội dung hướng dẫn
//            String content = "1. Đen đi trước. Bạn phải đặt quân cờ sao cho nó 'kẹp' được ít nhất một quân cờ của đối thủ.\n\n"
//                    + "2. 'Kẹp' có nghĩa là quân cờ mới của bạn và một quân cờ khác của bạn (đã có trên bàn) nằm ở hai đầu của một hàng (ngang, dọc, hoặc chéo) gồm các quân cờ của đối thủ.\n\n"
//                    + "3. Tất cả quân cờ của đối thủ bị kẹp sẽ bị lật lại thành màu của bạn.\n\n"
//                    + "4. Nếu không thể đi được nước nào, bạn sẽ bị mất lượt.\n\n"
//                    + "5. Trò chơi kết thúc khi bàn cờ đầy hoặc không ai có thể đi được nữa. Ai có nhiều quân cờ hơn sẽ thắng.";
//
//            alert.setContentText(content);
//
//            // Cho phép thay đổi kích thước cửa sổ pop-up nếu nội dung quá dài
//            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
//            // 2. Đặt cho cửa sổ đó có thể thay đổi kích thước
//            stage.setResizable(true);
//            // --- KẾT THÚC ---
//
//            // Dòng này của bạn không cần thiết nữa
//            // alert.getDialogPane().setResizable(true);
//
//            // Hiển thị pop-up
//            // Hiển thị pop-up và chờ người dùng đóng nó
//            alert.showAndWait();
//        });
//        // ===============================================
//    }
//
//    // (Code cho hàm 'switchToHome()' đã có)
//    private void switchToHome() throws IOException {
//        // Tải tệp FXML của màn hình chọn chế độ chơi
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
//        Parent homeRoot = loader.load();
//
//        // Lấy Stage (cửa sổ) hiện tại từ bất kỳ nút nào (ví dụ: nút 'start')
//        Stage stage = (Stage) start.getScene().getWindow();
//
//        // Tạo một Scene mới với nội dung FXML vừa tải
//        Scene scene = new Scene(homeRoot);
//
//        // Gán Scene mới cho Stage (thay thế màn hình hiện tại)
//        stage.setScene(scene);
//
//        // (Tùy chọn) Căn giữa cửa sổ nếu kích thước thay đổi
//        stage.centerOnScreen();
//    }

    public void Start(ActionEvent actionEvent) {
        try {
            // 1. Lấy Stage (cửa sổ) hiện tại từ sự kiện (nút đã được nhấn)
            Node source = (Node) actionEvent.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            // 2. Tải FXML mới và chuyển Scene
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

        // Dùng TextArea để nội dung có thể cuộn được
        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(450, 250);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }

    public void Exit(ActionEvent actionEvent) {
        System.out.println("===== NÚT THOÁT ĐÃ ĐƯỢC NHẤN! =====");
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}