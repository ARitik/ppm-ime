package model;

public class PPMImage implements Image {
  String identifier;
  int height;
  int width;
  int maxValue;
  int[][] R;
  int[][] G;
  int[][] B;
  int[][] value;
  int[][] luma;
  int[][] intensity;


  public int getHeight() {
    return height;
  }

  public int getWidth() {
    return width;
  }

  public int getRPixel(int row, int column) {
    return R[row][column];
  }

  public int getGPixel(int row, int column) {
    return G[row][column];
  }

  public int getBPixel(int row, int column) {
    return B[row][column];
  }

  public int getValuePixel(int row, int column) {
    return value[row][column];
  }

  public int getLumaPixel(int row, int column) {
    return luma[row][column];
  }

  public int getIntensityPixel(int row, int column) {
    return intensity[row][column];
  }


  public int[][] getValue() {
    return value;
  }

  public int[][] getLuma() {
    return luma;
  }

  public int[][] getIntensity() {
    return intensity;
  }

  private PPMImage(String identifier, int height, int width, int maxValue, int[][] R, int[][] G,
                   int[][] B, int[][] value, int[][] luma, int[][] intensity) {
    this.identifier = identifier;
    this.height = height;
    this.width = width;
    this.maxValue = maxValue;
    this.R = R;
    this.G = G;
    this.B= B;
    this.value = value;
    this.luma = luma;
    this.intensity= intensity;
  }

  public int[][] getR() {
    return R;
  }

  public int[][] getG() {
    return G;
  }

  public int[][] getB() {
    return B;
  }

  public static ImageBuilder getBuilder() {
    return new ImageBuilder();
  }

  @Override
  public int getMaxValue() {
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
    int[][] value;
    int[][] luma;
    int[][] intensity;

    private ImageBuilder() {
    }


    public ImageBuilder RMatrix(int[][] R) {
      this.R =  R;
      return this;
    }

    public ImageBuilder GMatrix(int[][] G) {
      this.G =  G;
      return this;
    }
    public ImageBuilder BMatrix(int[][] B) {
      this.B =  B;
      return this;
    }

    public ImageBuilder valueMatrix(int[][] value) {
      this.value =  value;
      return this;
    }

    public ImageBuilder intensityMatrix(int[][] intensity) {
      this.intensity =  intensity;
      return this;
    }
    public ImageBuilder lumaMatrix(int[][] luma) {
      this.luma =  luma;
      return this;
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

    public ImageBuilder value(int valuePixel, int row, int column) {
      value[row][column] = valuePixel;
      return this;
    }

    public ImageBuilder intensity(int intensityPixel, int row, int column) {
      intensity[row][column] = intensityPixel;
      return this;
    }

    public ImageBuilder luma(int lumaPixel, int row, int column) {
      luma[row][column] = lumaPixel;
      return this;
    }
    public ImageBuilder height(int height) {
      this.height = height;
      this.R = new int[height][width];
      this.G = new int[height][width];
      this.B = new int[height][width];
      this.value = new int[height][width];
      this.luma = new int[height][width];
      this.intensity = new int[height][width];
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
      return new PPMImage(identifier,height,width,maxValue,R,G,B,value,luma,intensity);
    }
  }

}
