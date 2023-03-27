package model;

public class RGBOperationsBasicPlus
        extends RGBOperationsBasic
        implements ImageOperationsBasicPlus {

  public RGBOperationsBasicPlus() {
    super();
  }

  @Override
  public Image blur(String identifier, String blurIdentifier) {
    RGBImage image = (RGBImage) imageMap.get(identifier);
    float[][] kernel = {{1 / 16f, 2 / 16f, 1 / 16f},
            {2 / 16f, 4 / 16f, 2 / 16f},
            {1 / 16f, 2 / 16f, 1 / 16f}};

    return applyFilter(kernel, image, blurIdentifier);
  }

  @Override
  public Image sharpen(String identifier, String sharpenIdentifier) {
    RGBImage image = (RGBImage) imageMap.get(identifier);
    float[][] kernel = {{-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f},
            {-1 / 8f, 2 / 8f, 2 / 8f, 2 / 8f, -1 / 8f},
            {-1 / 8f, 2 / 8f, 1, 2 / 8f, -1 / 8f},
            {-1 / 8f, 2 / 8f, 2 / 8f, 2 / 8f, -1 / 8f},
            {-1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f, -1 / 8f}
    };
    return applyFilter(kernel, image, sharpenIdentifier);
  }

  @Override
  public Image sepia(String identifier, String sepiaIdentifier) {
    RGBImage image = (RGBImage) imageMap.get(identifier);
    float[][] kernel = {{0.393f, 0.769f, 0.189f},
            {0.349f, 0.686f, 0.168f},
            {0.272f, 0.534f, 0.131f}};
    return applyColorTransformation(kernel, image, sepiaIdentifier);
  }

  @Override
  public Image greyscale(String identifier, String greyScaleIdentifier) {
    RGBImage image = (RGBImage) imageMap.get(identifier);
    float[][] kernel = {{0.2126f, 0.7152f, 0.0722f}, {0.2126f, 0.7152f, 0.0722f}, {0.2126f,
            0.7152f, 0.0722f}};
    return applyColorTransformation(kernel, image, greyScaleIdentifier);
  }


  @Override
  public Image dither(String identifier, String ditherIdentifier) {
//    RGBImage greyScaleImage = (RGBImage) this.greyscale("luma", identifier, identifier);
    RGBImage greyScaleImage = (RGBImage) this.greyscale( identifier, identifier);
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
//        int currentPixel = greyScaleImage.getRPixel(row, column);
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
    RGBImage ditherImage = imageBuilder.build();
    imageMap.put(ditherIdentifier, ditherImage);
    return ditherImage;
  }

  private Image applyFilter(float[][] kernel, RGBImage image, String filterIdentifier) {
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
    RGBImage filterImage = imageBuilder.build();
    imageMap.put(filterIdentifier, filterImage);
    return filterImage;
  }

  private Image applyColorTransformation(float[][] kernel, RGBImage image,
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

    RGBImage transformedImage = imageBuilder.build();
    imageMap.put(transformationIdentifier, transformedImage);
    return transformedImage;
  }
}