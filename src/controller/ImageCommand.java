package controller;

import java.io.IOException;

import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;
import view.AppView;

/**
 * An interface that represents the image Commands.
 */
interface ImageCommand {
  /**
   * The method executes the relevant method in the model.
   *
   * @param model model object
   * @throws IOException when the image does not exist.
   * @throws ExecutionControl.NotImplementedException when operation has not been implemented.
   */
  void execute(ImageOperationsBasicPlus model, AppView view) throws IOException,
          ExecutionControl.NotImplementedException;
}
