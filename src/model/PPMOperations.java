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


  public PPMOperations() {
    this.imageMap = new HashMap<String, PPMImage>();
  }

  private int limit(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  /**
   * The method loads an image from the specified path and refer it by the given image name.
   *
   * @param filePath   path of the image
   * @param identifier identifies the given image
   */
  @Override
  public Map<String, PPMImage> load(String filePath, String identifier) {
      PPMImage image = (PPMImage) ImageUtil.readPPM(filePath, identifier);
      if(image != null) {
        imageMap.put(identifier, image);
      }
    return imageMap;
  }

  /**
   * The method saves the image with the given name to the specified path.
   *
   * @param savePath        path at which the image has to be saved.
   * @param imageIdentifier identifier of the image
   * @throws IOException when the image path is not found. In this case the image
   *                     cannot be saved.
   */
  @Override
  public Image save(String savePath, String imageIdentifier) throws IOException {
    if (imageMap.get(imageIdentifier) == null) {
      return null;
    }
    ImageUtil.savePPM(savePath, imageMap.get(imageIdentifier));
    return imageMap.get(imageIdentifier);
  }

  /**
   * The method brightens the image by the given increment to create a new image.
   *
   * @param value              by which the image is to be brightened.
   * @param identifier         of the image
   * @param brightenIdentifier identifier of the new brightened image
   */
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

  /**
   * The method flips an image according to the orientation to create a new image
   *
   * @param orientation       image to be flipped horizontally or vertically.
   * @param identifier        of the image
   * @param flippedIdentifier identifier of the flipped image
   */
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

  /**
   * The method creates a greyscale image with the given component.
   *
   * @param component           either red, blue, green, luma, value, intensity
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new grey-scaled image.
   */
  @Override
  public Image greyscale(String component, String identifier, String greyScaleIdentifier) {
    PPMImage.ImageBuilder grayscaleImage = PPMImage.getBuilder();
    PPMImage image = imageMap.get(identifier);
    return splitIntoComponent(image, grayscaleImage, greyScaleIdentifier, component);
  }

  /**
   * The method splits the given image into three greyscale images
   * containing its red, green and blue components.
   *
   * @param identifier      of the image
   * @param redIdentifier   of image
   * @param greenIdentifier of image
   * @param blueIdentifier  of image
   */
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
        break;
    }
    PPMImage greyScaleImage = builder.build();
    imageMap.put(channelIdentifier, greyScaleImage);
    return greyScaleImage;
  }

  /**
   * The method combine the three greyscale images into a single image
   * that gets its red, green and blue components from the three images.
   *
   * @param identifier      of the image
   * @param redIdentifier   of the image
   * @param greenIdentifier of the image
   * @param blueIdentifier  of the image
   */
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
