package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

 class Flip implements ImageCommand {
  String orientation;
  String identifier;
  String flippedIdentifier;
  public Flip(String orientation, String identifier, String flippedIdentifier) {
    this.orientation = orientation;
    this.identifier = identifier;
    this.flippedIdentifier = flippedIdentifier;
  }

  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.flip(orientation,identifier,flippedIdentifier);
  }
}
