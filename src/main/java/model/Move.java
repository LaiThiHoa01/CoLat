package model;

public class Move {
    public final int x,y;
    public Move(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //kiem tra nuoc pass
    public boolean isPass(){
        return x==-1 && y==-1;
    }
    //tao nuoc pass
    public static Move pass(){
        return new Move(-1,-1);
    }

}
