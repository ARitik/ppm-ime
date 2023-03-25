package controller.commands;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

public class Save implements ImageCommand {
  String filePath;
  String identifier;

  public Save(String filePath, String identifer) {
    this.filePath = filePath;
    this.identifier = identifer;
  }

  @Override
  public void execute(ImageOperationsBasicPlus model) throws IOException {
    model.save(filePath, identifier);
  }

}
