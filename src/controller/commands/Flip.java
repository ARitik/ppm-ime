package controller.commands;

import model.ImageOperations;

public class Flip implements ImageCommand {
  String orientation;
  String identifier;
  String flippedIdentifier;
  public Flip(String orientation, String identifier, String flippedIdentifier) {
    this.orientation = orientation;
    this.identifier = identifier;
    this.flippedIdentifier = flippedIdentifier;
  }

  public void execute(ImageOperations model) {
    model.flip(orientation,identifier,flippedIdentifier);
  }
}
