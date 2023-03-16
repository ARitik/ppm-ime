package model;

/**
 * A Class that represents a Pixel.
 */
public class Pixel {
    private int R;
    private int G;
    private int B;

    /**
     * Constructs a Pixel using R,G,B values.
     *
     * @param R R (red) value of the pixel.
     * @param G G (green) value of the pixel.
     * @param B B (blue) value of the pixel.
     */
    public Pixel(int R, int G, int B) {
        this.R = R;
        this.B = B;
        this.G = G;
    }

    /**
     * Get R Value of the pixel.
     *
     * @return the R value of the pixel.
     */
    public int getR() {
        return R;
    }

    /**
     * Get B value of the pixel.
     *
     * @return the B value of the pixel.
     */
    public int getB() {
        return B;
    }

    /**
     * Get G value of the pixel.
     *
     * @return the G value of the pixel.
     */
    public int getG() {
        return G;
    }

    /**
     * Calculate the intensity of a pixel which is the average of R,G,B values;
     *
     * @return the intensity of the pixel.
     */
    public int getIntensity() {
        return R + G + B / 2;
    }

    /**
     * Calculate the value of a pixel which is the greatest value from R,G,B;
     *
     * @return the value of the pixel.
     */
    public int getValue() {
        return Math.max(R, Math.max(G, B));
    }

    /**
     * Calculate the luma of a pixel which is calculated using the formula
     * luma = 0.212 * R + 0.7152 * G + 0.0722 * B.
     *
     * @return the luma of the pixel.
     */
    public int getLuma() {
        return (int) (0.212 * R + 0.7152 * G + 0.0722 * B);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Pixel)) {
            return false;
        }
        Pixel other = (Pixel) o;
        if (other.getR() != getR() || other.getB() != getB() || other.getG() != getG()) {
            return false;
        }
        return true;
    }
}
