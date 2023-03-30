package model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import jdk.jshell.spi.ExecutionControl;

/**
 * An interface that represents ImageOperations and contains methods that perform
 * some operation on the ppm image.
 */
public interface ImageOperations {

  /**
   * The method loads an image from the specified path and refer it by the given image name.
   *
   * @param identifier of the image
   */

  void loadImage(InputStream in, String type, String identifier) throws IOException;

  /**
   * The method saves the image with the given name to the specified path.
   *
   * @param identifier of the image
   * @return image to be saved
   */
  BufferedImage getImage(String identifier) throws IOException;


  /**
   * The method brightens the image by the given increment to create a new image.
   *
   * @param value              by which the image is to be brightened.
   * @param identifier         of the image
   * @param brightenIdentifier identifier of the new brightened image
   */

  void brighten(int value, String identifier, String brightenIdentifier) throws IOException;

  /**
   * The method flips an image according to the orientation to create a new image.
   *
   * @param orientation       image to be flipped horizontally or vertically.
   * @param identifier        of the image
   * @param flippedIdentifier identifier of the flipped image
   */
  void flip(String orientation, String identifier, String flippedIdentifier) throws IOException;

  /**
   * The method creates a greyscale image with the given component.
   *
   * @param component           either red, blue, green, luma, value, intensity
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new grey-scaled image.
   */
  void greyscale(String component, String identifier, String greyScaleIdentifier)
          throws IOException, ExecutionControl.NotImplementedException;

  /**
   * The method splits the given image into three greyscale images
   * containing its red, green and blue components.
   *
   * @param identifier      of the image
   * @param redIdentifier   of image
   * @param greenIdentifier of image
   * @param blueIdentifier  of image
   */
  void rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                String blueIdentifier) throws IOException,
          ExecutionControl.NotImplementedException;

  /**
   * The method combine the three greyscale images into a single image
   * that gets its red, green and blue components from the three images.
   *
   * @param identifier      of the image
   * @param redIdentifier   of the image
   * @param greenIdentifier of the image
   * @param blueIdentifier  of the image
   */
  void rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                  String blueIdentifier) throws IOException;


}
