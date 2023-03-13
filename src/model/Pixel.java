package model;

public class Pixel {
    private int R;
    private int G;
    private int B;

    public Pixel(int R, int G, int B){
        this.R = R;
        this.B = B;
        this.G = G;
    }

    public int getR() {
        return R;
    }

    public int getB() {
        return B;
    }

    public int getG() {
        return G;
    }

    public int getIntensity() {
        return R+G+B/2;
    }

    public int getValue() {
        return Math.max(R,Math.max(G,B));
    }

    public int getLuma() {
        return (int) (0.212 * R + 0.7152 * G + 0.0722 * B);
    }
}
