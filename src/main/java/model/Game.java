package model;
//mode game
public class Game {
    private Board board;
    private int currentPlayer;

    //bat dau bang quan den
    public Game(){
        board = Board.boardInitial();
        currentPlayer = Board.black;
    }
    public Game(Board b, int player){
        board = b;
        currentPlayer = player;
    }

    //ai vs ai
    public void gameAIvsAI(AIPlayer a1, AIPlayer a2, int maxMoves){
        int moves = 0;
        //lap den khi game ket thuc hoac toi gian han maxMoves
        while(!board.checkEnd() && moves<maxMoves){
            AIPlayer current = (currentPlayer == Board.black) ? a1 : a2;//chon AI tuong ung
            Move move = current.getBestMove(board, currentPlayer);//AI chon nuoc

            board = board.applyMove(move,currentPlayer);//ap dung nuoc di
            currentPlayer = -currentPlayer;//doi luot
            moves++;
        }
    }
}
