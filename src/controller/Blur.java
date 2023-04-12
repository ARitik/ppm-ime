package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * A class that represents Blur operation on the image.
 */
class Blur implements ImageCommand {
  private final String identifier;
  private final String blurIdentifier;

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
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException {
    model.blur(identifier, blurIdentifier);
    view.setImage(model.getImage(blurIdentifier));
  }

}
