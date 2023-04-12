package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * A class that represents sharpen operation on the image.
 */
class Sharpen implements ImageCommand {
  private final String identifier;
  private final String sharpenIdentifier;

  /**
   * Implements ImageCommand and sharpens an image.
   *
   * @param identifier        of the image
   * @param sharpenIdentifier identifier of the new sharpened image.
   */
  public Sharpen(String identifier, String sharpenIdentifier) {
    this.identifier = identifier;
    this.sharpenIdentifier = sharpenIdentifier;
  }

  /**
   * The method executes the sharpen in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  @Override
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException {
    model.sharpen(identifier, sharpenIdentifier);
    view.setImage(model.getImage(sharpenIdentifier));
  }
}
