package model;
import java.util.*;

public class Minimax_AlphaBeta implements AIPlayer {
    private final int depth; //do sau tim kiem
    private final boolean userMoveOrdering;

    public Minimax_AlphaBeta(int depth, boolean ordering) {
        this.depth = depth;
        this.userMoveOrdering = ordering;
    }
    public Minimax_AlphaBeta(int depth) {
        this(depth, true);
    }

    //tra ve nuoc di tot nhat
    @Override
    public Move getBestMove(Board board, int player) {
        List<Move> moves = board.legalMoves(player); //lay tat ca nuoc di hop le
        //chi co pass thi tra ve pass
        if(moves.size() == 1 && moves.get(0).isPass()) return Move.pass();

        int alpha = Integer.MIN_VALUE/2;
        int beta = Integer.MAX_VALUE/2;

        Move best = moves.get(0);//gs nươc di dau tien là tot nhat
        int bestScore = Integer.MIN_VALUE; //diem Best

        if(userMoveOrdering) orderMoves(moves, board, player);//sap xep nuoc di de cat tia tot hon

        //duyet tung buoc di
        for(Move move : moves) {
            Board nb = board.applyMove(move, player);
            int score = -alphabeta(nb, -player, depth-1, -beta, -alpha);

            if(score > bestScore) {
                bestScore = score;
                best = move;
            }
            if(score > alpha) alpha = score;
        }
        return best;
    }

    //tra ve diem tot nhat
    private int alphabeta(Board board, int player, int depth, int alpha, int beta) {
        if(depth == 0 || board.checkEnd()) return Heuristic.evaluate(board, player);
        List<Move> moves = board.legalMoves(player);
        if(moves.size() == 1 && moves.get(0).isPass()) return -alphabeta(board, -player, depth-1, -beta, -alpha);
        if(userMoveOrdering) orderMoves(moves, board, player);
        for(Move move : moves) {
            Board nb = board.applyMove(move, player);
            int score = -alphabeta(nb, -player, depth-1, -beta, -alpha);
            if(score>=beta) return score;
            if(score > alpha) alpha = score;
        }
        return alpha;
    }

    //sap xep nuoc di
    private void orderMoves(List<Move> moves, Board board, int player) {
        moves.sort((a,b)->{
            boolean aCorner = isCorner(a), bCorner = isCorner(b);
            if(aCorner != bCorner) return aCorner ? -1 : 1;

            Board na = board.applyMove(a,player);
            Board nb = board.applyMove(b,player);
            int sa = Heuristic.evaluate(na, player);
            int sb = Heuristic.evaluate(nb, player);
            return Integer.compare(sb,sa); //sap xep giam dan
        });
    }

    //kiem tra Move la goc
    private boolean isCorner(Move move) {
        return (move.x==0||move.x==7)&&(move.y==0||move.y==7);
    }
}
