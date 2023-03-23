package controller.commands;

import java.io.IOException;

import model.ImageOperations;

public interface ImageCommand {
  public void execute(ImageOperations model) throws IOException;
}
