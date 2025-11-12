package controller;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import model.Othello;

import java.util.List;

public class Game {
    @FXML
    private GridPane boardGrid;

    // Model
    private Othello gameModel;

    // View
    private StackPane[][] cells = new StackPane[8][8]; // Mảng lưu các ô StackPane của UI

    /**
     * Phương thức này được JavaFX gọi sau khi FXML được tải.
     */
    public void initialize() {
        this.gameModel = new Othello(); // Khởi tạo Model
        createBoardUI(); // Tạo UI
        updateBoardView(); // Vẽ trạng thái ban đầu của bàn cờ
        updateTurnDisplay();
    }

    /**
     * Chỉ tạo các thành phần UI (StackPane) và thêm sự kiện click.
     */
    private void createBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("game-cell");

                final int finalRow = row;
                final int finalCol = col;
                // Xử lý sự kiện click
                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));

                boardGrid.add(cell, col, row);
                cells[row][col] = cell;
            }
        }
    }

    /**
     * Đọc dữ liệu từ Model và cập nhật giao diện (View).
     * Dùng để vẽ toàn bộ bàn cờ lúc bắt đầu.
     */
    private void updateBoardView() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String pieceMark = gameModel.getPieceAt(row, col);

                if (pieceMark != null) {
                    String color = pieceMark.equals(Othello.BLACK) ? "black" : "white";
                    placePiece(row, col, color);
                } else {
                    // Đảm bảo ô trống
                    cells[row][col].getChildren().clear();
                }
            }
        }
    }

    /**
     * Được gọi khi người dùng nhấp vào một ô.
     */
    private void handleCellClick(int row, int col) {
        // 1. Hỏi Model xem nước đi có hợp lệ không
        if (!gameModel.isMoveValid(row, col)) {
            System.out.println("Nước đi không hợp lệ !");
            return;
        }

        // Lấy màu của lượt đi này (TRƯỚC KHI đổi lượt)
        String colorToDraw = gameModel.isBlackTurn() ? "black" : "white";

        // 2. Yêu cầu Model thực hiện nước đi
        // Model sẽ cập nhật trạng thái nội bộ và trả về danh sách các ô đã thay đổi
        List<int[]> changedPieces = gameModel.makeMove(row, col);

        // 3. Cập nhật View (UI) dựa trên các ô đã thay đổi
        for (int[] pos : changedPieces) {
            placePiece(pos[0], pos[1], colorToDraw);
        }

        // 4. Cập nhật thông báo lượt đi
        updateTurnDisplay();
    }

    /**
     * Hiển thị thông báo lượt đi (hiện tại đang in ra console).
     */
    private void updateTurnDisplay() {
        // Hỏi Model xem đang là lượt của ai
        String turnText = gameModel.isBlackTurn() ? "Lượt của: Đen (Người chơi)" : "Lượt của: Trắng (Máy)";
        System.out.println("--- " + turnText + " ---");
    }

    /**
     * Phương thức tiện ích: Chỉ vẽ một quân cờ (Circle) lên ô (StackPane).
     * **Quan trọng:** Không còn cập nhật mảng `board` nữa.
     */
    private void placePiece(int row, int col, String color) {
        // Dòng này đã bị XÓA: board[row][col] = color.equals("black") ? "B" : "W";

        StackPane cell = cells[row][col];
        cell.getChildren().clear(); // Xóa quân cờ cũ (nếu có)

        Circle piece = new Circle(25); // Kích thước bán kính quân cờ
        if ("black".equals(color)) {
            piece.getStyleClass().add("piece-black");
        } else {
            piece.getStyleClass().add("piece-white");
        }
        cell.getChildren().add(piece);
    }
}
