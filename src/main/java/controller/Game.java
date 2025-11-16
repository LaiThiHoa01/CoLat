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
    private StackPane[][] cells = new StackPane[8][8];

    public void initialize() {
        this.gameModel = new Othello();
        createBoardUI();
        updateBoardView();
        updateTurnDisplay();
    }
//     tạo UI bàn cờ
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

                if (pieceMark != null) {
                    String color = pieceMark.equals(Othello.BLACK) ? "black" : "white";
                    placePiece(row, col, color);
                } else {
                    cells[row][col].getChildren().clear();
                }
            }
        }
    }

    private void handleCellClick(int row, int col) {
        if (!gameModel.isMoveValid(row, col)) {
            System.out.println("Nước đi không hợp lệ !");
            return;
        }
        String colorToDraw = gameModel.isBlackTurn() ? "black" : "white";
        List<int[]> changedPieces = gameModel.makeMove(row, col);
        for (int[] pos : changedPieces) {
            placePiece(pos[0], pos[1], colorToDraw);
        }
        updateTurnDisplay();
    }
    private void updateTurnDisplay() {
        String turnText = gameModel.isBlackTurn() ? "Lượt của: Đen (Người chơi)" : "Lượt của: Trắng (Máy)";
        System.out.println("--- " + turnText + " ---");
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
