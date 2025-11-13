package model;

import java.util.ArrayList;
import java.util.List;

public class Othello {
    public static final String BLACK = "B";
    public static final String WHITE = "W";
    public static final String EMPTY = null;

    private String[][] board = new String[8][8];
    private boolean isBlackTurn = true;
// hướng di chuyển
    private static final int[][] directions = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1},           {0, 1},
            {1, -1},  {1, 0},  {1, 1}
    };
    public Othello() {
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }

    public String getPieceAt(int row, int col) {
        return board[row][col];
    }

    public boolean isBlackTurn() {
        return isBlackTurn;
    }
//  lượt đi
    public String getCurrentPlayerMark() {
        return isBlackTurn ? BLACK : WHITE;
    }

// nước đi hợp lệ
    public boolean isMoveValid(int row, int col) {
        if (board[row][col] != EMPTY) {
            return false;
        }
        // Kiểm tra xem có thể lật được quân cờ nào không
        return canFlip(row, col, getCurrentPlayerMark());
    }

    public List<int[]> makeMove(int row, int col) {
        String me = getCurrentPlayerMark();

        List<int[]> flipped = applyFlip(row, col, me);

        board[row][col] = me;
        flipped.add(new int[]{row, col});

        isBlackTurn = !isBlackTurn;

        return flipped;
    }
    private boolean canFlip(int row, int col, String me) {
        String opp = me.equals(BLACK) ? WHITE : BLACK;

        for (int[] d : directions) {
            int r = row + d[0];
            int c = col + d[1];
            boolean seenOpp = false;

            while (r >= 0 && r < 8 && c >= 0 && c < 8 && board[r][c] != EMPTY) {
                if (board[r][c].equals(opp))
                    seenOpp = true;
                else if (board[r][c].equals(me) && seenOpp)
                    return true;
                 else
                    break;
                r += d[0];
                c += d[1];
            }
        }
        return false; // Không tìm thấy hướng nào
    }
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
                    for (int[] p : toFlip) {
                        board[p[0]][p[1]] = me;
                        totalFlipped.add(p);
                    }
                    break;
                } else
                    break;
                r += d[0];
                c += d[1];
            }
        }
        return totalFlipped;
    }
}
