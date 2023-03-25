package controller.commands;

import model.ImageOperations;
import model.ImageOperationsBasicPlus;

public class Brighten implements ImageCommand {
  int value;
  String identifier;
  String brightenIdentifier;

  public Brighten(int value, String identifier, String brightenIdentifier) {
    this.value = value;
    this.identifier = identifier;
    this.brightenIdentifier = brightenIdentifier;

  }

  public void execute(ImageOperationsBasicPlus model) {
    model.brighten(value,identifier,brightenIdentifier);
  }
}
