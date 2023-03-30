package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

/**
 * A class that represents Combine operation on the image.
 */
class Combine implements ImageCommand {
  String identifier;
  String redIdentifier;
  String blueIdentifier;
  String greenIdentifier;

  /**
   * Implements ImageCommand and combines images.
   *
   * @param identifier      of the image
   * @param redIdentifier   of the image
   * @param greenIdentifier of the image
   * @param blueIdentifier  of the image
   */
  public Combine(String identifier, String redIdentifier, String greenIdentifier,
                 String blueIdentifier) {
    this.identifier = identifier;
    this.redIdentifier = redIdentifier;
    this.blueIdentifier = blueIdentifier;
    this.greenIdentifier = greenIdentifier;
  }

  /**
   * The method executes the rgbcombine in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.rgbCombine(identifier, redIdentifier, greenIdentifier, blueIdentifier);
  }
}
