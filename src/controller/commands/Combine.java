package controller.commands;

import model.ImageOperations;

public class Combine implements ImageCommand {
  String identifier;
  String redIdentifier;
  String blueIdentifier;
  String greenIdentifier;

  public Combine(String identifier, String redIdentifier, String greenIdentifier,
            String blueIdentifier) {
    this.identifier = identifier;
    this.redIdentifier = redIdentifier;
    this.blueIdentifier = blueIdentifier;
    this.greenIdentifier = greenIdentifier;
  }

  public void execute(ImageOperations model) {
    model.rgbCombine(identifier,redIdentifier,greenIdentifier,blueIdentifier);
  }
}
