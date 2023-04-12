package controller;

import java.io.IOException;

import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;
import view.AppView;


/**
 * A class that represents Split operation on the image.
 */
class Split implements ImageCommand {
  private String identifier;
  private String redIdentifier;
  private String greenIdentifier;
  private String blueIdentifier;

  /**
   * Implements ImageCommand and splits images.
   *
   * @param identifier      of the image
   * @param redIdentifer    of the image
   * @param blueIdentifier  of the image
   * @param greenIdentifier of the image
   */
  public Split(String identifier, String redIdentifer, String blueIdentifier,
               String greenIdentifier) {
    this.identifier = identifier;
    this.redIdentifier = redIdentifer;
    this.blueIdentifier = blueIdentifier;
    this.greenIdentifier = greenIdentifier;
  }

  /**
   * The method executes rgbsplit in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException,
          ExecutionControl.NotImplementedException {
    model.greyscale("red", identifier, redIdentifier);
    model.greyscale("green", identifier, greenIdentifier);
    model.greyscale("blue", identifier, blueIdentifier);
    view.setImage(model.getImage(redIdentifier));
    view.setImage(model.getImage(greenIdentifier));
    view.setImage(model.getImage(blueIdentifier));
  }
}
