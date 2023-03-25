package controller.commands;

import model.ImageOperationsBasicPlus;

public class Flip implements ImageCommand {
  String orientation;
  String identifier;
  String flippedIdentifier;
  public Flip(String orientation, String identifier, String flippedIdentifier) {
    this.orientation = orientation;
    this.identifier = identifier;
    this.flippedIdentifier = flippedIdentifier;
  }

  public void execute(ImageOperationsBasicPlus model) {
    model.flip(orientation,identifier,flippedIdentifier);
  }
}
