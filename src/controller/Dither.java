package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

/**
 * A class that represents Dither operation on the image.
 */
class Dither implements ImageCommand {
  String identifier;
  String ditherIdentifier;

  /**
   * Implements ImageCommand and dithers an image.
   *
   * @param identifier       of the image
   * @param ditherIdentifier identifier of the new dithered image
   */
  public Dither(String identifier, String ditherIdentifier) {
    this.identifier = identifier;
    this.ditherIdentifier = ditherIdentifier;
  }

  /**
   * The method executes the dither in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.dither(identifier, ditherIdentifier);
  }
}
