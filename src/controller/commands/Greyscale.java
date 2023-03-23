package controller.commands;

import model.ImageOperations;

public class Greyscale implements ImageCommand {
  String component;
  String identifier;
  String greyScaleIdentifier;
  public Greyscale(String component, String identifier, String greyScaleIdentifier) {
    this.component = component;
    this.identifier = identifier;
    this.greyScaleIdentifier = greyScaleIdentifier;

  }

  public void execute(ImageOperations model) {
    model.greyscale(component,identifier,greyScaleIdentifier);
  }
}
