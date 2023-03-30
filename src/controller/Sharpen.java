package controller;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

 class Sharpen implements ImageCommand {
  String identifier;
  String sharpenIdentifier;
  public Sharpen(String identifier, String sharpenIdentifier) {
    this.identifier = identifier;
    this.sharpenIdentifier = sharpenIdentifier;
  }
  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.sharpen(identifier, sharpenIdentifier);
  }
}
