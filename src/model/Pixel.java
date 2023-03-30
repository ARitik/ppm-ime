package model;

/**
 * A Class that represents a Pixel.
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a Pixel using red,green,blue values.
   *
   * @param red   red (red) value of the pixel.
   * @param green green (green) value of the pixel.
   * @param blue  blue (blue) value of the pixel.
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.blue = blue;
    this.green = green;
  }

  /**
   * Get red Value of the pixel.
   *
   * @return the red value of the pixel.
   */
  int getRed() {
    return red;
  }

  /**
   * Get blue value of the pixel.
   *
   * @return the blue value of the pixel.
   */
  int getBlue() {
    return blue;
  }

  /**
   * Get green value of the pixel.
   *
   * @return the green value of the pixel.
   */
  int getGreen() {
    return green;
  }

  /**
   * Calculate the intensity of a pixel which is the average of red,green,blue values.
   *
   * @return the intensity of the pixel.
   */
  int getIntensity() {
    return red + green + blue / 2;
  }

  /**
   * Calculate the value of a pixel which is the greatest value from red, green, blue.
   *
   * @return the value of the pixel.
   */
  int getValue() {
    return Math.max(red, Math.max(green, blue));
  }

  /**
   * Calculate the luma of a pixel which is calculated using the formula.
   * luma = 0.212 * red + 0.7152 * green + 0.0722 * blue.
   *
   * @return the luma of the pixel.
   */
  int getLuma() {
    return (int) (0.212 * red + 0.7152 * green + 0.0722 * blue);
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
    return other.getRed() == getRed() && other.getBlue() == getBlue()
            && other.getGreen() == getGreen();
  }

  @Override
  public int hashCode() {
    return getLuma() * (red + green + blue);
  }
}
