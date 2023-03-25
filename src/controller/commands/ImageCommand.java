package controller.commands;

import java.io.IOException;

import model.ImageOperationsBasicPlus;

public interface ImageCommand {
  public void execute(ImageOperationsBasicPlus model) throws IOException;
}
