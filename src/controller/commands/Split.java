package controller.commands;

import model.ImageOperations;

public class Split implements ImageCommand {
  private String identifier;
  private String redIdentifier;
  private String greenIdentifier;
  private String blueIdentifier;

  public Split(String identifier, String redIdentifer, String blueIdentifier,
               String greenIdentifier) {
    this.identifier = identifier;
    this.redIdentifier = redIdentifer;
    this.blueIdentifier = blueIdentifier;
    this.greenIdentifier = greenIdentifier;
  }

  public void execute(ImageOperations model) {
    model.greyscale("red", identifier, redIdentifier);
    model.greyscale("green", identifier, greenIdentifier);
    model.greyscale("blue", identifier, blueIdentifier);
  }
}
