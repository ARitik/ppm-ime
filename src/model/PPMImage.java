package model;

import java.util.Arrays;

public class PPMImage implements Image {
  String identifier;
  int height;
  int width;
  int maxValue;
  Pixel pixels[][];

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PPMImage)) {
      return false;
    }
    PPMImage other = (PPMImage) o;
    if(other.getMaxValue() != this.getMaxValue() ||
    other.getHeight() != this.getHeight() || other.getWidth()!=this.getWidth()) {
      return false;
    }
    if(Arrays.deepEquals(other.getPixels(),this.getPixels())) {
      return false;
    }
    return true;
  }

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
                    .mapToInt(Pixel::getValue)
                    .toArray())
            .toArray(int[][]::new);
    return lumaArray;
  }

  public int getHeight() {
    return height;
  }

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


  private PPMImage(String identifier, int height, int width, int maxValue, //int[][] R, int[][] G,
                   //int[][] B, int[][] value, int[][] luma, int[][] intensity) {
                   Pixel pixels[][]) {
    this.identifier = identifier;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.pixels = pixels;
  }

  public static ImageBuilder getBuilder() {
    return new ImageBuilder();
  }

  public int getMaxValue() {
    return maxValue;
  }


  public static class ImageBuilder {
    private String identifier;
    private int height;
    private int width;
    private int maxValue;
    private Pixel pixels[][];

    private ImageBuilder() {
    }

    public ImageBuilder pixelMatrix(Pixel[][] pixels) {
      this.pixels = pixels;
      return this;
    }

    public ImageBuilder pixel(Pixel pixel, int row, int column) {
      pixels[row][column] = pixel;
      return this;
    }

    public ImageBuilder height(int height) {
      this.height = height;
      this.pixels = new Pixel[height][width];
      return this;
    }

    public ImageBuilder width(int width) {
      this.width = width;
      return this;
    }

    public ImageBuilder maxValue(int maxValue) {
      this.maxValue = maxValue;
      return this;
    }

    public ImageBuilder identifier(String identifier) {
      this.identifier = identifier;
      return this;
    }

    public PPMImage build() {
      //return new PPMImage(identifier,height,width,maxValue,R,G,B,value,luma,intensity);
      return new PPMImage(identifier, height, width, maxValue, pixels);
    }
  }

}
