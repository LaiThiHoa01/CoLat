package model;

import java.util.ArrayList;
import java.util.List;

public class Othello {
    public static final String BLACK = "B";
    public static final String WHITE = "W";

    private String[][] board = new String[8][8];
    private boolean isBlackTurn = true;
    private static final int[][] tamHuongDi = {
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}          ,{0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };
    public Othello() {
        board[3][3] = WHITE;
        board[3][4] = BLACK;
        board[4][3] = BLACK;
        board[4][4] = WHITE;
    }
    // --- [MỚI] Copy Constructor để AI mô phỏng ---
    public Othello(Othello other) {
        for (int i = 0; i < 8; i++) {
            System.arraycopy(other.board[i], 0, this.board[i], 0, 8);
        }
        this.isBlackTurn = other.isBlackTurn;
    }

    // --- [MỚI] Lấy danh sách các nước đi hợp lệ ---
    public List<int[]> getValidMoves() {
        List<int[]> moves = new ArrayList<>();
        String currentPlayer = getCurrentPlayerMark();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null && canPlace(i, j, currentPlayer)) {
                    moves.add(new int[]{i, j});
                }
            }
        }
        return moves;
    }
    // --- [MỚI] Đếm điểm để lượng giá ---
    public int getScore(String player) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (player.equals(board[i][j])) score++;
            }
        }
        return score;
    }

    public String getPieceAt(int row, int col) {
        return board[row][col];
    }
    public boolean isBlackTurn() {
        return isBlackTurn;
    }
    public String getCurrentPlayerMark() {
        return isBlackTurn ? BLACK : WHITE;
    }
    public boolean isMoveValid(int row, int col) {
        return board[row][col] == null && canPlace(row,col,getCurrentPlayerMark());
    }
    public List<int[]> makeMove(int row, int col) {
        String me = getCurrentPlayerMark();
        List<int[]> flipped = applyFlip(row, col, me);
        board[row][col] = me;
        flipped.add(new int[]{row, col});
        isBlackTurn = !isBlackTurn;
        return flipped;
    }
    private boolean canPlace(int row, int col, String me) {
        String bot = me.equals("B") ? WHITE : BLACK;
        for(int[] huongdi : tamHuongDi) {
            int r = row +huongdi[0];
            int c = col +huongdi[1];
            boolean timThayBot = false;
            while(r>=0&& r<8&& c>=0&& c<8&& board[r][c] !=null) {
                if(board[r][c].equals(bot)) {
                    timThayBot = true;
                }else if((board[r][c].equals(me))&& timThayBot){
                    return true;
                }else break;
                r+=huongdi[0];
                c+=huongdi[1];
            }
        }
       return false;
    }
    private List<int[]> applyFlip(int row, int col, String me) {
        String bot  = me.equals("B") ? WHITE : BLACK;
        List<int[]> tongQuanDaLat = new ArrayList<>();
        for(int[] huongdi : tamHuongDi) {
            int r = row +huongdi[0];
            int c = col +huongdi[1];
            List<int[]> biLat = new ArrayList<>();
            while(r>=0&& r<8 && c>=0&& c<8&& board[r][c]!= null) {
                if(board[r][c].equals(bot)) {
                    biLat.add(new int[]{r,c});
                }else if(board[r][c].equals(me)) {
                    for(int[] b : biLat) {
                        board[b[0]][b[1]] = me;
                        tongQuanDaLat.add(b);
                    } break;
                }else break;
                r+=huongdi[0];
                c+=huongdi[1];
                }

        }
        return tongQuanDaLat;

    }
}
