package model;

import java.util.Arrays;

/**
 * This class implements Image and builds a ppm type image.
 */
public class PPMImage implements Image {
    String identifier;
    int height;
    int width;
    int maxValue;
    Pixel pixels[][];

    /**
     * The method is used to compare whether two images are exact same or not.
     * @param o
     * @return true if the images are same, else it returns false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PPMImage)) {
            return false;
        }
        PPMImage other = (PPMImage) o;
        if (other.getMaxValue() != this.getMaxValue() ||
                other.getHeight() != this.getHeight() || other.getWidth() != this.getWidth()) {
            return false;
        }

        if (!Arrays.deepEquals(other.getPixels(), this.getPixels())) {
            return false;
        }
        return true;
    }

    /**
     * The method returns the pixel matrix containing RGB channels.
     * @return pixel matrix
     */
    public Pixel[][] getPixels() {
        return pixels;
    }


    public int[][] getRMatrix() {
        int[][] rArray = Arrays.stream(pixels)
                .map(row -> Arrays.stream(row)
                        .mapToInt(Pixel::getR)
                        .toArray())
                .toArray(int[][]::new);
        return rArray;
    }

    public int[][] getBMatrix() {
        int[][] bArray = Arrays.stream(pixels)
                .map(row -> Arrays.stream(row)
                        .mapToInt(Pixel::getB)
                        .toArray())
                .toArray(int[][]::new);
        return bArray;
    }


    public int[][] getGMatrix() {
        int[][] gArray = Arrays.stream(pixels)
                .map(row -> Arrays.stream(row)
                        .mapToInt(Pixel::getG)
                        .toArray())
                .toArray(int[][]::new);
        return gArray;
    }

    public int[][] getValueMatrix() {
        int[][] valueArray = Arrays.stream(pixels)
                .map(row -> Arrays.stream(row)
                        .mapToInt(Pixel::getValue)
                        .toArray())
                .toArray(int[][]::new);
        return valueArray;
    }

    public int[][] getIntensityMatrix() {
        int[][] intensityArray = Arrays.stream(pixels)
                .map(row -> Arrays.stream(row)
                        .mapToInt(Pixel::getIntensity)
                        .toArray())
                .toArray(int[][]::new);
        return intensityArray;
    }

    public int[][] getLumaMatrix() {
        int[][] lumaArray = Arrays.stream(pixels)
                .map(row -> Arrays.stream(row)
                        .mapToInt(Pixel::getLuma)
                        .toArray())
                .toArray(int[][]::new);
        return lumaArray;
    }

    /**
     * The method returns height of the image.
     * @return height of image
     */
    public int getHeight() {
        return height;
    }

    /**
     * The method returns width of the image.
     * @return width of image
     */
    public int getWidth() {
        return width;
    }

    public int getRPixel(int row, int column) {
        return pixels[row][column].getR();
    }

    public int getGPixel(int row, int column) {
        return pixels[row][column].getG();
    }

    public int getBPixel(int row, int column) {
        return pixels[row][column].getB();
    }

    /**
     * Constructs an image of type ppm with the help of height, width, max value,
     * rgp pixel values and identifier.
     * @param identifier of the image
     * @param height of the image
     * @param width of the image
     * @param maxValue of the image
     * @param pixels of the image
     */
    private PPMImage(String identifier, int height, int width, int maxValue, Pixel pixels[][]) {
        this.identifier = identifier;
        this.height = height;
        this.width = width;
        this.maxValue = maxValue;
        this.pixels = pixels;
    }

    /**
     * The method creates an object of ImageBuilder class.
     * @return object of ImageBuilder
     */
    public static ImageBuilder getBuilder() {
        return new ImageBuilder();
    }

    /**
     * The method returns the maximum value of the image.
     * @return maximum value of the image
     */
    public int getMaxValue() {
        return maxValue;
    }


    /**
     * This class represents ImageBuilder and is used to construct an image
     * of type ppm.
     */
    public static class ImageBuilder {
        private String identifier;
        private int height;
        private int width;
        private int maxValue;
        private Pixel pixels[][];

        /**
         * Constructor of ImageBuilder class.
         */
        private ImageBuilder() {
        }

        public ImageBuilder pixelMatrix(Pixel[][] pixels) {
            this.pixels = pixels;
            return this;
        }

        /**
         * The method returns the pixels of the image.
         * @param pixel of RGB channels
         * @param row of the pixel
         * @param column of the pixel
         * @return pixels
         */
        public ImageBuilder pixel(Pixel pixel, int row, int column) {
            pixels[row][column] = pixel;
            return this;
        }

        /**
         * The method returns the height of the image.
         * @param height of the image
         * @return height
         */
        public ImageBuilder height(int height) {
            this.height = height;
            this.pixels = new Pixel[height][width];
            return this;
        }

        /**
         * The method returns the width of the image.
         * @param width of the image
         * @return width
         */
        public ImageBuilder width(int width) {
            this.width = width;
            return this;
        }

        /**
         * The method returns the maximum value of the image.
         * @param maxValue of the image
         * @return maximum value
         */
        public ImageBuilder maxValue(int maxValue) {
            this.maxValue = maxValue;
            return this;
        }

        /**
         * The method returns the identifier of the image.
         * @param identifier of the image
         * @return identifier of the image
         */
        public ImageBuilder identifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        /**
         * The method creates a PPMImage object.
         * @return new PPMImage object
         */
        public PPMImage build() {
            return new PPMImage(identifier, height, width, maxValue, pixels);
        }
    }

}
