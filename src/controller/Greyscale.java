package controller;

import java.io.IOException;

import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * A class that represents greyscale operation on the image.
 */
class Greyscale implements ImageCommand {
  String component;
  String identifier;
  String greyScaleIdentifier;

  /**
   * Implements ImageCommand and greyscale an image on the basis of component.
   *
   * @param component           either red, blue, green, luma, value, intensity
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new grey-scaled image.
   */
  public Greyscale(String component, String identifier, String greyScaleIdentifier) {
    this.component = component;
    this.identifier = identifier;
    this.greyScaleIdentifier = greyScaleIdentifier;
  }

  /**
   * Implements ImageCommand and greyscale an image.
   *
   * @param identifier          of the image
   * @param greyScaleIdentifier identifier of the new grey-scaled image.
   */
  public Greyscale(String identifier, String greyScaleIdentifier) {
    this.component = null;
    this.identifier = identifier;
    this.greyScaleIdentifier = greyScaleIdentifier;
  }

  /**
   * The method executes the greyscale in the model.
   *
   * @param model model object
   * @throws IOException wherever required
   */
  public void execute(ImageOperationsBasicPlus model, AppView view) throws IOException,
          ExecutionControl.NotImplementedException {
    if (this.component == null) {
      model.greyscale(identifier, greyScaleIdentifier);
    } else {
      model.greyscale(component, identifier, greyScaleIdentifier);
    }
    view.setImage(model.getImage(greyScaleIdentifier));
  }
}
