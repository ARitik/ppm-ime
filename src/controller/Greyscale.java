package controller;

import java.io.IOException;

import jdk.jshell.spi.ExecutionControl;
import model.ImageOperationsBasicPlus;

 class Greyscale implements ImageCommand {
  String component;
  String identifier;
  String greyScaleIdentifier;
  public Greyscale(String component, String identifier, String greyScaleIdentifier) {
    this.component = component;
    this.identifier = identifier;
    this.greyScaleIdentifier = greyScaleIdentifier;
  }

  public Greyscale(String identifier, String greyScaleIdentifier) {
    this.component = null;
    this.identifier = identifier;
    this.greyScaleIdentifier = greyScaleIdentifier;
  }
  public void execute(ImageOperationsBasicPlus model) throws IOException, ExecutionControl.NotImplementedException {
    if(this.component == null) {
      model.greyscale(identifier,greyScaleIdentifier);
    } else {
      model.greyscale(component,identifier,greyScaleIdentifier);
    }
  }
}
