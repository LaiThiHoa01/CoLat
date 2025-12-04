package model;

public class Heuristic {
    private static final int[][] PST = {
            {120,    -20,    20,     5,  5,  20,     -20,   120},
            {-20,    -40,    -5,    -5, -5,  -5,     -40,   -20},
            {20,     -5,     15,     3,  3,  15,     -5,     20},
            {5,      -5,     3,      3,  3,  3,      -5,     5},
            {5,      -5,     3,      3,  3,  3,      -5,     5},
            {20,     -5,     15,     3,  3,  15,     -5,     20},
            {-20,    -40,   -5,     -5, -5,  -5,     -40,   -20},
            {120,    -20,   20,      5,  5,  20,     -20,   120}
    };

    public static int evaluate(Board board, int player){
        int material = 0; //diem vi tri - dua tren PST
        int myPiece = 0, oppPiece = 0;
        int myCorner = 0, oppCorner = 0; //so goc chiem

        //tinh material va so quan
        for(int i = 0; i<Board.size; i++){
            for(int j = 0; j<Board.size; j++){
                int  p = board.get(i,j);//gan gia tri o
                if(p == player){
                    material+= PST[i][j];
                    myPiece++;
                } else if (p==-player) {
                    material-= PST[i][j];
                    oppPiece++;
                }
            }
        }

        //so nuoc di hop le
        int myMove = board.legalMoves(player).size();
        int oppMove = board.legalMoves(-player).size();
        int mobility = myMove - oppMove; //chenh lech nuoc di hop le

        //tinh goc
        int[][] corner = {{0,0}, {0,7}, {7,0}, {7,7}};
        for(int[] c : corner){
            int val = board.get(c[0],c[1]);
            if(val == player) myCorner++;
            else if(val == -player) oppCorner++;
        }
        int cornerScore = 25*(myCorner + oppCorner);//he so lon

        //parity : uu tien piece difference o cuoi tran
        int parity = 0;
        int total = myPiece + oppPiece;
        if(total > 48) {
            parity = 100 * (myPiece + oppPiece)/total;
        }

        //trong so tung thanh phan
        double wMaterial = 1.0;
        double wMobility = 2.0;
        double wCorner = 10.0;
        double wParity = 1.0;

        //tong hop diem
        double score = wMaterial*material + wMobility*mobility + wCorner*cornerScore + wParity*wParity;
        return (int) Math.round(score);
    }
}
