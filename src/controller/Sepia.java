package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;


/**
 * A class that represents Sepia operation on the image.
 */
class Sepia implements ImageCommand {
  String identifier;
  String sepiaIdentifier;

  /**
   * Implements ImageCommand and given an image sepia tone.
   *
   * @param identifier      of the image
   * @param sepiaIdentifier identifier of the new sepia image.
   */
  public Sepia(String identifier, String sepiaIdentifier) {
    this.identifier = identifier;
    this.sepiaIdentifier = sepiaIdentifier;
  }

  /**
   * The method executes the sepia in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.sepia(identifier, sepiaIdentifier);
  }
}
