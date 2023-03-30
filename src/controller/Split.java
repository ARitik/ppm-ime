package controller;

import java.io.IOException;

import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;

 class Split implements ImageCommand {
  private String identifier;
  private String redIdentifier;
  private String greenIdentifier;
  private String blueIdentifier;

  public Split(String identifier, String redIdentifer, String blueIdentifier,
               String greenIdentifier) {
    this.identifier = identifier;
    this.redIdentifier = redIdentifer;
    this.blueIdentifier = blueIdentifier;
    this.greenIdentifier = greenIdentifier;
  }

  public void execute(ImageOperationsBasicPlus model) throws IOException, ExecutionControl.NotImplementedException {
    model.greyscale("red", identifier, redIdentifier);
    model.greyscale("green", identifier, greenIdentifier);
    model.greyscale("blue", identifier, blueIdentifier);
  }
}
