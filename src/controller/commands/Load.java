package controller.commands;

import model.ImageOperationsBasicPlus;

public class Load implements ImageCommand {
  String filePath;
  String identifier;
  public Load(String filePath, String identifer) {
    this.filePath = filePath;
    this.identifier = identifer;
  }
  @Override
  public void execute(ImageOperationsBasicPlus model) {
    model.load(filePath,identifier);
  }

}
