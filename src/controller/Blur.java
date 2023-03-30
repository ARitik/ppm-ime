package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

/**
 * A class that represents Blur operation on the image.
 */
class Blur implements ImageCommand {
  String identifier;
  String blurIdentifier;

  /**
   * Implements ImageCommand and performs blur on an image.
   *
   * @param identifier     of the image to be blurred
   * @param blurIdentifier new identifier
   */
  public Blur(String identifier, String blurIdentifier) {
    this.identifier = identifier;
    this.blurIdentifier = blurIdentifier;
  }

  /**
   * The method executes the blur in the model.
   *
   * @param model model object
   * @throws IOException image to be blurred does not exist.
   */
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.blur(identifier, blurIdentifier);
  }

}
