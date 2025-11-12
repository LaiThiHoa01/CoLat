package model;

import java.util.ArrayList;
import java.util.List;

public class Othello {
    public static final String BLACK = "B";
    public static final String WHITE = "W";
    public static final String EMPTY = null;

    private String[][] board = new String[8][8];
    private boolean isBlackTurn = true;

    // Các hướng di chuyển
    private static final int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };

    /**
     * Hàm khởi tạo (Constructor)
     * Thiết lập trạng thái ban đầu của bàn cờ.
     */
    public Othello() {
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }

    // --- Phương thức Public cho Controller ---

    /**
     * Lấy quân cờ tại một vị trí cụ thể.
     * @return GameModel.BLACK, GameModel.WHITE, hoặc GameModel.EMPTY
     */
    public String getPieceAt(int row, int col) {
        return board[row][col];
    }

    /**
     * Kiểm tra xem có phải lượt của quân Đen không.
     */
    public boolean isBlackTurn() {
        return isBlackTurn;
    }

    /**
     * Lấy ký hiệu quân cờ của người chơi hiện tại ("B" hoặc "W").
     */
    public String getCurrentPlayerMark() {
        return isBlackTurn ? BLACK : WHITE;
    }

    /**
     * Lấy ký hiệu quân cờ của đối thủ.
     */
    public String getOpponentMark() {
        return isBlackTurn ? WHITE : BLACK;
    }

    /**
     * Kiểm tra xem một nước đi có hợp lệ không.
     */
    public boolean isMoveValid(int row, int col) {
        // Ô đã có quân cờ
        if (board[row][col] != EMPTY) {
            return false;
        }
        // Kiểm tra xem có thể lật được quân cờ nào không
        return canFlip(row, col, getCurrentPlayerMark());
    }

    /**
     * Thực hiện một nước đi.
     * Cập nhật bàn cờ và chuyển lượt.
     * @return Danh sách các ô đã bị thay đổi (bao gồm ô mới và các ô bị lật)
     */
    public List<int[]> makeMove(int row, int col) {
        String me = getCurrentPlayerMark();

        // Lấy danh sách các quân cờ sẽ bị lật và cập nhật chúng
        List<int[]> flipped = applyFlip(row, col, me);

        // Đặt quân cờ mới
        board[row][col] = me;
        flipped.add(new int[]{row, col}); // Thêm cả quân cờ vừa đặt vào danh sách

        // Đổi lượt
        isBlackTurn = !isBlackTurn;

        return flipped;
    }

    // --- Phương thức Private (Logic nội bộ) ---

    /**
     * Kiểm tra xem việc đặt quân cờ 'me' tại (row, col) có lật được quân nào không.
     */
    private boolean canFlip(int row, int col, String me) {
        String opp = me.equals(BLACK) ? WHITE : BLACK;

        for (int[] d : directions) {
            int r = row + d[0];
            int c = col + d[1];
            boolean seenOpp = false;

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] != EMPTY) {
                if (board[r][c].equals(opp)) {
                    seenOpp = true;
                } else if (board[r][c].equals(me) && seenOpp) {
                    return true; // Tìm thấy một hướng có thể lật
                } else {
                    break; // Gặp quân cờ của mình quá sớm hoặc một ô trống
                }
                r += d[0];
                c += d[1];
            }
        }
        return false; // Không tìm thấy hướng nào
    }

    /**
     * Áp dụng việc lật cờ và cập nhật mảng board.
     * @return Danh sách các quân cờ đã bị lật (chỉ các quân của đối thủ).
     */
    private List<int[]> applyFlip(int row, int col, String me) {
        String opp = me.equals(BLACK) ? WHITE : BLACK;
        List<int[]> totalFlipped = new ArrayList<>();

        for (int[] d : directions) {
            int r = row + d[0];
            int c = col + d[1];
            List<int[]> toFlip = new ArrayList<>();

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] != EMPTY) {
                if (board[r][c].equals(opp)) {
                    toFlip.add(new int[]{r, c});
                } else if (board[r][c].equals(me)) {
                    // Tìm thấy quân cờ của mình, lật tất cả quân cờ trong danh sách toFlip
                    for (int[] p : toFlip) {
                        board[p[0]][p[1]] = me; // Cập nhật model
                        totalFlipped.add(p);
                    }
                    break;
                } else {
                    break; // Gặp ô trống
                }
                r += d[0];
                c += d[1];
            }
        }
        return totalFlipped;
    }
}
