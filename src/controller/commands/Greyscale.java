package controller.commands;

import model.ImageOperationsBasicPlus;

public class Greyscale implements ImageCommand {
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
  public void execute(ImageOperationsBasicPlus model) {
    if(this.component == null) {
      model.greyscale(identifier,greyScaleIdentifier);
    } else {
      model.greyscale(component,identifier,greyScaleIdentifier);
    }
  }
}
