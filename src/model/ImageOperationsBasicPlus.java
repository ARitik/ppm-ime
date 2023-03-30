package model;


/**
 * An interface that represents ImageOperationsBasicPlus and contains methods that perform
 * some operation on an image.
 */
public interface ImageOperationsBasicPlus extends ImageOperations {

  /**
   * The method blurs the image to create a new image.
   *
   * @param identifier     of the image
   * @param blurIdentifier identifier of the new blurred image.
   */
  void blur(String identifier, String blurIdentifier);

  /**
   * The method sharpens the image to create a new image.
   *
   * @param identifier        of the image
   * @param sharpenIdentifier identifier of the new sharpened image.
   */

  void sharpen(String identifier, String sharpenIdentifier);

  /**
   * The method gives the image a sepia tone.
   *
   * @param identifier      of the image
   * @param sepiaIdentifier identifier of the new sepia image.
   */
  void sepia(String identifier, String sepiaIdentifier);

  /**
   * The method gives the image a greyscale tone.
   *
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new greyscale image.
   */

  void greyscale(String identifier, String greyScaleIdentifier);

  /**
   * The method dithers the image to create a new image.
   *
   * @param identifier       of the image
   * @param ditherIdentifier identifier of the new dithered image
   */

  void dither(String identifier, String ditherIdentifier);
}
