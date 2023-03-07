package model;

public class PPMImage implements Image {
  String identifier;
  int height;
  int width;
  int maxValue;
  int[][] R;
  int[][] G;
  int[][] B;


  private PPMImage(String identifier, int height, int width, int maxValue, int[][] R, int[][] G,
                   int[][] B) {
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.R = R;
    this.G = G;
    this.B= B;
    this.identifier = identifier;
  }


  public static ImageBuilder getBuilder() {
    return new ImageBuilder();
  }

  @Override
  public int getMaxValue() {
    System.out.println("Max Value: " + maxValue);
    return maxValue;
  }


  public static class ImageBuilder {
    private String identifier;
    private int height;
    private int width;
    private int maxValue;
    private int[][] R;
    private int[][] G;
    private int[][] B;
    private ImageBuilder() {
      this.R = new int[1000][1000];
      this.G = new int[1000][1000];
      this.B = new int[1000][1000];
    }

    public ImageBuilder R(int RPixel, int row, int column) {
      R[row][column] = RPixel;
      return this;
    }

    public ImageBuilder G(int GPixel, int row, int column) {
      G[row][column] = GPixel;
      return this;
    }
    public ImageBuilder B(int BPixel, int row, int column) {
      B[row][column] = BPixel;
      return this;
    }
    public ImageBuilder height(int height) {
      this.height = height;
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
      return new PPMImage(identifier,height,width,maxValue,R,G,B);
    }
  }

}
