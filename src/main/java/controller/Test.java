package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import model.BotAI; // Import BotAI
import model.Othello;

public class Test {
    @FXML
    private GridPane boardGrid;

    private Othello gameModel;
    private BotAI botAI; // Khai báo Bot
    private StackPane[][] cells = new StackPane[8][8];
    private boolean isInputAllowed = true; // Khóa bàn cờ khi máy đang nghĩ

    public void initialize() {
        this.gameModel = new Othello();
        this.botAI = new BotAI(); // Khởi tạo Bot
        createBoardUI();
        updateBoardView();
        updateTurnDisplay();
    }

    private void createBoardUI() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();
                cell.getStyleClass().add("game-cell");

                final int finalRow = row;
                final int finalCol = col;

                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));

                boardGrid.add(cell, col, row);
                cells[row][col] = cell;
            }
        }
    }

    private void updateBoardView() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                String pieceMark = gameModel.getPieceAt(row, col);
                cells[row][col].getChildren().clear(); // Xóa quân cũ

                if (pieceMark != null) {
                    String color = pieceMark.equals(Othello.BLACK) ? "black" : "white";
                    placePiece(row, col, color);
                }
            }
        }
    }

    private void handleCellClick(int row, int col) {
        // Nếu đang lượt máy hoặc game kết thúc thì không cho bấm
        if (!isInputAllowed || !gameModel.isBlackTurn()) return;

        if (!gameModel.isMoveValid(row, col)) {
            System.out.println("Nước đi không hợp lệ !");
            return;
        }

        // 1. Người chơi đánh
        performMove(row, col);

        // 2. Gọi AI đánh (Chạy trên luồng riêng để không đơ giao diện)
        if (!gameModel.isBlackTurn()) { // Nếu đến lượt Trắng
            isInputAllowed = false; // Khóa input
            new Thread(() -> {
                try {
                    Thread.sleep(100); // Giả vờ suy nghĩ 0.7s cho tự nhiên
                } catch (InterruptedException e) { e.printStackTrace(); }

                // Tìm nước đi tốt nhất (Độ sâu 4)
                int[] bestMove = botAI.findBestMove(gameModel, 8);

                // Cập nhật UI trên luồng JavaFX
                Platform.runLater(() -> {
                    if (bestMove != null) {
                        performMove(bestMove[0], bestMove[1]);
                    } else {
                        // Trường hợp máy không có nước đi (pass lượt)
                        System.out.println("Máy không có nước đi, chuyển lượt về người chơi.");
                        // Logic xử lý pass lượt (cần thêm vào model nếu muốn chặt chẽ)
                        // Tạm thời ta chỉ cần mở khóa input nếu game chưa kết thúc
                    }
                    isInputAllowed = true; // Mở khóa input
                    checkGameOver();
                });
            }).start();
        }
    }

    private void performMove(int row, int col) {
        String colorToDraw = gameModel.isBlackTurn() ? "black" : "white";
        gameModel.makeMove(row, col); // Model cập nhật logic
        updateBoardView();            // View vẽ lại toàn bộ bàn cờ
        updateTurnDisplay();
    }

    private void updateTurnDisplay() {
        String turnText = gameModel.isBlackTurn() ? "Lượt của: Đen (Người chơi)" : "Lượt của: Trắng (Máy)";
        System.out.println("--- " + turnText + " ---");
    }

    private void placePiece(int row, int col, String color) {
        StackPane cell = cells[row][col];
        Circle piece = new Circle(18);
        if ("black".equals(color)) {
            piece.getStyleClass().add("piece-black");
        } else {
            piece.getStyleClass().add("piece-white");
        }
        cell.getChildren().add(piece);
    }

    private void checkGameOver() {
        if (gameModel.getValidMoves().isEmpty()) {
            System.out.println("HẾT GAME!");
            // Bạn có thể thêm logic hiển thị Dialog thông báo thắng thua ở đây
        }
    }
}