package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * A class that represents Flip operation on the image.
 */
class Flip implements ImageCommand {
  private final String orientation;
  private final String identifier;
  private final String flippedIdentifier;

  /**
   * Implements ImageCommand and flips an image.
   *
   * @param orientation       image to be flipped horizontally or vertically.
   * @param identifier        of the image
   * @param flippedIdentifier identifier of the flipped image
   */
  public Flip(String orientation, String identifier, String flippedIdentifier) {
    this.orientation = orientation;
    this.identifier = identifier;
    this.flippedIdentifier = flippedIdentifier;
  }

  /**
   * The method executes the flip in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException {
    model.flip(orientation, identifier, flippedIdentifier);
    view.setImage(model.getImage(flippedIdentifier));
  }
}
