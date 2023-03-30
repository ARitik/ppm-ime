package controller;

import java.io.IOException;

import model.ImageOperations;
import model.ImageOperationsBasicPlus;

 class Brighten implements ImageCommand {
  int value;
  String identifier;
  String brightenIdentifier;

  public Brighten(int value, String identifier, String brightenIdentifier) {
    this.value = value;
    this.identifier = identifier;
    this.brightenIdentifier = brightenIdentifier;

  }

  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.brighten(value,identifier,brightenIdentifier);
  }
}
