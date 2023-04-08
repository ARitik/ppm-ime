package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * A class that represents Brighten operation on the image.
 */
class Brighten implements ImageCommand {
  int value;
  String identifier;
  String brightenIdentifier;

  /**
   * Implements ImageCommand and brightens an image.
   *
   * @param value              by which the image is to be brightened.
   * @param identifier         of the image
   * @param brightenIdentifier identifier of the new brightened image
   */
  public Brighten(int value, String identifier, String brightenIdentifier) {
    this.value = value;
    this.identifier = identifier;
    this.brightenIdentifier = brightenIdentifier;

  }

  /**
   * The method executes the brighten in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */

  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException {
    model.brighten(value, identifier, brightenIdentifier);
    view.setImage(model.getImage(brightenIdentifier));
  }
}
