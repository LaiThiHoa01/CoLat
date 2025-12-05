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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML private BorderPane boardGame;
    @FXML private GridPane boardGrid;
    @FXML private JFXButton btnHome;
    @FXML private JFXButton btnReplay;
    @FXML private Label countBlack;
    @FXML private Label countWhite;
    @FXML private VBox vbMenuGame;

    private Board board;
    private int currentPlayer;

    // bot
    private AIPlayer aiPlayer1;
    private AIPlayer aiPlayer2;

    private boolean isAiVsAi = false;
    private boolean isGameRunning = true;
    private boolean isBlockingInput = false;

    private StackPane[][] cells = new StackPane[8][8];

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createBoardUI();
        startNewGame();
    }

    private void startNewGame() {
        // 1. Reset Model
        board = Board.boardInitial();
        currentPlayer = Board.black;
        isGameRunning = true;
        isBlockingInput = false;
//lay do kho
        int mode = PlayMode.gameMode;
        int depth = (PlayMode.difficultyDepth > 0) ? PlayMode.difficultyDepth : 4;

        if (mode == 2) {
            isAiVsAi = true;
            isBlockingInput = true;
            aiPlayer1 = new Minimax_AlphaBeta(depth, true);
            aiPlayer2 = new Minimax_AlphaBeta(depth, true);

            updateBoardView();
            updateScore();

            startAiVsAiLoop();

        } else {
            isAiVsAi = false;
            aiPlayer2 = new Minimax_AlphaBeta(depth, true);
            updateBoardView();
            updateScore();
        }
    }
// may vs may
    private void startAiVsAiLoop() {
        Thread aiThread = new Thread(() -> {
            while (isGameRunning && !board.checkEnd()) {
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) { e.printStackTrace(); }
                AIPlayer currentBot = (currentPlayer == Board.black) ? aiPlayer1 : aiPlayer2;
                Move bestMove = currentBot.getBestMove(board, currentPlayer);

                // Cập nhật UI trên Main Thread
                Platform.runLater(() -> {
                    if (!isGameRunning) return; //
                    if (!bestMove.isPass()) {
                        executeMove(bestMove);
                    } else {
                        System.out.println("Bot Pass lượt!");
                        currentPlayer = -currentPlayer;
                        updateBoardView();
                    }
                    if (board.checkEnd()) {
                        handleGameOver();
                    }
                });
                try { Thread.sleep(100); } catch (InterruptedException e) {}
            }
        });
        aiThread.setDaemon(true);
        aiThread.start();
    }
// nguoi vs may
    private void handleCellClick(int row, int col) {
        if (isAiVsAi || currentPlayer == Board.white || isBlockingInput || !isGameRunning) {
            return;
        }
        List<Move> legalMoves = board.legalMoves(currentPlayer);
        if (!isLegalMove(legalMoves, row, col)) {
            System.out.println("Nước đi không hợp lệ!");
            return;
        }
        executeMove(new Move(row, col));

        if (board.checkEnd()) {
            handleGameOver();
            return;
        }
        checkPassOrRunBot();
    }

    // goi bot
    private void runBotPvE() {
        isBlockingInput = true;
        updateBoardView();
        new Thread(() -> {
            try { Thread.sleep(700); } catch (InterruptedException e) {}
            Move bestMove = aiPlayer2.getBestMove(board, currentPlayer);

            Platform.runLater(() -> {
                if (!bestMove.isPass()) {
                    executeMove(bestMove);
                } else {
                    System.out.println("Máy không có nước đi -> Pass");
                    currentPlayer = -currentPlayer;
                }
                isBlockingInput = false;
                updateBoardView();

                if (board.checkEnd()) handleGameOver();
                else checkPassOrRunBot();
            });
        }).start();
    }
//    goi bot or pass
    private void checkPassOrRunBot() {
        List<Move> moves = board.legalMoves(currentPlayer);

        if (moves.size() == 1 && moves.get(0).isPass()) {
            System.out.println("Lượt hiện tại bị Pass!");
            currentPlayer = -currentPlayer;
            updateBoardView();

            if (currentPlayer == Board.white) {
                runBotPvE();
            }
        } else {
            if (currentPlayer == Board.white) {
                runBotPvE();
            }
        }
    }

    private void executeMove(Move move) {
        board = board.applyMove(move, currentPlayer);
        currentPlayer = -currentPlayer;
        updateScore();
        updateBoardView();
    }

    private void createBoardUI() {
        boardGrid.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = new StackPane();

                final int r = row;
                final int c = col;
                cell.setOnMouseClicked(e -> handleCellClick(r, c));

                boardGrid.add(cell, col, row);
                cells[row][col] = cell;
            }
        }
    }

    private void updateBoardView() {
        List<Move> legalMoves = board.legalMoves(currentPlayer);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                StackPane cell = cells[row][col];
                cell.getChildren().clear();
                int value = board.get(row, col);
                if (value != Board.empty) {
                    placePiece(cell, value);
                }
                else {
//                    goi y nuoc di
                    if (!isAiVsAi && currentPlayer == Board.black && !isBlockingInput) {
                        if (isLegalMove(legalMoves, row, col)) {
                            Circle hint = new Circle(8, Color.rgb(0, 0, 0, 0.2));
                            cell.getChildren().add(hint);
                        }
                    }
                }
            }
        }
    }

    private void placePiece(StackPane cell, int value) {
        Circle piece = new Circle(18);
        if (value == Board.black) {
            piece.setFill(Color.BLACK);
            piece.setStroke(Color.GRAY);
        } else {
            piece.setFill(Color.WHITE);
            piece.setStroke(Color.GRAY);
        }
        cell.getChildren().add(piece);
    }

    private boolean isLegalMove(List<Move> moves, int r, int c) {
        for (Move m : moves) {
            if (m.x == r && m.y == c) return true;
        }
        return false;
    }

    private void updateScore() {
        // diem
        int blackScore = board.count(Board.black);
        int whiteScore = board.count(Board.white);

        Platform.runLater(() -> {
            countBlack.setText(String.valueOf(blackScore));
            countWhite.setText(String.valueOf(whiteScore));
        });
    }

    private void handleGameOver() {
        isGameRunning = false;
        int black = board.count(Board.black);
        int white = board.count(Board.white);
        String result;
        if (black > white) result = "Đen thắng!";
        else if (white > black) result = "Trắng thắng!";
        else result = "Hòa!";

        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Kết thúc ván đấu");
            alert.setHeaderText(result);
            alert.setContentText("Tỉ số: Đen " + black + " - Trắng " + white);
            alert.showAndWait();
        });
    }

    @FXML
    void backToHome(ActionEvent event) {
        isGameRunning = false;
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/trangchu.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void replayGame(ActionEvent event) {
        isGameRunning = false;
        startNewGame();
    }
}