package com.example.colat;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class Controller {
    @FXML
    private GridPane boardGrid;
    private StackPane[][] cells = new StackPane[8][8];
    private boolean isBlackTurn = true;;
    public void initialize() {
        createBoard();
    }
    private void createBoard() {
        // 8 hàng (Row)
        for (int row = 0; row < 8; row++) {
            // 8 cột (Column)
            for (int col = 0; col < 8; col++) {
                // 1. Tạo ô cờ
                StackPane cell = new StackPane();
                // Gán CSS cho nền ô cờ
                cell.getStyleClass().add("game-cell");
                // 2. Click
                final int finalRow = row;
                final int finalCol = col;
                cell.setOnMouseClicked(e -> handleCellClick(finalRow, finalCol));
                boardGrid.add(cell, col, row);
                // 4. Lưu ô cờ vào mảng
                cells[row][col] = cell;
            }
        }
        placePiece(3, 3, "white");
        placePiece(3, 4, "black");
        placePiece(4, 3, "black");
        placePiece(4, 4, "white");

    }
    private void updateTurnDisplay() {
        String turnText = isBlackTurn ? "Lượt của: Đen (Người chơi)" : "Lượt của: Trắng (Máy)";
        //lblCurrentTurn.setText(turnText);

        System.out.println("--- " + turnText + " ---");
    }
    private void handleCellClick(int row, int col) {
        String currentColor = isBlackTurn ? "black" : "white";
// chỉ đặt được chưa làm các bước đi logic
        StackPane clickedCell = cells[row][col];
        if (clickedCell.getChildren().isEmpty()) {
            placePiece(row, col, currentColor);
//logic đặt cờ(chưa làm)
            isBlackTurn = !isBlackTurn;
            updateTurnDisplay();
            if (!isBlackTurn) {
            }
        } else {
            System.out.println("Ô [" + row + ", " + col + "] đã có quân cờ.");
        }
    }
    private void placePiece(int row, int col, String color) {
        StackPane cell = cells[row][col];
        cell.getChildren().clear();
        Circle piece = new Circle(25);
        if ("black".equals(color)) {
            piece.getStyleClass().add("piece-black");
        } else {
            piece.getStyleClass().add("piece-white");
        }
        cell.getChildren().add(piece);
    }

}


