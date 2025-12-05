package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    public static final int size = 8;
    public static final int empty = 0;
    public static final int black = 1;
    public static final int white = -1;

    //huong di chuyen de kiem tra lat quan
    private static final int[] DX = {-1,-1,-1,0,0,1,1,1};
    private static final int[] DY = {-1,0,1,-1,1,-1,0,1};

    private int[][] board = new int[size][size];

    public Board(){}
    public Board(Board otherBoard){
        for(int i=0;i<size;i++){
            this.board[i]= Arrays.copyOf(otherBoard.board[i],size);
        }
    }
    //tao ban ban dau
    public static Board boardInitial(){
        Board b = new Board();
        b.board[3][3] = white;
        b.board[4][4] = white;
        b.board[3][4] = black;
        b.board[4][3] = black;
        return b;
    }
    //get gia tri o
    public int get(int x, int y){
        return board[x][y];
    }
    //set gia tri o
    public void set(int x, int y, int v){
        board[x][y] = v;
    }
    //gioi han quan co tren ban
    public boolean checkBoard(int x, int y){
        return x>=0 && x<size && y>=0 && y<size;
    }
    //kiem tra huong co the lat co doi thu neu dat o (x,y)
    public boolean canFlip(int x, int y, int player){
        //duyen 8 huong
        for(int i=0;i<8;i++){
            int nx = x + DX[i],  ny = y + DY[i];
            boolean foundOpp = false;

            //di chuyen len cung huong, neu gap doi thu thi tiep tuc
            while(checkBoard(nx,ny)&&board[nx][ny]==-player){
                foundOpp = true; // co it nhat 1 quan doi
                nx += DX[i];
                ny += DY[i];
            }
            //sau quan doi thu la quan minh thi hop le
            if(foundOpp && checkBoard(nx,ny) && board[nx][ny]==player){
                return true;
            }
        }
        return false;
    }
    //tra ve danh sach nuoc di hop le
    public List<Move> legalMoves(int player){
        List<Move> moves = new ArrayList<>();
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]!=empty) continue; //full ban thi kh dat
                if(canFlip(i,j,player))
                    moves.add(new Move(i,j));
            }
        }
        if(moves.isEmpty())
            moves.add(Move.pass()); //kh co nuoc di thi pass
        return moves;
    }
    public Board applyMove(Move move, int player){
        //neu pass tra ve ban sao ban y het
        if(move.isPass())
            return new Board(this);
        Board nb = new Board(this); //tao ban sao de thuc hien lat quan
        nb.board[move.x][move.y] = player; //dat quan

        for(int i=0;i<8;i++){
            int nx = move.x + DX[i], ny = move.y + DY[i];
            List<int[]> flips = new ArrayList<>(); //luu toa do quan can lat

            while(checkBoard(nx,ny) && nb.board[nx][ny]==-player){
                flips.add(new int[]{nx,ny});
                nx += DX[i];
                ny += DY[i];
            }

            if(checkBoard(nx,ny) && nb.board[nx][ny]==player){
                for (int[] flip : flips) {
                    nb.board[flip[0]][flip[1]] = player; //gan thanh quan minh
                }
            }
        }
        return nb;
    }
    //dem so quan co tren ban
    public int count(int player){
        int count = 0;
        for(int i=0; i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]==player) count++;
            }
        }
        return count;
    }
    //kiem tra ket thuc game
    public boolean checkEnd(){
        return legalMoves(black).size()==1 && legalMoves(black).get(0).isPass()
            && legalMoves(white).size()==1 && legalMoves(white).get(0).isPass();

    }

}
