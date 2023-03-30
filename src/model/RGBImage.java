package model;

import java.util.Arrays;

/**
 * This class implements Image and builds a ppm type image.
 */
public class RGBImage implements Image {
  String identifier;
  int height;
  int width;
  int maxValue;
  Pixel[][] pixels;

  @Override
  public int hashCode() {
    return (identifier.length() * height) + width + maxValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof RGBImage)) {
      return false;
    }
    RGBImage other = (RGBImage) o;
    if (other.getMaxValue() != this.getMaxValue()
            || other.getHeight() != this.getHeight() || other.getWidth() != this.getWidth()) {
      return false;
    }

    return Arrays.deepEquals(other.getPixels(), this.getPixels());
  }

  /**
   * The method returns the pixel matrix containing RGB channels.
   *
   * @return pixel matrix
   */
  Pixel[][] getPixels() {
    return pixels;
  }


  /**
   * Return Red Matrix.
   *
   * @return Red Matrix
   */
  int[][] getRMatrix() {
    int[][] rArray = Arrays.stream(pixels)
            .map(row -> Arrays.stream(row)
                    .mapToInt(Pixel::getRed)
                    .toArray())
            .toArray(int[][]::new);
    return rArray;
  }

  /**
   * Return Blue Matrix.
   *
   * @return Blue Matrix
   */
  int[][] getBMatrix() {
    int[][] bArray = Arrays.stream(pixels)
            .map(row -> Arrays.stream(row)
                    .mapToInt(Pixel::getBlue)
                    .toArray())
            .toArray(int[][]::new);
    return bArray;
  }


  /**
   * Return Green Matrix.
   *
   * @return Green Matrix.
   */
  int[][] getGMatrix() {
    int[][] gArray = Arrays.stream(pixels)
            .map(row -> Arrays.stream(row)
                    .mapToInt(Pixel::getGreen)
                    .toArray())
            .toArray(int[][]::new);
    return gArray;
  }

  /**
   * Return the Value Matrix.
   *
   * @return Value Matrix.
   */
  int[][] getValueMatrix() {
    int[][] valueArray = Arrays.stream(pixels)
            .map(row -> Arrays.stream(row)
                    .mapToInt(Pixel::getValue)
                    .toArray())
            .toArray(int[][]::new);
    return valueArray;
  }

  /**
   * Return the Intensity Matrix.
   *
   * @return Intensity Matrix.
   */
  int[][] getIntensityMatrix() {
    int[][] intensityArray = Arrays.stream(pixels)
            .map(row -> Arrays.stream(row)
                    .mapToInt(Pixel::getIntensity)
                    .toArray())
            .toArray(int[][]::new);
    return intensityArray;
  }

  /**
   * Return the Luma Matrix.
   *
   * @return Luma Matrix.
   */
  int[][] getLumaMatrix() {
    int[][] lumaArray = Arrays.stream(pixels)
            .map(row -> Arrays.stream(row)
                    .mapToInt(Pixel::getLuma)
                    .toArray())
            .toArray(int[][]::new);
    return lumaArray;
  }

  /**
   * The method returns height of the image.
   *
   * @return height of image
   */
  int getHeight() {
    return height;
  }

  /**
   * The method returns width of the image.
   *
   * @return width of image
   */
  int getWidth() {
    return width;
  }

  /**
   * Get Red value of a pixel.
   *
   * @param row    the row of the image.
   * @param column the column of the image.
   * @return the pixel object.
   */
  int getRPixel(int row, int column) {
    return pixels[row][column].getRed();
  }

  /**
   * Get Green value of a pixel.
   *
   * @param row    the row of the image.
   * @param column the column of the image.
   * @return the pixel object.
   */
  int getGPixel(int row, int column) {
    return pixels[row][column].getGreen();
  }

  /**
   * Get Blue value of a pixel.
   *
   * @param row    the row of the image.
   * @param column the column of the image.
   * @return the pixel object.
   */
  int getBPixel(int row, int column) {
    return pixels[row][column].getBlue();
  }

  /**
   * Constructs an image of type ppm with the help of height, width, max value,
   * rgp pixel values and identifier.
   *
   * @param identifier of the image
   * @param height     of the image
   * @param width      of the image
   * @param maxValue   of the image
   * @param pixels     of the image
   */
  private RGBImage(String identifier, int height, int width, int maxValue, Pixel[][] pixels) {
    this.identifier = identifier;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }

  Pixel getPixel(int row, int column) {
    return pixels[row][column];
  }

  /**
   * The method creates an object of ImageBuilder class.
   *
   * @return object of ImageBuilder
   */
  public static ImageBuilder getBuilder() {
    return new ImageBuilder();
  }

  /**
   * The method returns the maximum value of the image.
   *
   * @return maximum value of the image
   */
  int getMaxValue() {
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
    private Pixel[][] pixels;

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
     *
     * @param pixel  of RGB channels
     * @param row    of the pixel
     * @param column of the pixel
     * @return pixels
     */
    public ImageBuilder pixel(Pixel pixel, int row, int column) {
      pixels[row][column] = pixel;
      return this;
    }

    public ImageBuilder pixel(int row, int column, int newRedValue, int newGreenValue,
                       int newBlueValue) {
      pixels[row][column] = new Pixel(newRedValue, newGreenValue, newBlueValue);
      return this;
    }

    /**
     * The method returns the height of the image.
     *
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
     *
     * @param width of the image
     * @return width
     */
    public ImageBuilder width(int width) {
      this.width = width;
      return this;
    }

    /**
     * The method returns the maximum value of the image.
     *
     * @param maxValue of the image
     * @return maximum value
     */
    public ImageBuilder maxValue(int maxValue) {
      this.maxValue = maxValue;
      return this;
    }

    /**
     * The method returns the identifier of the image.
     *
     * @param identifier of the image
     * @return identifier of the image
     */
    public ImageBuilder identifier(String identifier) {
      this.identifier = identifier;
      return this;
    }

    /**
     * The method creates a RGBImage object.
     *
     * @return new RGBImage object
     */
    public Image build() {
      return new RGBImage(identifier, height, width, maxValue, pixels);
    }
  }

}
