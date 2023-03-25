package controller.commands;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

public class Blur implements ImageCommand {
  String identifier;
  String blurIdentifier;
  public Blur(String identifier, String blurIdentifier) {
    this.identifier = identifier;
    this.blurIdentifier = blurIdentifier;
  }
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.blur(identifier, blurIdentifier);
  }

}
