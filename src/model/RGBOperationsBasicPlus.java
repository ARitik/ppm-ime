package model;

import java.io.IOException;

/**
 * This class implements ImageOperationsBasicPlus and perform certain operations
 * on an image.
 */
public class RGBOperationsBasicPlus
        extends RGBOperationsBasic
        implements ImageOperationsBasicPlus {

  /**
   * Calls the constructor of the parent class.
   */
  public RGBOperationsBasicPlus() {
    super();
  }

  /**
   * The method blurs the image to create a new image.
   *
   * @param identifier     of the image
   * @param blurIdentifier identifier of the new blurred image.
   */
  @Override
  public void blur(String identifier, String blurIdentifier) throws IOException {
    Image image = imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    float[][] kernel = {{1 / 16f, 2 / 16f, 1 / 16f}, {2 / 16f, 4 / 16f, 2 / 16f}, {1 / 16f,
            2 / 16f, 1 / 16f}};

    applyFilter(kernel, (RGBImage) image, blurIdentifier);
  }

  /**
   * The method sharpens the image to create a new image.
   *
   * @param identifier        of the image
   * @param sharpenIdentifier identifier of the new sharpened image.
   */
  @Override
  public void sharpen(String identifier, String sharpenIdentifier) throws IOException {
    Image image = imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    float[][] kernel = {{-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f}, {-1 / 8f,
            2 / 8f, 2 / 8f, 2 / 8f, -1 / 8f}, {-1 / 8f, 2 / 8f, 1, 2 / 8f, -1 / 8f}, {-1 / 8f,
            2 / 8f, 2 / 8f, 2 / 8f, -1 / 8f}, {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f}
    };
    applyFilter(kernel, (RGBImage) image, sharpenIdentifier);
  }

  /**
   * The method gives the image a sepia tone.
   *
   * @param identifier      of the image
   * @param sepiaIdentifier identifier of the new sepia image.
   */
  @Override
  public void sepia(String identifier, String sepiaIdentifier) throws IOException {
    Image image = imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    float[][] kernel = {{0.393f, 0.769f, 0.189f}, {0.349f, 0.686f, 0.168f}, {0.272f,
            0.534f, 0.131f}};
    applyColorTransformation(kernel, (RGBImage) image, sepiaIdentifier);
  }

  /**
   * The method gives the image a greyscale tone.
   *
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new greyscale image.
   */
  @Override
  public void greyscale(String identifier, String greyScaleIdentifier) throws IOException {
    Image image = imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    float[][] kernel = {{0.2126f, 0.7152f, 0.0722f}, {0.2126f, 0.7152f, 0.0722f}, {0.2126f,
            0.7152f, 0.0722f}};
    applyColorTransformation(kernel, (RGBImage) image, greyScaleIdentifier);
  }


  /**
   * The method dithers the image to create a new image.
   *
   * @param identifier       of the image
   * @param ditherIdentifier identifier of the new dithered image
   */
  @Override
  public void dither(String identifier, String ditherIdentifier) throws IOException {
    greyscale(identifier, identifier);
    RGBImage greyScaleImage = (RGBImage) imageMap.get(identifier);
    if( greyScaleImage == null) {
      throw new IOException("Image does not exist!");
    }

    RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
    int width = greyScaleImage.getWidth();
    int height = greyScaleImage.getHeight();
    imageBuilder.identifier(ditherIdentifier);
    imageBuilder.width(width);
    imageBuilder.height(height);
    imageBuilder.pixelMatrix(greyScaleImage.getPixels());
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        int old_color = greyScaleImage.getRPixel(row, column);
        int new_color = (old_color < 128) ? 0 : 255;
        int error = old_color - new_color;
        imageBuilder.pixel(new Pixel(new_color, new_color, new_color), row, column);

        if (column < greyScaleImage.getWidth() - 1) {
          Pixel rightPixel = greyScaleImage.getPixel(row, column + 1);
          int rightError = (int) (error * 7.0 / 16.0);
          int newRed = rightPixel.getRed() + rightError;
          newRed = Math.max(0, Math.min(255, newRed));
          imageBuilder.pixel(row, column + 1, newRed, newRed, newRed);
        }
        if (column > 0 && row < greyScaleImage.getHeight() - 1) {
          Pixel bottomLeftPixel = greyScaleImage.getPixel(row + 1, column - 1);
          int bottomLeftError = (int) (error * 3.0 / 16.0);
          int newRed = bottomLeftPixel.getRed() + bottomLeftError;
          newRed = Math.max(0, Math.min(255, newRed));
          imageBuilder.pixel(row + 1, column - 1, newRed, newRed, newRed);
        }
        if (row < greyScaleImage.getHeight() - 1) {
          Pixel bottomPixel = greyScaleImage.getPixel(row + 1, column);
          int bottomError = (int) (error * 5.0 / 16.0);
          int newRed = bottomPixel.getRed() + bottomError;
          newRed = Math.max(0, Math.min(255, newRed));
          imageBuilder.pixel(row + 1, column, newRed, newRed, newRed);
        }
        if (row < greyScaleImage.getHeight() - 1 && column < greyScaleImage.getWidth() - 1) {
          Pixel bottomRightPixel = greyScaleImage.getPixel(row + 1, column + 1);
          int bottomRightError = (int) (error * 1.0 / 16.0);
          int newRed = bottomRightPixel.getRed() + bottomRightError;
          newRed = Math.max(0, Math.min(255, newRed));
          imageBuilder.pixel(row + 1, column + 1, newRed, newRed, newRed);
        }
      }
    }
    Image ditherImage = imageBuilder.build();
    imageMap.put(ditherIdentifier, ditherImage);
  }

  /**
   * Helper method that applies filter(blur or sharpen) on an image.
   *
   * @param kernel           matrix or filter
   * @param image            to which filter need to be applied.
   * @param filterIdentifier identifier of the newly filtered image.
   */
  private void applyFilter(float[][] kernel, RGBImage image, String filterIdentifier) {
    RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
    int kernelSize = kernel.length;
    int offset = kernelSize / 2;
    int height = image.getHeight();
    int width = image.getWidth();
    imageBuilder.identifier(filterIdentifier);
    imageBuilder.width(width);
    imageBuilder.height(height);


    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int newRed = 0;
        int newGreen = 0;
        int newBlue = 0;

        for (int k = -offset; k <= offset; k++) {
          for (int l = -offset; l <= offset; l++) {
            int rowIndex = i + k;
            int colIndex = j + l;
            if (rowIndex >= 0 && rowIndex < height && colIndex >= 0 && colIndex < width) {
              float kernelValue = kernel[k + offset][l + offset];
              newRed += image.getRPixel(rowIndex, colIndex) * kernelValue;
              newGreen += image.getGPixel(rowIndex, colIndex) * kernelValue;
              newBlue += image.getBPixel(rowIndex, colIndex) * kernelValue;
            }
          }
        }
        imageBuilder.pixel(new Pixel(limit(newRed), limit(newGreen), limit(newBlue)), i, j);
      }
    }
    Image filterImage = imageBuilder.build();
    imageMap.put(filterIdentifier, filterImage);
  }

  /**
   * Helper method that applies color transformation on an image.
   *
   * @param kernel                   matrix or filter
   * @param image                    which is to be color transformed
   * @param transformationIdentifier identifier of the newly transformed image.
   */
  private void applyColorTransformation(float[][] kernel, RGBImage image,
                                        String transformationIdentifier) {
    RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
    int kernelSize = kernel.length;
    int offset = kernelSize / 2;
    int height = image.getHeight();
    int width = image.getWidth();
    imageBuilder.identifier(transformationIdentifier);
    imageBuilder.width(width);
    imageBuilder.height(height);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

        int red = image.getRPixel(i, j);
        int green = image.getGPixel(i, j);
        int blue = image.getBPixel(i, j);
        int newRed = (int) (red * kernel[0][0] + green * kernel[0][1] + blue * kernel[0][2]);
        int newGreen = (int) (red * kernel[1][0] + green * kernel[1][1] + blue * kernel[1][2]);
        int newBlue = (int) (red * kernel[2][0] + green * kernel[2][1] + blue * kernel[2][2]);

        imageBuilder.pixel(new Pixel(limit(newRed), limit(newGreen), limit(newBlue)), i, j);
      }
    }

    Image transformedImage = imageBuilder.build();
    imageMap.put(transformationIdentifier, transformedImage);
  }
}
