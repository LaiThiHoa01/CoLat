package demo;

import java.util.List;

public class BotAI {
    // Bảng trọng số vị trí (Vẫn dùng cho Khai cuộc và Trung cuộc)
    private static final int[][] STATIC_WEIGHTS = {
            {100, -20, 10,  5,  5, 10, -20, 100},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            { 10,  -2, -1, -1, -1, -1,  -2,  10},
            {  5,  -2, -1, -1, -1, -1,  -2,   5},
            {  5,  -2, -1, -1, -1, -1,  -2,   5},
            { 10,  -2, -1, -1, -1, -1,  -2,  10},
            {-20, -50, -2, -2, -2, -2, -50, -20},
            {100, -20, 10,  5,  5, 10, -20, 100}
    };

    private String botColor = Othello.WHITE;
    private String playerColor = Othello.BLACK;

    // --- HÀM TÌM KIẾM MINIMAX (Không đổi) ---
    public int[] findBestMove(Othello currentState, int depth) {
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        List<int[]> moves = currentState.getValidMoves();
        if (moves.isEmpty()) return null;

        int[] bestMove = moves.get(0);
        int maxEval = Integer.MIN_VALUE;

        for (int[] move : moves) {
            Othello clone = new Othello(currentState);
            clone.makeMove(move[0], move[1]);

            // Lấy tổng số quân để biết giai đoạn game
            int totalPieces = countTotalPieces(clone);

            int eval = minimax(clone, depth - 1, alpha, beta, false, totalPieces);

            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
            alpha = Math.max(alpha, eval);
        }
        return bestMove;
    }

    private int minimax(Othello state, int depth, int alpha, int beta, boolean isMaximizing, int totalPieces) {
        List<int[]> moves = state.getValidMoves();
        if (depth == 0 || moves.isEmpty()) {
            return evaluateBoardDynamic(state, totalPieces);
        }

        if (isMaximizing) {
            int maxEval = Integer.MIN_VALUE;
            for (int[] move : moves) {
                Othello clone = new Othello(state);
                clone.makeMove(move[0], move[1]);
                int eval = minimax(clone, depth - 1, alpha, beta, false, totalPieces + 1);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) break;
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (int[] move : moves) {
                Othello clone = new Othello(state);
                clone.makeMove(move[0], move[1]);
                int eval = minimax(clone, depth - 1, alpha, beta, true, totalPieces + 1);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) break;
            }
            return minEval;
        }
    }

    // --- [QUAN TRỌNG] HÀM ĐÁNH GIÁ ĐỘNG ---
    private int evaluateBoardDynamic(Othello state, int totalPieces) {
        // 1. Tàn cuộc (Endgame): Hơn 58 quân -> Chỉ quan tâm đếm số quân
        if (totalPieces >= 58) {
            return calcCoinParity(state) * 1000;
        }

        // 2. Các chỉ số thành phần
        int positionalScore = calcPositionalScore(state); // Điểm vị trí (bảng cũ)
        int mobilityScore   = calcMobility(state);        // Khả năng di chuyển
        int cornerScore     = calcCornerGrab(state);      // Chiếm góc
        int parityScore     = calcCoinParity(state);      // Tỉ lệ quân (ít quan trọng đầu game)

        // 3. Trọng số ĐỘNG thay đổi theo giai đoạn
        double wPosition, wMobility, wCorner, wParity;

        if (totalPieces < 20) {
            // Khai cuộc: Mobility là vua, tránh bị ép, không ham ăn quân
            wMobility = 50.0;
            wPosition = 10.0;
            wCorner   = 100.0;
            wParity   = -10.0; // Thậm chí ăn ít quân lại tốt hơn để giữ mobility
        } else {
            // Trung cuộc: Cân bằng giữa vị trí và mobility
            wMobility = 20.0;
            wPosition = 30.0;
            wCorner   = 150.0; // Góc cực kỳ quan trọng
            wParity   = 5.0;   // Bắt đầu quan tâm số lượng quân
        }

        return (int) (wPosition * positionalScore +
                wMobility * mobilityScore +
                wCorner * cornerScore +
                wParity * parityScore);
    }

    // --- CÁC HÀM TÍNH TOÁN THÀNH PHẦN ---

    // 1. Tính điểm dựa trên bảng STATIC_WEIGHTS
    private int calcPositionalScore(Othello state) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String p = state.getPieceAt(i, j);
                if (p != null) {
                    if (p.equals(botColor)) score += STATIC_WEIGHTS[i][j];
                    else if (p.equals(playerColor)) score -= STATIC_WEIGHTS[i][j];
                }
            }
        }
        return score;
    }

    // 2. Tính chênh lệch số lượng quân (Parity)
    // Công thức: 100 * (Bot - Player) / (Bot + Player)
    private int calcCoinParity(Othello state) {
        int botCount = state.getScore(botColor);
        int playerCount = state.getScore(playerColor);
        if (botCount + playerCount == 0) return 0;
        return 100 * (botCount - playerCount) / (botCount + playerCount);
    }

    // 3. Tính khả năng di chuyển (Mobility)
    // Ai có nhiều nước đi hơn thì nắm quyền chủ động
    private int calcMobility(Othello state) {
        // Số nước đi của người chơi hiện tại (đang trong lượt tính toán của Minimax)
        int currentMoves = state.getValidMoves().size();

        // Để tính nước đi đối thủ, ta cần giả lập đổi lượt tạm thời (hơi tốn kém nhưng đáng giá)
        // Tuy nhiên để đơn giản và nhanh, ta chỉ tính:
        // Nếu state.isBlackTurn() là lượt người -> currentMoves là của người -> Bot thiệt
        // Nếu state.isBlackTurn() là lượt Bot -> currentMoves là của Bot -> Bot lợi

        // Lưu ý: logic này chỉ tương đối
        if (state.isBlackTurn()) { // Đang là lượt Đen (Người)
            return -currentMoves;
        } else { // Đang là lượt Trắng (Bot)
            return currentMoves;
        }
    }

    // 4. Kiểm tra chiếm góc (Corners)
    private int calcCornerGrab(Othello state) {
        int botCorners = 0;
        int playerCorners = 0;
        int[][] corners = {{0,0}, {0,7}, {7,0}, {7,7}};

        for (int[] c : corners) {
            String p = state.getPieceAt(c[0], c[1]);
            if (p != null) {
                if (p.equals(botColor)) botCorners++;
                else if (p.equals(playerColor)) playerCorners++;
            }
        }
        return 25 * (botCorners - playerCorners);
    }

    // Tiện ích đếm tổng số quân trên bàn
    private int countTotalPieces(Othello state) {
        int count = 0;
        for(int i=0; i<8; i++)
            for(int j=0; j<8; j++)
                if(state.getPieceAt(i, j) != null) count++;
        return count;
    }
}