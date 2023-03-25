package controller.commands;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

public class Sepia implements ImageCommand {
  String identifier;
  String sepiaIdentifier;

  public Sepia(String identifier, String sepiaIdentifier) {
    this.identifier = identifier;
    this.sepiaIdentifier = sepiaIdentifier;
  }
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.sepia(identifier, sepiaIdentifier);
  }
}
