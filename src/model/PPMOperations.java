package model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.ImageUtil;

/**
 * This class implements ImageOperations and perform certain operations
 * on an image of ppm format.
 */
public class PPMOperations implements ImageOperations {
  private final Map<String, PPMImage> imageMap;


  /**
   * Constructs an object representing PPMOperations on a PPMImage.
   */
  public PPMOperations() {
    this.imageMap = new HashMap<String, PPMImage>();
  }

  /**
   * Limits the value of a pixel to an integer between 0 and 255.
   *
   * @param value the value to be limited.
   * @return a value that satisfies pixel class invariants.
   */
  private int limit(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  @Override
  public Map<String, PPMImage> load(String filePath, String identifier) {
      PPMImage image = (PPMImage) ImageUtil.readPPM(filePath, identifier);
      if(image != null) {
        imageMap.put(identifier, image);
      }
    return imageMap;
  }

  @Override
  public Image save(String savePath, String imageIdentifier) throws IOException {
    if (imageMap.get(imageIdentifier) == null) {
      return null;
    }
    ImageUtil.savePPM(savePath, imageMap.get(imageIdentifier));
    return imageMap.get(imageIdentifier);
  }

  @Override
  public Image brighten(int value, String identifier, String brightenIdentifier) {
    PPMImage image = imageMap.get(identifier);
    if(image == null) {
      return null;
    }
    PPMImage.ImageBuilder newImageBuilder = PPMImage
            .getBuilder()
            .identifier(identifier)
            .width(image.getWidth())
            .height(image.getHeight())
            .maxValue(Math.min(255, image.getMaxValue() + value));


    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = limit(image.pixels[row][column].getR() + value);
        int g = limit(image.pixels[row][column].getG() + value);
        int b = limit(image.pixels[row][column].getB() + value);

        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    PPMImage brightenedImage = newImageBuilder.build();
    imageMap.put(brightenIdentifier, brightenedImage);
    return brightenedImage;
  }

  /**
   * Helper method that flips an image horizontally.
   *
   * @param image             to be flipped
   * @param newImageBuilder   builds a new image
   * @param flippedIdentifier idefier of the newly built image
   * @return The image flipped horizontally.
   */
  private Image horizontalFlip(PPMImage image, PPMImage.ImageBuilder newImageBuilder,
                               String flippedIdentifier) {
    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = Math.min(image.getRPixel(row, image.getWidth() - column - 1), 255);
        int g = Math.min(image.getGPixel(row, image.getWidth() - column - 1), 255);
        int b = Math.min(image.getBPixel(row, image.getWidth() - column - 1), 255);
        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    PPMImage flippedImage = newImageBuilder.build();
    imageMap.put(flippedIdentifier, flippedImage);
    return flippedImage;
  }

  /**
   * Helper method that flips an image vertically.
   *
   * @param image             to be flipped
   * @param newImageBuilder   builds a new image
   * @param flippedIdentifier idefier of the newly built image
   * @return the image flipped vertically.
   */
  private Image verticalFlip(PPMImage image, PPMImage.ImageBuilder newImageBuilder,
                             String flippedIdentifier) {

    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = Math.min(image.getRPixel(image.getHeight() - 1 - row, column), 255);
        int g = Math.min(image.getGPixel(image.getHeight() - 1 - row, column), 255);
        int b = Math.min(image.getBPixel(image.getHeight() - 1 - row, column), 255);
        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    PPMImage flippedImage = newImageBuilder.build();
    imageMap.put(flippedIdentifier, flippedImage);
    return flippedImage;
  }

  @Override
  public Image flip(String orientation, String identifier, String flippedIdentifier) {
    PPMImage image = imageMap.get(identifier);
    if(image == null) {
      return null;
    }
    PPMImage.ImageBuilder newImageBuilder = PPMImage
            .getBuilder()
            .identifier(identifier)
            .width(image.getWidth())
            .height(image.getHeight())
            .maxValue(image.getMaxValue());

    if (orientation.equalsIgnoreCase("horizontal-flip")) {
      return horizontalFlip(image, newImageBuilder, flippedIdentifier);
    } else {
      return verticalFlip(image, newImageBuilder, flippedIdentifier);
    }
  }

  @Override
  public Image greyscale(String component, String identifier, String greyScaleIdentifier) {
    PPMImage.ImageBuilder grayscaleImage = PPMImage.getBuilder();
    PPMImage image = imageMap.get(identifier);
    return splitIntoComponent(image, grayscaleImage, greyScaleIdentifier, component);
  }

  @Override
  public List<Image> rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                              String blueIdentifier) {
    if(imageMap.get(identifier) == null) {
      return null;
    }
    PPMImage.ImageBuilder redChannel = PPMImage.getBuilder();
    PPMImage.ImageBuilder greenChannel = PPMImage.getBuilder();
    PPMImage.ImageBuilder blueChannel = PPMImage.getBuilder();
    PPMImage image = imageMap.get(identifier);
    PPMImage redChannelImage = (PPMImage) splitIntoComponent(image, redChannel, redIdentifier,
            "red");
    PPMImage greenChannelImage = (PPMImage) splitIntoComponent(image, greenChannel, greenIdentifier,
            "green");
    PPMImage blueChannelImage = (PPMImage) splitIntoComponent(image, blueChannel, blueIdentifier, "blue");
    List<Image> channelImages= new ArrayList<Image>();
    channelImages.add(redChannelImage);
    channelImages.add(blueChannelImage);
    channelImages.add(greenChannelImage);
    return channelImages;
  }

  /**
   * Create a Pixel Matrix based on the component supplied.
   *
   * @param builder The builder object used to build the pixel matrix for an image.
   * @param component can be red,green,blue,value,luma,intensity.
   * @param height the height of the image.
   * @param width the width of the image.
   */
  private void createPixelsBasedOnComponent(PPMImage.ImageBuilder builder, int[][] component,
                                            int height, int width) {
    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        int basePixel = component[row][column];
        builder.pixel(new Pixel(basePixel, basePixel, basePixel), row, column);
      }
    }
  }

  /**
   * Splits a given image by its corresponding channel.
   *
   * @param image The image to be split.
   * @param builder the builder object for the new image.
   * @param channelIdentifier the identifier of the channel.
   * @param type the component based on which image is split.
   * @return the greyscale image split by channel.
   */
  private Image splitIntoComponent(PPMImage image, PPMImage.ImageBuilder builder,
                                   String channelIdentifier,
                                   String type) {
    int height = image.getHeight();
    int width = image.getWidth();
    builder.identifier(channelIdentifier)
            .width(image.getWidth())
            .height(image.getHeight())
            .maxValue(image.getMaxValue());
    switch (type) {
      case "red":
        createPixelsBasedOnComponent(builder, image.getRMatrix(), height, width);
        break;
      case "green":
        createPixelsBasedOnComponent(builder, image.getGMatrix(), height, width);
        break;
      case "blue":
        createPixelsBasedOnComponent(builder, image.getBMatrix(), height, width);
        break;
      case "value":
        createPixelsBasedOnComponent(builder, image.getValueMatrix(), height, width);
        break;
      case "intensity":
        createPixelsBasedOnComponent(builder, image.getIntensityMatrix(), height, width);
        break;
      case "luma":
        createPixelsBasedOnComponent(builder, image.getLumaMatrix(), height, width);
        break;
      default:
        return null;
    }
    PPMImage greyScaleImage = builder.build();
    imageMap.put(channelIdentifier, greyScaleImage);
    return greyScaleImage;
  }

  @Override
  public Image rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                          String blueIdentifier) {
    if(imageMap.get(redIdentifier) == null || imageMap.get(blueIdentifier) == null ||
    imageMap.get(greenIdentifier) == null) {
      return null;
    }
    int redMaxValue = imageMap.get(redIdentifier).getMaxValue();
    int greenMaxValue = imageMap.get(greenIdentifier).getMaxValue();
    int blueMaxValue = imageMap.get(blueIdentifier).getMaxValue();
    int maxValue = Math.max(redMaxValue, Math.max(greenMaxValue, blueMaxValue));
    int width = imageMap.get(redIdentifier).getWidth();
    int height = imageMap.get(redIdentifier).getHeight();
    PPMImage.ImageBuilder newImageBuilder = PPMImage
            .getBuilder()
            .identifier(identifier)
            .width(imageMap.get(redIdentifier).getWidth())
            .height(imageMap.get(redIdentifier).getHeight())
            .maxValue(maxValue);

    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        int r = imageMap.get(redIdentifier).getRPixel(row, column);
        int g = imageMap.get(greenIdentifier).getGPixel(row, column);
        int b = imageMap.get(blueIdentifier).getBPixel(row, column);
        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    PPMImage combinedImage = newImageBuilder.build();
    imageMap.put(identifier, combinedImage);
    return combinedImage;
  }
}
