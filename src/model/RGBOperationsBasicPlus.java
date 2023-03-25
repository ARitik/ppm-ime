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

    applyFilter(kernel, image, blurIdentifier);
    return null;
  }

  @Override
  public Image sharpen(String identifier, String blurIdentifier) {
    return null;
  }

  @Override
  public Image sepia(String identifier, String sepiaIdentifier) {
    RGBImage image = (RGBImage) imageMap.get(identifier);
     float[][] kernel = {{0.393f, 0.769f, 0.189f},
                                 {0.349f, 0.686f, 0.168f},
                                 {0.272f, 0.534f, 0.131f}};
    applyColorTransformation(kernel, image, sepiaIdentifier);
    return null;
  }

  @Override
  public Image greyscale(String identifier, String greyScaleIdentifier) {
    return null;
  }

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
        imageBuilder.pixel(new Pixel(newRed, newGreen, newBlue), i, j);
      }
    }
    imageMap.put(filterIdentifier, imageBuilder.build());
  }

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

    imageMap.put(transformationIdentifier, imageBuilder.build());
  }
}
