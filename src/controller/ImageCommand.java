package controller;

import java.io.IOException;

import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;

 interface ImageCommand {
  public void execute(ImageOperationsBasicPlus model) throws IOException, ExecutionControl.NotImplementedException;
}
