package model;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * This class implements ImageOperations and contains methods that perform
 * some operation on the image.
 */
public interface ImageOperations {
  /**
   * The method loads an image from the specified path and refer it by the given image name.
   *
   * @param filePath   path of the image
   * @param identifier identifies the given image
   * @return updated hashmap containing all images.
   */
  Map<String, RGBImage> load(String filePath, String identifier);

  /**
   * The method saves the image with the given name to the specified path.
   *
   * @param savePath        path at which the image has to be saved.
   * @param imageIdentifier identifier of the image
   * @return the saved image.
   * @throws IOException when the image path is not found. In this case the image
   *                     cannot be saved.
   */
  Image save(String savePath, String imageIdentifier) throws IOException;

  /**
   * The method brightens the image by the given increment to create a new image.
   *
   * @param value              by which the image is to be brightened.
   * @param identifier         of the image
   * @param brightenIdentifier identifier of the new brightened image
   * @return the brightened image.
   */

  Image brighten(int value, String identifier, String brightenIdentifier);

  /**
   * The method flips an image according to the orientation to create a new image.
   *
   * @param orientation       image to be flipped horizontally or vertically.
   * @param identifier        of the image
   * @param flippedIdentifier identifier of the flipped image
   * @return the flipped image.
   */
  Image flip(String orientation, String identifier, String flippedIdentifier);

  /**
   * The method creates a greyscale image with the given component.
   *
   * @param component           either red, blue, green, luma, value, intensity
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new grey-scaled image.
   * @return the greyscale image.
   */
  Image greyscale(String component, String identifier, String greyScaleIdentifier);

  /**
   * The method splits the given image into three greyscale images
   * containing its red, green and blue components.
   *
   * @param identifier      of the image
   * @param redIdentifier   of image
   * @param greenIdentifier of image
   * @param blueIdentifier  of image
   * @return list containing all the channels of an image.
   */
  List<Image> rgbSplit(String identifier, String redIdentifier, String greenIdentifier,
                       String blueIdentifier);

  /**
   * The method combine the three greyscale images into a single image
   * that gets its red, green and blue components from the three images.
   *
   * @param identifier      of the image
   * @param redIdentifier   of the image
   * @param greenIdentifier of the image
   * @param blueIdentifier  of the image
   * @return the combined image.
   */
  Image rgbCombine(String identifier, String redIdentifier, String greenIdentifier,
                   String blueIdentifier);
}
