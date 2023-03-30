package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import jdk.jshell.spi.ExecutionControl;


/**
 * This class implements ImageOperations and perform certain operations
 * on an image of ppm format.
 */
public class RGBOperationsBasic implements ImageOperations {
  protected final Map<String, Image> imageMap;


  /**
   * Constructs an object representing RGBOperationsBasic on a RGBImage.
   */
  public RGBOperationsBasic() {
    this.imageMap = new HashMap<String, Image>();
  }

  /**
   * Limits the value of a pixel to an integer between 0 and 255.
   *
   * @param value the value to be limited.
   * @return a value that satisfies pixel class invariants.
   */
  protected int limit(int value) {
    return Math.max(0, Math.min(value, 255));
  }

  private void readPPMImage(InputStream in, String identifier) {
    Scanner sc;
    sc = new Scanner(in);
    StringBuilder builder = new StringBuilder();
    RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
    imageBuilder.identifier(identifier);
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
      throw new IllegalStateException("Invalid PPM file");
    }
    int width = sc.nextInt();
    imageBuilder.width(width);
    int height = sc.nextInt();
    imageBuilder.height(height);
    int maxValue = sc.nextInt();
    imageBuilder.maxValue(maxValue);

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        imageBuilder.pixel(new Pixel(r, g, b), i, j);
      }
    }
    imageMap.put(identifier,imageBuilder.build());
  }

  @Override
  public void loadImage(InputStream in, String type, String identifier) throws IOException {
    if (type.equals("ppm")) {
      readPPMImage(in, identifier);
    } else {
      BufferedImage image = ImageIO.read(in);
      RGBImage.ImageBuilder imageBuilder = RGBImage.getBuilder();
      int width = image.getWidth();
      int height = image.getHeight();
      imageBuilder.identifier(identifier);
      imageBuilder.width(width);
      imageBuilder.height(height);
      for (int j = 0; j < height; j++) {
        for (int i = 0; i < width; i++) {
          int pixel = image.getRGB(i, j);
          int r = (pixel >> 16) & 0xff;
          int g = (pixel >> 8) & 0xff;
          int b = pixel & 0xff;
          imageBuilder.pixel(new Pixel(r, g, b), j, i);
        }
      }
      imageMap.put(identifier,imageBuilder.build());
    }
  }

  public BufferedImage getImage(String identifier) throws IOException {
    if (imageMap.get(identifier) == null) {
      throw new IOException("Image does not exist!");
    }
    RGBImage image = (RGBImage) imageMap.get(identifier);


    int width = image.getWidth();
    int height = image.getHeight();
    BufferedImage imageToBeSaved = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    for (int y = 0; y < height; y++) {
      for (int x = 0; x < width; x++) {
        int r = image.getRPixel(y, x);
        int g = image.getGPixel(y, x);
        int b = image.getBPixel(y, x);
        int rgb = (r << 16) | (g << 8) | b;
        imageToBeSaved.setRGB(x, y, rgb);
      }
    }
    return imageToBeSaved;
  }



  @Override
  public void brighten(int value, String identifier, String brightenIdentifier) throws IOException {
    RGBImage image = (RGBImage) imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    RGBImage.ImageBuilder newImageBuilder = RGBImage
            .getBuilder()
            .identifier(identifier)
            .width(image.getWidth())
            .height(image.getHeight())
            .maxValue(limit(image.getMaxValue() + value));


    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = limit(image.pixels[row][column].getRed() + value);
        int g = limit(image.pixels[row][column].getGreen() + value);
        int b = limit(image.pixels[row][column].getBlue() + value);

        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    Image brightenedImage = newImageBuilder.build();
    imageMap.put(brightenIdentifier, brightenedImage);
  }

  /**
   * Helper method that flips an image horizontally.
   *
   * @param image             to be flipped
   * @param newImageBuilder   builds a new image
   * @param flippedIdentifier idefier of the newly built image
   * @return The image flipped horizontally.
   */
  private Image horizontalFlip(RGBImage image, RGBImage.ImageBuilder newImageBuilder,
                               String flippedIdentifier) {
    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = Math.min(image.getRPixel(row, image.getWidth() - column - 1), 255);
        int g = Math.min(image.getGPixel(row, image.getWidth() - column - 1), 255);
        int b = Math.min(image.getBPixel(row, image.getWidth() - column - 1), 255);
        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    Image flippedImage = newImageBuilder.build();
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
  private Image verticalFlip(RGBImage image, RGBImage.ImageBuilder newImageBuilder,
                             String flippedIdentifier) {

    for (int row = 0; row < image.getHeight(); row++) {
      for (int column = 0; column < image.getWidth(); column++) {
        int r = Math.min(image.getRPixel(image.getHeight() - 1 - row, column), 255);
        int g = Math.min(image.getGPixel(image.getHeight() - 1 - row, column), 255);
        int b = Math.min(image.getBPixel(image.getHeight() - 1 - row, column), 255);
        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    Image flippedImage = newImageBuilder.build();
    imageMap.put(flippedIdentifier, flippedImage);
    return flippedImage;
  }

  @Override
  public void flip(String orientation, String identifier, String flippedIdentifier) throws IOException {
    RGBImage image = (RGBImage) imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    RGBImage.ImageBuilder newImageBuilder = RGBImage
            .getBuilder()
            .identifier(identifier)
            .width(image.getWidth())
            .height(image.getHeight())
            .maxValue(image.getMaxValue());

    if (orientation.equalsIgnoreCase("horizontal-flip")) {
      horizontalFlip(image, newImageBuilder, flippedIdentifier);
    } else {
      verticalFlip(image, newImageBuilder, flippedIdentifier);
    }
  }

  @Override
  public void greyscale(String component, String identifier, String greyScaleIdentifier) throws IOException, ExecutionControl.NotImplementedException {
    RGBImage.ImageBuilder grayscaleImage = RGBImage.getBuilder();
    RGBImage image = (RGBImage) imageMap.get(identifier);
    if (image == null) {
      throw new IOException("Image does not exist!");
    }
    splitIntoComponent(image, grayscaleImage, greyScaleIdentifier, component);
  }

  @Override
  public void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                       String blueIdentifier) throws IOException,
          ExecutionControl.NotImplementedException {
    if (imageMap.get(identifier) == null) {
      throw new IOException("Image does not exist!");
    }
    RGBImage.ImageBuilder redChannel = RGBImage.getBuilder();
    RGBImage.ImageBuilder greenChannel = RGBImage.getBuilder();
    RGBImage.ImageBuilder blueChannel = RGBImage.getBuilder();
    RGBImage image = (RGBImage) imageMap.get(identifier);
    RGBImage redChannelImage = (RGBImage) splitIntoComponent(image, redChannel, redIdentifier,
            "red");
    RGBImage greenChannelImage = (RGBImage) splitIntoComponent(image, greenChannel, greenIdentifier,
            "green");
    RGBImage blueChannelImage = (RGBImage) splitIntoComponent(image, blueChannel, blueIdentifier,
            "blue");
    List<Image> channelImages = new ArrayList<Image>();
    channelImages.add(redChannelImage);
    channelImages.add(blueChannelImage);
    channelImages.add(greenChannelImage);
  }

  /**
   * Create a Pixel Matrix based on the component supplied.
   *
   * @param builder   The builder object used to build the pixel matrix for an image.
   * @param component can be red,green,blue,value,luma,intensity.
   * @param height    the height of the image.
   * @param width     the width of the image.
   */
  private void createPixelsBasedOnComponent(RGBImage.ImageBuilder builder, int[][] component,
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
   * @param image             The image to be split.
   * @param builder           the builder object for the new image.
   * @param channelIdentifier the identifier of the channel.
   * @param type              the component based on which image is split.
   * @return the greyscale image split by channel.
   */
  private Image splitIntoComponent(RGBImage image, RGBImage.ImageBuilder builder,
                                   String channelIdentifier,
                                   String type) throws ExecutionControl.NotImplementedException {
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
        throw new ExecutionControl.NotImplementedException("Invalid Component!");
    }
    Image greyScaleImage = builder.build();
    imageMap.put(channelIdentifier, greyScaleImage);
    return greyScaleImage;
  }

  @Override
  public void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                         String blueIdentifier) throws IOException {
    if (imageMap.get(redIdentifier) == null || imageMap.get(blueIdentifier) == null
            || imageMap.get(greenIdentifier) == null) {
      throw new IOException("Image does not exist!");
    }
    RGBImage redImage = (RGBImage) imageMap.get(redIdentifier);
    RGBImage greenImage = (RGBImage) imageMap.get(greenIdentifier);
    RGBImage blueImage = (RGBImage) imageMap.get(blueIdentifier);
    int redMaxValue = redImage.getMaxValue();
    int greenMaxValue = greenImage.getMaxValue();
    int blueMaxValue = blueImage.getMaxValue();
    int maxValue = Math.max(redMaxValue, Math.max(greenMaxValue, blueMaxValue));
    int width = redImage.getWidth();
    int height = redImage.getHeight();
    RGBImage.ImageBuilder newImageBuilder = RGBImage
            .getBuilder()
            .identifier(identifier)
            .width(width)
            .height(height)
            .maxValue(maxValue);

    for (int row = 0; row < height; row++) {
      for (int column = 0; column < width; column++) {
        int r = redImage.getRPixel(row, column);
        int g = greenImage.getGPixel(row, column);
        int b = blueImage.getBPixel(row, column);
        newImageBuilder.pixel(new Pixel(r, g, b), row, column);
      }
    }
    Image combinedImage = newImageBuilder.build();
    imageMap.put(identifier, combinedImage);
  }
}
