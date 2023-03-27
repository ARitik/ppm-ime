package controller.commands;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

public class Dither implements ImageCommand {
  String identifier;
  String ditherIdentifier;
  public Dither(String identifier, String ditherIdentifier) {
    this.identifier = identifier;
    this.ditherIdentifier = ditherIdentifier;
  }
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.dither(identifier, ditherIdentifier);
  }
}
